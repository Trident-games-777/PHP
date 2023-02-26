package com.abdula.pranabrea.olympus_data

interface ILocalLink {
    fun readLink(): String
    fun writeLink(link: String)
    fun isExistLinkFile(): Boolean
}