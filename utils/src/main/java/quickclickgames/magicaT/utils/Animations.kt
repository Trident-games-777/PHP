package quickclickgames.magicaT.utils

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.widget.ImageView
import androidx.core.animation.doOnEnd

class Animations {

    fun verticalAnimation(imageView: ImageView, curEl: Int, action: (Int) -> Unit): Animator {
        val start = ValueAnimator.ofFloat(0f, 200f).apply {
            addUpdateListener {
                val v = it.animatedValue as Float
                imageView.translationY = (0f - v)
            }
            doOnEnd {
                action(curEl)
            }
        }
        val finish= ValueAnimator.ofFloat(-200f, 0f).apply {
            addUpdateListener {
                val v = it.animatedValue as Float
                imageView.translationY = (1f - v)
            }
            doOnEnd {

            }
        }
        return AnimatorSet().apply {
            playSequentially(start, finish)
            duration = 100
            start()
        }
    }

    fun horizontalAnimation(imageView: ImageView, curEl: Int, action: (Int) -> Unit): Animator {
        val start = ValueAnimator.ofFloat(0f, 200f).apply {
            addUpdateListener {
                val v = it.animatedValue as Float
                imageView.translationX = (0f - v)
            }
            doOnEnd {
                action(curEl)
            }
        }
        val finish= ValueAnimator.ofFloat(-200f, 0f).apply {
            addUpdateListener {
                val v = it.animatedValue as Float
                imageView.translationX = (1f - v)
            }
            doOnEnd {

            }
        }
        return AnimatorSet().apply {
            playSequentially(start, finish)
            duration = 100
            start()
        }
    }

    fun angleAnimation(imageView: ImageView, curEl: Int, action: (Int) -> Unit): Animator {
        val start = ValueAnimator.ofFloat(0f, 200f).apply {
            addUpdateListener {
                val v = it.animatedValue as Float
                imageView.translationX = (0f - v)
                imageView.translationY = (0f - v)
            }
            doOnEnd {
                action(curEl)
            }
        }
        val finish= ValueAnimator.ofFloat(-200f, 0f).apply {
            addUpdateListener {
                val v = it.animatedValue as Float
                imageView.translationX = (1f - v)
                imageView.translationY = (1f - v)
            }
            doOnEnd {

            }
        }
        return AnimatorSet().apply {
            playSequentially(start, finish)
            duration = 100
            start()
        }
    }
}