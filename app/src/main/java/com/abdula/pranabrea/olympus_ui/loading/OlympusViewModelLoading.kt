package com.abdula.pranabrea.olympus_ui.loading

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdula.pranabrea.olympus_repo.IOlympusRepo
import com.abdula.pranabrea.olympus_ui.states.OlympusStates
import com.abdula.pranabrea.utils.OlympusParams
import com.trident.media.helper.network.models.postmodel.ConversionDataObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class OlympusViewModelLoading(private val iOlympusRepo: IOlympusRepo): ViewModel() {

    private val _state = MutableLiveData<OlympusStates>()
    val state: LiveData<OlympusStates> = _state

    init {
        _state.value = OlympusStates(loading = true)

        viewModelScope.launch {
            if (iOlympusRepo.isExistLinkFile()) {
                _state.value = OlympusStates(loadedLinkFromFile = iOlympusRepo.getLink())
            } else {
                fetchFbData()
            }
        }
    }

    private suspend fun fetchFbData() {
        when (val fb = iOlympusRepo.getFbData()) {
            null -> fetchFbNamingData()
            else -> _state.value = OlympusStates(loadedFbData = fb)
        }
    }

    private suspend fun fetchFbNamingData() {
        _state.value = OlympusStates(loadedFbNamingData = iOlympusRepo.getFbNamingData())
    }

    suspend fun create(
        fbNamingData: ConversionDataObject? = null,
        fbData: Uri? = null,
        advert: String
    ): String = withContext(Dispatchers.IO) {
        OlympusParams.ROOT + OlympusParams.PACKAGE.toUri().buildUpon().apply {
            appendQueryParameter(OlympusParams.SECURE_GET_PARAM, OlympusParams.SECURE_KEY)
            appendQueryParameter(OlympusParams.TMZ_KEY, TimeZone.getDefault().id)
            appendQueryParameter(OlympusParams.ADVERT_KEY, advert)
            appendQueryParameter(OlympusParams.FB_KEY, fbData.toString())
            appendQueryParameter(
                OlympusParams.SOURCE_KEY,
                if (fbData != null) "deeplink" else fbNamingData?.source.toString()
            )
            appendQueryParameter(
                OlympusParams.EXTERNAL_ID,
                if (fbData != null) "null" else fbNamingData?.externalId.toString()
            )
            appendQueryParameter(OlympusParams.EVENT_ID, fbNamingData?.adEventId.toString())
            appendQueryParameter(OlympusParams.CAMPAIGN_ID, fbNamingData?.campaignId.toString())
            appendQueryParameter(OlympusParams.CAMPAIGN_NAME, fbNamingData?.campaignName.toString())
            appendQueryParameter(OlympusParams.ID_TYPE, fbNamingData?.adType.toString())
            appendQueryParameter(OlympusParams.GROUP_NAME, fbNamingData?.adGroupName.toString())
            appendQueryParameter(OlympusParams.ORIG_COST, "null")
            appendQueryParameter(OlympusParams.NET_TYPE, fbNamingData?.networkType.toString())
        }.toString()
    }
}