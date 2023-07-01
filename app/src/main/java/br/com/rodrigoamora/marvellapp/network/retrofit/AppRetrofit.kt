package br.com.rodrigoamora.marvellapp.network.retrofit

import br.com.rodrigoamora.marvellapp.network.retrofit.service.CharacterService
import br.com.rodrigoamora.marvellapp.network.retrofit.service.ComicService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppRetrofit {

    private val client by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    private val retrofit by lazy {
        val baseURL = "https://gateway.marvel.com/v1/public/"
        Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun instantiateRetrofit(): Retrofit {
        return retrofit
    }

    // SERVICES
    fun characterService(): CharacterService {
        return retrofit.create(CharacterService::class.java)
    }

    fun comicService(): ComicService {
        return retrofit.create(ComicService::class.java)
    }

}