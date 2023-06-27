package br.com.rodrigoamora.marvellapp.repository

import androidx.lifecycle.LiveData
import br.com.rodrigoamora.marvellapp.model.Character

interface CharacterRepository {
    fun deleteCharacter(character: Character)
    fun getCharacters(): LiveData<Resource<List<Character>?>>
    fun getCharacterByName(name: String): LiveData<Resource<List<Character>?>>
    fun saveCharacter(character: Character)
    fun saveCharacters(charactersList: List<Character>)
}