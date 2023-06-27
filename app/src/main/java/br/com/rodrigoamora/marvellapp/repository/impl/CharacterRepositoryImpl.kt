package br.com.rodrigoamora.marvellapp.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import br.com.rodrigoamora.marvellapp.database.dao.CharacterDao
import br.com.rodrigoamora.marvellapp.model.Character
import br.com.rodrigoamora.marvellapp.repository.CharacterRepository
import br.com.rodrigoamora.marvellapp.repository.Resource
import br.com.rodrigoamora.marvellapp.retrofit.webclient.CharacterWebClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class CharacterRepositoryImpl(
    private val characterDao: CharacterDao,
    private val characterWebClient: CharacterWebClient
) : CharacterRepository {

    private val mediator = MediatorLiveData<Resource<List<Character>?>>()

    override fun deleteCharacter(character: Character) {
        TODO("Not yet implemented")
    }

    override fun getCharacters(): LiveData<Resource<List<Character>?>> {
        mediator.addSource(getCharactersFromDataBase()) { salonsFound ->
            mediator.value = Resource(salonsFound)
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

        getCharacter(
            failure = { errorCode ->
                failuresFromWebApiLiveData.value = Resource(result = null, error = errorCode)
            }
        )

        return mediator
    }

    private fun getCharacter(failure: (errorCode: Int) -> Unit) {
        characterWebClient.getCharacters(
            completion = { charactersList ->
                charactersList?.data?.result?.let {
                    saveCharacters(it)
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