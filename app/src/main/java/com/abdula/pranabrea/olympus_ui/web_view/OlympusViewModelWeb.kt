package com.abdula.pranabrea.olympus_ui.web_view

import androidx.lifecycle.ViewModel
import com.abdula.pranabrea.olympus_repo.IOlympusRepo

class OlympusViewModelWeb(private val iOlympusRepo: IOlympusRepo): ViewModel() {

    fun setLink(link: String) {
        iOlympusRepo.writeLink(link)
    }
}