package br.com.rodrigoamora.marvellapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.rodrigoamora.marvellapp.model.Comic
import br.com.rodrigoamora.marvellapp.repository.ComicRepository
import br.com.rodrigoamora.marvellapp.repository.Resource

class ComicViewModel(
    private val comicRepository: ComicRepository
): ViewModel() {
    fun getComicsByCharacterId(characterId: Int): LiveData<Resource<List<Comic>?>> {
        return comicRepository.getComicsByCharacterId(characterId)
    }
}