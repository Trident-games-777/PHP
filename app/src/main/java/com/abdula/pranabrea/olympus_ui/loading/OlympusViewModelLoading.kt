package com.abdula.pranabrea.olympus_ui.loading

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdula.pranabrea.olympus_repo.IOlympusRepo
import com.abdula.pranabrea.olympus_ui.states.OlympusStates
import com.abdula.pranabrea.utils.OlympusConstance
import com.android.installreferrer.api.InstallReferrerClient
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URLEncoder
import java.util.*

class OlympusViewModelLoading(private val iOlympusRepo: IOlympusRepo): ViewModel() {

    private val _state = MutableLiveData<OlympusStates>()
    val state: LiveData<OlympusStates> = _state

    init {
        _state.value = OlympusStates(loading = true)

        viewModelScope.launch {
            if (iOlympusRepo.isExistLinkFile()) {
                _state.value = OlympusStates(loadedLink = iOlympusRepo.getLink())
            } else {
                _state.value = OlympusStates(installReferrerClient = true)
            }
        }
    }

    fun createLink(referrerClient: InstallReferrerClient?, context: Context, advert: String) {
        val packageName = context.packageName
        val appVersion = versionCode(context)
        val osVersion = Build.VERSION.RELEASE
        val timestamp = System.currentTimeMillis() / 1000f
        val userAgent = "Android ${Build.VERSION.RELEASE}; " +
                "${Locale.getDefault()}; " +
                "${Build.MODEL}; " +
                "Build/${Build.ID}"
        val referrerUrl = referrerClient?.installReferrer?.installReferrer ?: ""
        val jsonString = createJsonString(
            OlympusConstance.PACKAGE_KEY to packageName,
            OlympusConstance.REF_STRING_KEY to referrerUrl,
            OlympusConstance.GADID_KEY to advert,
            OlympusConstance.APP_VERSION_KEY to appVersion,
            OlympusConstance.OS_VERSION_KEY to osVersion,
            OlympusConstance.TIMESTAMP_KEY to timestamp,
            OlympusConstance.USER_AGENT_KEY to userAgent
        )
        _state.value = OlympusStates(
            loadedLink = OlympusConstance.BASE_LINK + URLEncoder.encode(
                jsonString,
                "UTF-8"
            )
        )
    }

    private fun createJsonString(vararg params: Pair<String, Any>): String {
        val jsonObject = JSONObject()
        for ((key, value) in params) {
            jsonObject.put(key, value)
        }
        return jsonObject.toString()
    }

    private fun versionCode(context: Context): Long = try {
        val info = packageInfo(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            info.longVersionCode
        } else {
            info.versionCode.toLong()
        }
    } catch (ex: Exception) {
        -1
    }

    private fun packageInfo(context: Context): PackageInfo {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(0))
        } else {
            context.packageManager.getPackageInfo(context.packageName, 0)
        }
    }
}