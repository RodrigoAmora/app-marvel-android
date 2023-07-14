package br.com.rodrigoamora.marvellapp.repository.impl

import br.com.rodrigoamora.marvellapp.database.dao.CharacterDao
import br.com.rodrigoamora.marvellapp.model.Character
import br.com.rodrigoamora.marvellapp.model.Thumbnail
import br.com.rodrigoamora.marvellapp.network.retrofit.AppRetrofit
import br.com.rodrigoamora.marvellapp.network.retrofit.webclient.CharacterWebClient
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Test

internal class CharacterRepositoryImplTest {

    @Test
    fun saveCharacter() {
        // Arrange
        val characterWebClient = CharacterWebClient(AppRetrofit().characterService())

        val dao = mockk<CharacterDao>()
        val repository = CharacterRepositoryImpl(dao, characterWebClient)

        val thumbnail =  Thumbnail(
            thumbnailId = 1,
            path = "http://i.annihil.us/u/prod/marvel/i/mg/5/a0/538615ca33ab0",
            extension = "jpg"
        )

        val description = """
            Caught in a gamma bomb explosion while trying to save the life of a teenager,
            Dr. Bruce Banner was transformed into the incredibly powerful creature called the Hulk.
            An all too often misunderstood hero, the angrier the Hulk gets, the stronger the Hulk gets.
            """
        val character = Character(
            id = 1,
            name = "Hulk",
            description = description,
            thumbnail = thumbnail
        )

        val list = mutableListOf<Character>()
        list.add(character)

        coEvery {
            dao.save(character)
        }.returns(Unit)

        // Act
        repository.saveCharacter(character)

        coEvery {
            dao.save(character)
        }
    }

    @Test
    fun saveCharacters() {
    }
}