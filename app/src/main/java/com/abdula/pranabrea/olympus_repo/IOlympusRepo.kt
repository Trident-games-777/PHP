package com.abdula.pranabrea.olympus_repo


interface IOlympusRepo {
    fun getLink(): String
    fun writeLink(link: String)
    fun isExistLinkFile(): Boolean
}