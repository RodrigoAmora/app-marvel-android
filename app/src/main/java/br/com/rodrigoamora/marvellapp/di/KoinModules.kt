package br.com.rodrigoamora.marvellapp.di

import androidx.room.Room
import br.com.rodrigoamora.marvellapp.BuildConfig
import br.com.rodrigoamora.marvellapp.database.AppDatabase
import br.com.rodrigoamora.marvellapp.network.retrofit.AppRetrofit
import br.com.rodrigoamora.marvellapp.network.retrofit.webclient.CharacterWebClient
import br.com.rodrigoamora.marvellapp.network.retrofit.webclient.ComicWebClient
import br.com.rodrigoamora.marvellapp.repository.CharacterRepository
import br.com.rodrigoamora.marvellapp.repository.ComicRepository
import br.com.rodrigoamora.marvellapp.repository.impl.CharacterRepositoryImpl
import br.com.rodrigoamora.marvellapp.repository.impl.ComicRepositoryImpl
import br.com.rodrigoamora.marvellapp.ui.viewmodel.CharacterViewModel
import br.com.rodrigoamora.marvellapp.ui.viewmodel.ComicViewModel
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
            BuildConfig.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().characterDao() }
    single { get<AppDatabase>().comicDao() }
}

val repositoryModule = module {
    single<CharacterRepository> { CharacterRepositoryImpl(get(), get()) }
    single<ComicRepository> { ComicRepositoryImpl(get(), get()) }
}

val retrofitModule = module {
    single { AppRetrofit("").instantiateRetrofit() }
}

val servicesModule = module {
    single { AppRetrofit(BuildConfig.MARVEL_BASE_URL).characterService() }
    single { AppRetrofit(BuildConfig.MARVEL_BASE_URL).comicService() }
}

val viewModelModule = module {
    viewModel { CharacterViewModel(get()) }
    viewModel { ComicViewModel(get()) }
}

val webClientModule = module {
    single { CharacterWebClient(get()) }
    single { ComicWebClient(get(), get()) }
}