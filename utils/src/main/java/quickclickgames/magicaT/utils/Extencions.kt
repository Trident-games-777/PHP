package quickclickgames.magicaT.utils

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import com.onesignal.OneSignal

fun Activity.jumpToMidasGoldenTouchWeb(url: String, activity: Activity) {
    val i = Intent(this, activity::class.java)
    i.putExtra("url", url)
    startActivity(i)
    finish()
}

fun Activity.jumpToMidasGoldenTouchSettings(activity: Activity) {
    val i = Intent(this, activity::class.java)
    startActivity(i)
    finish()
}

fun Activity.jumpToMidasGoldenTouchRoom(level: String, room: String, activity: Activity) {
    val i = Intent(this, activity::class.java)
    i.putExtra("level", level)
    i.putExtra("room", room)
    startActivity(i)
    finish()
}

fun Activity.isAdb(): Boolean {
    return Settings.Global.getString(
        this.contentResolver,
        Settings.Global.ADB_ENABLED
    ) == "1"
}