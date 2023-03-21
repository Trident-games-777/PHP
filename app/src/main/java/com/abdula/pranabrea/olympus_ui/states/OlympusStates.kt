package com.abdula.pranabrea.olympus_ui.states

data class OlympusStates(
    val loading: Boolean = false,
    val loadedLink: String? = null,
    val installReferrerClient: Boolean = false,
)