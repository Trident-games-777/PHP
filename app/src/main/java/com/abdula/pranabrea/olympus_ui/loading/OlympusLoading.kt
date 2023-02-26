package com.abdula.pranabrea.olympus_ui.loading

import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.abdula.pranabrea.databinding.OlympusLoadingBinding
import com.abdula.pranabrea.olympus_game.SlotStartGameActivity
import com.abdula.pranabrea.olympus_ui.web_view.OlympusWeb
import com.abdula.pranabrea.utils.OlympusParams
import com.abdula.pranabrea.utils.goToStartGame
import com.abdula.pranabrea.utils.goToWeb
import com.abdula.pranabrea.worker.SignalWorkManager
import com.fmb.conversion.ConvClass
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
import com.trident.media.helper.network.models.postmodel.ConversionDataObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class OlympusLoading: AppCompatActivity() {

    private lateinit var binding: OlympusLoadingBinding

    private val vm by viewModel<OlympusViewModelLoading>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OlympusLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        ConvClass.makeTest("{\"ad_id\":23852668462580140,\"ad_objective_name\":\"APP_INSTALLS\",\"adgroup_id\":23852668431700140,\"adgroup_name\":\"1\",\"campaign_id\":23852668430840140,\"campaign_name\":\"1\",\"campaign_group_id\":23852668430440140,\"campaign_group_name\":\"traffdelivery_summer_creo\",\"account_id\":1125247238167128,\"is_instagram\":false,\"publisher_platform\":\"facebook\",\"platform_position\":null}")

        if (adbJob()) {
            this.goToStartGame(SlotStartGameActivity())
        } else {
            vm.state.observe(this) { state ->
                when {
                    state.loading -> binding.loadingAnim.visibility = View.VISIBLE
                    state.loadedLinkFromFile != null -> mainJob(state.loadedLinkFromFile)
                    state.loadedFbData != null -> lifecycleScope.launch { mainJob(state.loadedFbData) }
                    state.loadedFbNamingData != null -> lifecycleScope.launch { mainJob(state.loadedFbNamingData) }
                    else -> lifecycleScope.launch { mainJob(null) }
                }
            }
        }
    }

    private suspend fun mainJob(fbNaming: ConversionDataObject?) {
        val advert = advertJob()
        OneSignal.setExternalUserId(advert)
        oneSignalJob(fbNaming = fbNaming)
        val link = vm.create(fbNamingData = fbNaming, advert = advert)
        this.goToWeb(link, OlympusWeb())
    }

    private suspend fun mainJob(fb: Uri) {
        val advert = advertJob()
        OneSignal.setExternalUserId(advert)
        oneSignalJob(fb = fb)
        val link = vm.create(fbData = fb, advert = advert)
        this.goToWeb(link, OlympusWeb())
    }

    private fun mainJob(link: String) {
        this.goToWeb(link, OlympusWeb())
    }

    private fun adbJob(): Boolean {
        return Settings.Global.getString(
            this.contentResolver,
            Settings.Global.ADB_ENABLED
        ) == "1"
    }

    private suspend fun advertJob(): String {
        return withContext(Dispatchers.IO) {
            AdvertisingIdClient.getAdvertisingIdInfo(this@OlympusLoading).id.toString()
        }
    }

    private fun oneSignalJob(fb: Uri? = null, fbNaming: ConversionDataObject? = null) {

        val str = fb?.toString() ?: fbNaming?.campaignName.toString()

        val data = Data.Builder().putString(OlympusParams.DATA_PARAM, str).build()

        val workRequest = OneTimeWorkRequestBuilder<SignalWorkManager>()
            .setInputData(data)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }
}