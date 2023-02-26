package com.abdula.pranabrea.olympus_repo

import android.net.Uri
import com.trident.media.helper.network.models.postmodel.ConversionDataObject

interface IOlympusRepo {
    fun getLink(): String
    fun writeLink(link: String)
    fun isExistLinkFile(): Boolean

    suspend fun getFbData(): Uri?
    suspend fun getFbNamingData(): ConversionDataObject?
}