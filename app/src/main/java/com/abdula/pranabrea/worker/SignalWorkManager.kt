package com.abdula.pranabrea.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.abdula.pranabrea.utils.OlympusParams
import com.onesignal.OneSignal

class SignalWorkManager(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val data = inputData.getString(OlympusParams.DATA_PARAM)
        sendDataToOneSignal(data)
        return Result.success()
    }

    private fun sendDataToOneSignal(data: String?) {
        if (data != null) {
            when {
                data == "null" -> {
                    OneSignal.sendTag("key2", "organic")
                }
                data.contains("myapp") -> {
                    OneSignal.sendTag(
                        "key2",
                        data.toString().replace("myapp://", "").substringBefore("/")
                    )
                }
                (!data.contains("myapp")) -> {
                    OneSignal.sendTag(
                        "key2",
                        data.substringBefore("_")
                    )
                }
            }
        }
    }
}