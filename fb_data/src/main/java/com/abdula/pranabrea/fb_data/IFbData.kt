package com.abdula.pranabrea.fb_data

import android.net.Uri

interface IFbData {
    suspend fun getFbData(): Uri?
}