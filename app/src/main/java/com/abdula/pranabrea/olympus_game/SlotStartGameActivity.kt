package com.abdula.pranabrea.olympus_game

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.lifecycle.lifecycleScope
import com.abdula.pranabrea.databinding.OlympusGameStartBinding
import com.abdula.pranabrea.utils.goToGame
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SlotStartGameActivity: AppCompatActivity() {

    private lateinit var binding: OlympusGameStartBinding
    private var animation: Animator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OlympusGameStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding.imageStart.isEnabled = false

        lifecycleScope.launch {
            delay(1000)
            btnAnim(binding.startButton)

        }
    }

    private fun btnAnim(btn: TextView) {
        btn.visibility = View.VISIBLE
        animation = ObjectAnimator.ofFloat(btn, "translationY", -2000f, 0f).apply {
            duration = 2000
            interpolator = BounceInterpolator()
            doOnCancel {  }
            doOnEnd {
                imAnim(binding.imageStart)
            }
            start()
        }
    }

    private fun imAnim(im: ImageView) {
        im.visibility = View.VISIBLE
        animation = ObjectAnimator.ofFloat(im, "translationY", -1500f, 0f).apply {
            duration = 2000
            interpolator = BounceInterpolator()
            doOnCancel {  }
            doOnEnd {
                txtAnim(binding.startGameTxt)
            }
            start()
        }
    }

    private fun txtAnim(txt: TextView) {
        txt.visibility = View.VISIBLE
        animation = ObjectAnimator.ofFloat(txt, "translationY", -800f, 0f).apply {
            duration = 1500
            interpolator = BounceInterpolator()
            doOnCancel {  }
            doOnEnd {
                binding.startButton.isEnabled = true
                binding.startButton.setOnClickListener {
                    this@SlotStartGameActivity.goToGame(SlotGameActivity())
                }
            }
            start()
        }
    }

    override fun onPause() {
        super.onPause()
        animation?.cancel()
    }
}