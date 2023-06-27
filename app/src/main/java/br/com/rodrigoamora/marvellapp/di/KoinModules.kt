package br.com.rodrigoamora.marvellapp.di

import androidx.room.Room
import br.com.rodrigoamora.marvellapp.database.AppDatabase
import br.com.rodrigoamora.marvellapp.repository.CharacterRepository
import br.com.rodrigoamora.marvellapp.repository.impl.CharacterRepositoryImpl
import br.com.rodrigoamora.marvellapp.network.retrofit.AppRetrofit
import br.com.rodrigoamora.marvellapp.network.retrofit.webclient.CharacterWebClient
import br.com.rodrigoamora.marvellapp.ui.viewmodel.CharacterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.dsl.module

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            databaseModule,
            repositoryModule,
            retrofitModule,
            servicesModule,
            viewModelModule,
            webClientModule
        )
    )
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "app-marvel-database")
            .build()
    }

    single { get<AppDatabase>().characterDao() }
}

val repositoryModule = module {
    single<CharacterRepository> { CharacterRepositoryImpl(get(), get()) }
}

val retrofitModule = module {
    single { AppRetrofit().instantiateRetrofit() }
}

val servicesModule = module {
    single { AppRetrofit().characterService() }
}

val viewModelModule = module {
    viewModel { CharacterViewModel(get()) }
}

val webClientModule = module {
    single { CharacterWebClient(get()) }
}