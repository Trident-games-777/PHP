package com.abdula.pranabrea.olympus_repo

import android.net.Uri
import com.abdula.pranabrea.fb_data.IFbData
import com.abdula.pranabrea.fb_naming_data.IFbNamingData
import com.abdula.pranabrea.olympus_data.ILocalLink
import com.abdula.pranabrea.utils.OlympusParams
import com.trident.media.helper.network.models.postmodel.ConversionDataObject

class OlympusRepo(
    private val iLocalLink: ILocalLink,
    private val iFbData: IFbData,
    private val iFbNamingData: IFbNamingData
    ) : IOlympusRepo {

    override fun getLink(): String {
        return iLocalLink.readLink()
    }

    override fun writeLink(link: String) {
        if (!link.contains(OlympusParams.ROOT)) {
            iLocalLink.writeLink(link)
        }
    }

    override fun isExistLinkFile(): Boolean {
        return iLocalLink.isExistLinkFile()
    }

    override suspend fun getFbData(): Uri? {
        return iFbData.getFbData()
    }

    override suspend fun getFbNamingData(): ConversionDataObject? {
        return iFbNamingData.getFbNamingData()
    }
}