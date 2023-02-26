package com.abdula.pranabrea.fb_naming_data

import com.trident.media.helper.network.models.postmodel.ConversionDataObject

interface IFbNamingData {

    suspend fun getFbNamingData(): ConversionDataObject?
}