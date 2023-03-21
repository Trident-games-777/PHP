package com.abdula.pranabrea.olympus_ui.loading

import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.abdula.pranabrea.databinding.OlympusLoadingBinding
import com.abdula.pranabrea.olympus_game.SlotStartGameActivity
import com.abdula.pranabrea.olympus_ui.web_view.OlympusWeb
import com.abdula.pranabrea.utils.goToStartGame
import com.abdula.pranabrea.utils.goToWeb
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
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

        if (adbJob()) {
            this.goToStartGame(SlotStartGameActivity())
        } else {
            vm.state.observe(this) { state ->
                when {
                    state.loading -> binding.loadingAnim.visibility = View.VISIBLE
                    state.loadedLink != null -> mainJob(state.loadedLink)
                    else -> lifecycleScope.launch(Dispatchers.IO) { mainJob() }
                }
            }
        }
    }

    private suspend fun mainJob() {
        val advert = advertJob()
        OneSignal.setExternalUserId(advert)
        getReferrerData(advert)
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

    private fun getReferrerData(advert: String) {
        val referrerClient = InstallReferrerClient.newBuilder(applicationContext).build()
        referrerClient.startConnection(object : InstallReferrerStateListener {

            override fun onInstallReferrerSetupFinished(responseCode: Int) {
                when (responseCode) {
                    InstallReferrerClient.InstallReferrerResponse.OK -> {
                        vm.createLink(referrerClient, applicationContext, advert)
                    }
                    InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {
                       vm.createLink(null, applicationContext, advert)
                    }
                    InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
                       vm.createLink(null, applicationContext, advert)
                    }
                    else -> {
                        vm.createLink(null, applicationContext, advert)
                    }
                }
            }

            override fun onInstallReferrerServiceDisconnected() {
                vm.createLink(null, applicationContext, advert)
            }
        })
    }
}