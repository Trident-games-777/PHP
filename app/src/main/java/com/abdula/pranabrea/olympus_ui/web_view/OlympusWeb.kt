package com.abdula.pranabrea.olympus_ui.web_view

import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.abdula.pranabrea.databinding.OlympusWebViewBinding
import com.abdula.pranabrea.olympus_game.SlotStartGameActivity
import com.abdula.pranabrea.utils.goToStartGame
import org.koin.androidx.viewmodel.ext.android.viewModel

class OlympusWeb: AppCompatActivity() {

    private val vm by viewModel<OlympusViewModelWeb>()
    private lateinit var binding: OlympusWebViewBinding

    private lateinit var valueCallback: ValueCallback<Array<Uri?>>

    val result = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        valueCallback.onReceiveValue(it.toTypedArray())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OlympusWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val midasUserAgent = WebView(this).settings.userAgentString.replace(" wv", "")

        with(binding.webView) {
            loadUrl(requireNotNull(intent.getStringExtra("url")))
            webViewClient = Client()
            with(settings) {
                javaScriptEnabled = true
                domStorageEnabled = true
                loadWithOverviewMode = false
                userAgentString = midasUserAgent
            }
        }

        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(binding.webView, true)

        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.webView.canGoBack()) {
                        binding.webView.goBack()
                    }
                }
            })

        binding.webView.webChromeClient = object : WebChromeClient() {

            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri?>>,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                valueCallback = filePathCallback
                result.launch(IMG_TYPE)
                return true
            }

            override fun onCreateWindow(
                view: WebView?, isDialog: Boolean,
                isUserGesture: Boolean, resultMsg: Message
            ): Boolean {
                val newWebView = WebView(applicationContext)
                newWebView.webChromeClient = this
                with(newWebView) {
                    with(settings) {
                        javaScriptEnabled = true
                        javaScriptCanOpenWindowsAutomatically = true
                        domStorageEnabled = true
                        setSupportMultipleWindows(true)
                    }
                }
                val transport = resultMsg.obj as WebView.WebViewTransport
                transport.webView = newWebView
                resultMsg.sendToTarget()
                return true
            }
        }
    }

    private inner class Client : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            CookieManager.getInstance().flush()
            url?.let {
                if (it == "https://olympuslightning.shop/") {
                    goToStartGame(SlotStartGameActivity())
                } else {
                    vm.setLink(it)
                }
            }
        }
    }

    companion object {
        private const val IMG_TYPE = "image/*"
    }
}