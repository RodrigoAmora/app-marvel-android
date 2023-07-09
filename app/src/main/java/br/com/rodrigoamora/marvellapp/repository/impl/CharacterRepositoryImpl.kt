package br.com.rodrigoamora.marvellapp.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import br.com.rodrigoamora.marvellapp.database.dao.CharacterDao
import br.com.rodrigoamora.marvellapp.model.Character
import br.com.rodrigoamora.marvellapp.network.retrofit.webclient.CharacterWebClient
import br.com.rodrigoamora.marvellapp.repository.CharacterRepository
import br.com.rodrigoamora.marvellapp.repository.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class CharacterRepositoryImpl(
    private val characterDao: CharacterDao,
    private val characterWebClient: CharacterWebClient
) : CharacterRepository {

    private val mediator = MediatorLiveData<Resource<List<Character>?>>()

    override fun getCharacters(offset: Int): LiveData<Resource<List<Character>?>> {
        mediator.addSource(getCharactersFromDataBase()) { charactersFound ->
            mediator.value = Resource(charactersFound)
        }


        val failuresFromWebApiLiveData = MutableLiveData<Resource<List<Character>?>>()
        mediator.addSource(failuresFromWebApiLiveData) { resourceOfFailure ->
            val currentResource = mediator.value
            val newResource: Resource<List<Character>?> = if(currentResource != null) {
                Resource(result = currentResource.result, error = resourceOfFailure.error)
            } else {
                resourceOfFailure
            }
            mediator.value = newResource
        }

        getCharacters(offset,
            failure = { errorCode ->
                failuresFromWebApiLiveData.value = Resource(result = null, error = errorCode)
            }
        )

        return mediator
    }

    private fun getCharacters(offset: Int, failure: (errorCode: Int) -> Unit) {
        characterWebClient.getCharacters(offset,
            completion = { charactersList ->
                charactersList?.data?.result?.let {
                    saveCharacters(it)
                }
            },
            failure = { errorCode ->  failure(errorCode) }
        )
    }

    override fun getCharacterByName(name: String): LiveData<Resource<List<Character>?>> {
        getCharacterByName(name,
            failure = { errorCode ->
                mediator.value = Resource(result = null, error = errorCode)
            }
        )
        return mediator
    }

    private fun getCharacterByName(name: String,
                                   failure: (errorCode: Int) -> Unit) {
        characterWebClient.getCharacterByName(name,
            completion = { charactersList ->
                charactersList?.data?.result?.let {
                    mediator.value = Resource(result = it)
                }
            },
            failure = { errorCode ->  failure(errorCode) }
        )
    }

    private fun getCharactersFromDataBase(): LiveData<List<Character>> {
        return characterDao.findAll()
    }

    override fun saveCharacter(character: Character) {
        saveInDatabase(character)
    }

    override fun saveCharacters(charactersList: List<Character>) {
        saveInDatabase(charactersList)
    }

    private fun saveInDatabase(character: Character) {
        CoroutineScope(IO).launch {
            characterDao.save(character)
        }
    }

    private fun saveInDatabase(charactersList: List<Character>) {
        CoroutineScope(IO).launch {
            characterDao.save(charactersList)
        }
    }
}