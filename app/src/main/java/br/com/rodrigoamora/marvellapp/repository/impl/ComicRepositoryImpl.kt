package br.com.rodrigoamora.marvellapp.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import br.com.rodrigoamora.marvellapp.database.dao.ComicDao
import br.com.rodrigoamora.marvellapp.model.Character
import br.com.rodrigoamora.marvellapp.model.Comic
import br.com.rodrigoamora.marvellapp.network.retrofit.webclient.CharacterWebClient
import br.com.rodrigoamora.marvellapp.network.retrofit.webclient.ComicWebClient
import br.com.rodrigoamora.marvellapp.repository.ComicRepository
import br.com.rodrigoamora.marvellapp.repository.Resource

class ComicRepositoryImpl(
    private val comicDao: ComicDao,
    private val comicWebClient: ComicWebClient
): ComicRepository {

    private val mediator = MediatorLiveData<Resource<List<Comic>?>>()

    override fun getComicsByCharacterId(characterId: Int): LiveData<Resource<List<Comic>?>> {
        getComicsByCharacterId(characterId,
            completion = { comics ->
                        mediator.value = Resource(result = comics)
            },
            failure = { errorCode ->
                        mediator.value = Resource(result = null, error = errorCode)
            }
        )
        return mediator
    }

    private fun getComicsByCharacterId(characterId: Int,
                                       completion: (comics: List<Comic>) -> Unit,
                                       failure: (errorCode: Int) -> Unit) {
        comicWebClient.getComicsByCharacterId(characterId,
            completion = { comics ->
                comics?.data?.result?.let {
                    completion(it)
                }
            },
            failure = { errorCode ->  failure(errorCode) }
        )
    }

}