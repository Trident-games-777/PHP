package com.abdula.pranabrea.olympus_di

import com.abdula.pranabrea.olympus_ui.loading.OlympusViewModelLoading
import com.abdula.pranabrea.olympus_ui.web_view.OlympusViewModelWeb
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    viewModel<OlympusViewModelLoading> {
        OlympusViewModelLoading(
            iOlympusRepo = get()
        )
    }

    viewModel<OlympusViewModelWeb> {
        OlympusViewModelWeb(
            iOlympusRepo = get()
        )
    }
}