package com.abdula.pranabrea.olympus_data

import android.content.Context
import java.io.File

class LocalLink(private val context: Context): ILocalLink {
    override fun readLink(): String {
        return context.openFileInput(OLYMPUS_DATA_LINK).bufferedReader().useLines { it.first() }
    }

    override fun writeLink(link: String) {
        if (!File(context.filesDir, OLYMPUS_DATA_LINK).exists()) {
            context.openFileOutput(OLYMPUS_DATA_LINK, Context.MODE_PRIVATE)
                .use { it.write(link.toByteArray()) }
        }
    }

    override fun isExistLinkFile(): Boolean {
        return File(context.filesDir, OLYMPUS_DATA_LINK).exists()
    }

    companion object {
        const val OLYMPUS_DATA_LINK = "olympus.data.link"
    }
}