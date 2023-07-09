package br.com.rodrigoamora.marvellapp.repository

import androidx.lifecycle.LiveData
import br.com.rodrigoamora.marvellapp.model.Comic

interface ComicRepository {
    fun getComicsByCharacterId(characterId: Int): LiveData<Resource<List<Comic>?>>
}