package com.abdula.pranabrea.fb_data

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.facebook.applinks.AppLinkData
import kotlin.coroutines.suspendCoroutine

class FbData(private val context: Context): IFbData {

    override suspend fun getFbData(): Uri? = suspendCoroutine { con ->
        AppLinkData.fetchDeferredAppLinkData(context) { data ->
            con.resumeWith(Result.success(data?.targetUri))
//            con.resumeWith(Result.success("myapp://test1/test!3/test-3/test 4/test5/ыtest6".toUri()))
        }
    }
}