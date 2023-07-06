package br.com.rodrigoamora.marvellapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.rodrigoamora.marvellapp.model.Character
import br.com.rodrigoamora.marvellapp.repository.CharacterRepository
import br.com.rodrigoamora.marvellapp.repository.Resource

class CharacterViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    fun getCharacters(offset: Int): LiveData<Resource<List<Character>?>> {
        return characterRepository.getCharacters(offset)
    }

    fun getCharacterByName(name: String): LiveData<Resource<List<Character>?>> {
        return characterRepository.getCharacterByName(name)
    }

}