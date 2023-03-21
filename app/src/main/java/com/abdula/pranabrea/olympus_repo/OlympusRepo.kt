package com.abdula.pranabrea.olympus_repo

import com.abdula.pranabrea.olympus_data.ILocalLink
import com.abdula.pranabrea.utils.OlympusConstance
import com.abdula.pranabrea.utils.rootLink

class OlympusRepo(
    private val iLocalLink: ILocalLink
    ) : IOlympusRepo {

    override fun getLink(): String {
        return iLocalLink.readLink()
    }

    override fun writeLink(link: String) {
        if (!link.contains(OlympusConstance.BASE_LINK.rootLink())) {
            iLocalLink.writeLink(link)
        }
    }

    override fun isExistLinkFile(): Boolean {
        return iLocalLink.isExistLinkFile()
    }
}