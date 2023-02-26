package com.abdula.pranabrea.utils

import android.app.Activity
import android.content.Intent

fun Activity.goToWeb(url: String, activity: Activity) {
    val i = Intent(this, activity::class.java)
    i.putExtra("url", url)
    startActivity(i)
    finish()
}

fun Activity.goToStartGame(activity: Activity) {
    val i = Intent(this, activity::class.java)
    startActivity(i)
    finish()
}

fun Activity.goToGame(activity: Activity) {
    val i = Intent(this, activity::class.java)
    startActivity(i)
}
