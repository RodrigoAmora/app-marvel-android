package br.com.rodrigoamora.marvellapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.rodrigoamora.marvellapp.model.Character
import br.com.rodrigoamora.marvellapp.repository.CharacterRepository
import br.com.rodrigoamora.marvellapp.repository.Resource

class CharacterViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    fun getCharacters(): LiveData<Resource<List<Character>?>> {
        return characterRepository.getCharacters()
    }

    fun getCharacterByName(name: String): LiveData<Resource<List<Character>?>> {
        return characterRepository.getCharacterByName(name)
    }

}