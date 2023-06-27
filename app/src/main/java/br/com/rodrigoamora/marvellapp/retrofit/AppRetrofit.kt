package br.com.rodrigoamora.marvellapp.retrofit

import br.com.rodrigoamora.marvellapp.retrofit.service.CharacterService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppRetrofit {

    companion object {
        const val API_KEY = "d17cef9472abf96413048a10bf24e04a"
        const val MD5_HASH = "cd79db65cd307fc9503bdadcfd9179e2"
    }

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
        val baseURL = "https://gateway.marvel.com/v1/"
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

}