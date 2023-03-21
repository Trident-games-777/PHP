package com.abdula.pranabrea.olympus_di

import com.abdula.pranabrea.olympus_data.ILocalLink
import com.abdula.pranabrea.olympus_data.LocalLink
import com.abdula.pranabrea.olympus_repo.IOlympusRepo
import com.abdula.pranabrea.olympus_repo.OlympusRepo
import org.koin.dsl.module

val olympusModule = module {

    single<ILocalLink> {
        LocalLink(context = get())
    }

    single<IOlympusRepo> {
        OlympusRepo(
            iLocalLink = get()
        )
    }
}