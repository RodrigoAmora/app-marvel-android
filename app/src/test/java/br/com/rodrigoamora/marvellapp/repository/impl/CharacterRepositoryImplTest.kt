package br.com.rodrigoamora.marvellapp.repository.impl

import br.com.rodrigoamora.marvellapp.BuildConfig
import br.com.rodrigoamora.marvellapp.database.dao.CharacterDao
import br.com.rodrigoamora.marvellapp.model.Character
import br.com.rodrigoamora.marvellapp.model.Thumbnail
import br.com.rodrigoamora.marvellapp.network.retrofit.AppRetrofit
import br.com.rodrigoamora.marvellapp.network.retrofit.webclient.CharacterWebClient
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

internal class CharacterRepositoryImplTest {

    private lateinit var characterWebClient: CharacterWebClient
    private lateinit var dao: CharacterDao
    private lateinit var repository: CharacterRepositoryImpl

    @Before
    fun init() {
        characterWebClient = CharacterWebClient(AppRetrofit(BuildConfig.MARVEL_BASE_URL).characterService())
        dao = mockk<CharacterDao>()
        repository = CharacterRepositoryImpl(dao, characterWebClient)
    }

    @Test
    fun saveOneCharacterTest() {
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

        coEvery {
            dao.save(character)
        }.returns(Unit)

        repository.saveCharacter(character)

        coEvery {
            dao.save(character)
        }
    }

    @Test
    fun saveManyCharactersTest() {
        // CAPTAIN AMERICA
        val thumbnailCaptainAmerica =  Thumbnail(
            thumbnailId = 1,
            path = "http://i.annihil.us/u/prod/marvel/i/mg/3/50/537ba56d31087",
            extension = "jpg"
        )

        val descriptionCaptainAmerica = """
            Vowing to serve his country any way he could, young Steve Rogers took the super soldier 
            serum to become America's one-man army. 
            Fighting for the red, white and blue for over 60 years, Captain America is the living, 
            breathing symbol of freedom and liberty.
            """
        val captainAmerica = Character(
            id = 1,
            name = "Captain America",
            description = descriptionCaptainAmerica,
            thumbnail = thumbnailCaptainAmerica
        )

        // HULK
        val thumbnailHulk =  Thumbnail(
            thumbnailId = 1,
            path = "http://i.annihil.us/u/prod/marvel/i/mg/5/a0/538615ca33ab0",
            extension = "jpg"
        )

        val descriptionHulk = """
            Caught in a gamma bomb explosion while trying to save the life of a teenager,
            Dr. Bruce Banner was transformed into the incredibly powerful creature called the Hulk.
            An all too often misunderstood hero, the angrier the Hulk gets, the stronger the Hulk gets.
            """
        val hulk = Character(
            id = 1,
            name = "Hulk",
            description = descriptionHulk,
            thumbnail = thumbnailHulk
        )

        // IRON MAN
        val thumbnailIronMan =  Thumbnail(
            thumbnailId = 1,
            path = "http://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55",
            extension = "jpg"
        )

        val descriptionIronMan = """
            Wounded, captured and forced to build a weapon by his enemies, billionaire industrialist 
            Tony Stark instead created an advanced suit of armor to save his life and escape captivity. 
            Now with a new outlook on life, Tony uses his money and intelligence to make the world a safer, 
            better place as Iron Man.
            """
        val ironMan = Character(
            id = 1,
            name = "Iron Man",
            description = descriptionIronMan,
            thumbnail = thumbnailIronMan
        )

        // THOR
        val thumbnailThor =  Thumbnail(
            thumbnailId = 1,
            path = "http://i.annihil.us/u/prod/marvel/i/mg/d/d0/5269657a74350",
            extension = "jpg"
        )

        val descriptionThor = """
            As the Norse God of thunder and lightning, Thor wields one of the greatest weapons ever made, 
            the enchanted hammer Mjolnir. While others have described Thor as an over-muscled, 
            oafish imbecile, he's quite smart and compassionate. 
            He's self-assured, and he would never, ever stop fighting for a worthwhile cause.
            """
        val thor = Character(
            id = 1,
            name = "Iron Man",
            description = descriptionThor,
            thumbnail = thumbnailThor
        )

        val avengers = mutableListOf<Character>()
        avengers.add(captainAmerica)
        avengers.add(hulk)
        avengers.add(ironMan)
        avengers.add(thor)

        coEvery {
            dao.save(avengers)
        }.returns(Unit)

        repository.saveCharacters(avengers)

        coEvery {
            dao.save(avengers)
        }
    }
}