package com.abdula.pranabrea.olympus_game

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.abdula.pranabrea.R
import com.abdula.pranabrea.databinding.OlympusGameBinding
import com.abdula.pranabrea.utils.goToStartGame
import kotlinx.coroutines.launch

class SlotGameActivity : AppCompatActivity() {
    private lateinit var binding: OlympusGameBinding
    private lateinit var viewModel: SlotGameViewModel

    private var credits = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OlympusGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        viewModel = ViewModelProvider(this)[SlotGameViewModel::class.java]

        binding.attemptsTxt.text = getString(R.string.credits, credits)

        binding.spinButton.setOnClickListener {
            gameBet()
            viewModel.processIntent(SlotGameIntent.Spin)
        }

        lifecycleScope.launch {
            viewModel.model.collect { render(it) }
        }
    }

    private fun render(model: SlotGameModel) {
        when (model) {
            is SlotGameModel.Idle -> {
                binding.imageView1.setImageResource(model.slots[0])
                binding.imageView2.setImageResource(model.slots[1])
                binding.imageView3.setImageResource(model.slots[2])
            }
            is SlotGameModel.Spinning -> {
                hideAnimation()
            }
            is SlotGameModel.Result -> {
                showAnimation()

                binding.imageView1.setImageResource(model.slots[0])
                binding.imageView2.setImageResource(model.slots[1])
                binding.imageView3.setImageResource(model.slots[2])

                if (model.win) {
                    winTextAnimator()
                } else {
                    if (credits <= 0) {
                        loseTextAnimator()
                    } else {
                        binding.spinButton.isEnabled = true
                    }
                }
            }
        }
    }

    private fun gameBet() {
        binding.tvWin.visibility = View.INVISIBLE
        binding.imageView4.visibility = View.INVISIBLE
        binding.spinButton.isEnabled = false
        if (credits > 0) {
            credits -= 10
            binding.attemptsTxt.text = getString(R.string.credits, credits)
        }
    }

    private fun loseTextAnimator() {
        binding.tvWin.text = getString(R.string.lose)
        binding.tvWin.visibility = View.VISIBLE
        val anim = ObjectAnimator.ofFloat(binding.tvWin, "alpha", 0f, 1f).apply {
            doOnEnd {
                binding.spinButton.visibility = View.INVISIBLE
                binding.returnButton.visibility = View.VISIBLE
                binding.returnButton.setOnClickListener {
                    this@SlotGameActivity.goToStartGame(SlotStartGameActivity())
                }
            }
        }
        anim.duration = 2000
        anim.start()
    }

    private fun winTextAnimator() {

        val animText = ObjectAnimator.ofFloat(binding.tvWin, "alpha", 0f, 1f)
        val animImage = ObjectAnimator.ofFloat(binding.imageView4, "alpha", 0f, 1f)
        AnimatorSet().apply {
            playSequentially(animText, animImage)
            doOnStart {
                binding.tvWin.visibility = View.VISIBLE
                binding.imageView4.visibility = View.VISIBLE
            }
            doOnEnd {
                credits += 200
                binding.attemptsTxt.text = getString(R.string.credits, credits)
                binding.spinButton.isEnabled = true
            }
            duration = 2000
            start()
        }
    }

    private fun showAnimation() {
        val animator1 = ValueAnimator.ofFloat(500f, 0f)
        val animator2 = ValueAnimator.ofFloat(500f, 0f)
        val animator3 = ValueAnimator.ofFloat(500f, 0f)
        animator1.duration = 300
        animator2.duration = 300
        animator3.duration = 300
        animator1.addUpdateListener {
            val value = it.animatedValue as Float
            binding.imageView1.rotation = value
            binding.imageView1.scaleX = (500f - value) / 500f
            binding.imageView1.scaleY = (500f - value) / 500f
        }
        animator2.addUpdateListener {
            val value = it.animatedValue as Float
            binding.imageView2.rotation = value
            binding.imageView2.scaleX = (500f - value) / 500f
            binding.imageView2.scaleY = (500f - value) / 500f
        }
        animator3.addUpdateListener {
            val value = it.animatedValue as Float
            binding.imageView3.rotation = value
            binding.imageView3.scaleX = (500f - value) / 500f
            binding.imageView3.scaleY = (500f - value) / 500f
        }
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animator1, animator2, animator3)
        animatorSet.start()
    }

    private fun hideAnimation() {
        val animator1 = ValueAnimator.ofFloat(0f, 500f)
        val animator2 = ValueAnimator.ofFloat(0f, 500f)
        val animator3 = ValueAnimator.ofFloat(0f, 500f)
        animator1.duration = 300
        animator2.duration = 300
        animator3.duration = 300
        animator1.addUpdateListener {
            val value = it.animatedValue as Float
            binding.imageView1.rotation = value
            binding.imageView1.scaleX = (500f - value) / 500f
            binding.imageView1.scaleY = (500f - value) / 500f
        }
        animator2.addUpdateListener {
            val value = it.animatedValue as Float
            binding.imageView2.rotation = value
            binding.imageView2.scaleX = (500f - value) / 500f
            binding.imageView2.scaleY = (500f - value) / 500f
        }
        animator3.addUpdateListener {
            val value = it.animatedValue as Float
            binding.imageView3.rotation = value
            binding.imageView3.scaleX = (500f - value) / 500f
            binding.imageView3.scaleY = (500f - value) / 500f
        }
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animator1, animator2, animator3)
        animatorSet.start()
    }
}
