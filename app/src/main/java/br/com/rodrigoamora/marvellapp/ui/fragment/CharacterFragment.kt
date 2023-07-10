package br.com.rodrigoamora.marvellapp.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.extension.loadCircleImageWithGlide
import br.com.rodrigoamora.marvellapp.model.Character
import br.com.rodrigoamora.marvellapp.model.Comic
import br.com.rodrigoamora.marvellapp.ui.activity.CharacterActivity
import br.com.rodrigoamora.marvellapp.ui.activity.ComicActivity

class CharacterFragment: Fragment() {

    private lateinit var ivImageCharacter: ImageView
    private lateinit var tvDescriptionCharacter: TextView
    private lateinit var tvNameCharacter: TextView
    private lateinit var spComics: Spinner

    private lateinit var character: Character
    private lateinit var characterActivity: CharacterActivity
    private lateinit var comics: List<Comic>
    private var currentSelection: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_character, container, false)

        ivImageCharacter = root.findViewById(R.id.iv_image_character)
        tvDescriptionCharacter = root.findViewById(R.id.tv_description_character)
        tvNameCharacter = root.findViewById(R.id.tv_name_character)
        spComics = root.findViewById(R.id.sp_comics)
        spComics.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                currentSelection++
                if (currentSelection > 1) {
                    val intent = Intent(characterActivity, ComicActivity::class.java)
                    intent.putExtra("comic", comics[position])
                    startActivity(intent)
                }
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recoveryActivity()
        populateViews()
        getComicsByCharacterId()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentSelection = 0
    }
    override fun onStop() {
        super.onStop()
        currentSelection = 0
    }

    private fun recoveryActivity() {
        characterActivity = activity as CharacterActivity
    }

    private fun populateViews() {
        character = arguments?.getSerializable("character") as Character

        val imageCharacterURL = "${character.thumbnail.path}.${character.thumbnail.extension}"
        context?.let { ivImageCharacter.loadCircleImageWithGlide(it, imageCharacterURL) }

        tvDescriptionCharacter.text = character.description
        tvNameCharacter.text = character.name
    }

    private fun getComicsByCharacterId() {
        characterActivity.getComicsByCharacterId(character.id)
    }

    fun populateSpinner(comics: List<Comic>) {
        this.comics = comics

        val titles: MutableList<String> = mutableListOf()
        for (comic in comics) {
            titles.add(comic.title)
        }

        val arrayAdapter = context?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, titles) }
        spComics.adapter = arrayAdapter
    }
}