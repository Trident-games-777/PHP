package com.abdula.pranabrea.olympus_ui.states

import android.net.Uri
import com.trident.media.helper.network.models.postmodel.ConversionDataObject

data class OlympusStates(
    val loading: Boolean = false,
    val loadedLinkFromFile: String? = null,
    val loadedFbData: Uri? = null,
    val loadedFbNamingData: ConversionDataObject? = null
)