package br.com.rodrigoamora.marvellapp.network.retrofit.webclient

import br.com.rodrigoamora.marvellapp.network.apikey.MarvelApiKey
import br.com.rodrigoamora.marvellapp.network.response.CharacterResponse
import br.com.rodrigoamora.marvellapp.network.response.ComicResponse
import br.com.rodrigoamora.marvellapp.network.retrofit.service.CharacterService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterWebClient(
    private val service: CharacterService
) {

    private fun<T> executeRequest(
        call: Call<T>,
        completion: (result: T?) -> Unit,
        failure: (errorCode: Int) -> Unit
    ) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                when (val responseCode = response.code()) {
                    in 200..299 -> {
                        completion(response.body())
                    }
                    else -> {
                        failure(responseCode)
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                failure(call.hashCode())
            }
        })
    }

    fun getCharacters(offset: Int, completion: (charactersList: CharacterResponse?) -> Unit,
                      failure: (errorCode: Int) -> Unit) {
        executeRequest(
            service.getCharacters(1, MarvelApiKey.API_KEY, MarvelApiKey.MD5_HASH, 20, offset, "name"),
            completion = { charactersList ->
                charactersList?.let {
                    completion(it)
                }
            },
            failure = { errorCode ->  failure(errorCode) }
        )
    }

    fun getCharacterByName(name: String,
                           completion: (charactersList: CharacterResponse?) -> Unit,
                           failure: (errorCode: Int) -> Unit) {
        executeRequest(
            service.getCharacterByName(1, MarvelApiKey.API_KEY, MarvelApiKey.MD5_HASH, name),
            completion = { charactersList ->
                charactersList?.let {
                    completion(it)
                }
            },
            failure = { errorCode ->  failure(errorCode) }
        )
    }

    fun getComicsOfCharacters(characterID: Int,
                              completion: (comicResponse: ComicResponse?) -> Unit,
                              failure: (errorCode: Int) -> Unit) {
        executeRequest(
            service.getComicsOfCharacters(characterID, 1, MarvelApiKey.API_KEY, MarvelApiKey.MD5_HASH),
            completion = { comicResponse ->
                comicResponse?.let {
                    completion(it)
                }
            },
            failure = { errorCode ->  failure(errorCode) }
        )
    }

}