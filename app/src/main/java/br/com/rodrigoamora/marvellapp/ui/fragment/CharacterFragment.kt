package br.com.rodrigoamora.marvellapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.extension.loadImageWithGlide
import br.com.rodrigoamora.marvellapp.model.Character
import br.com.rodrigoamora.marvellapp.ui.activity.CharacterActivity

class CharacterFragment: Fragment() {

    private lateinit var ivImageCharacter: ImageView
    private lateinit var tvDescriptionCharacter: TextView
    private lateinit var tvNameCharacter: TextView

    private lateinit var characterActivity: CharacterActivity

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(
            R.layout.fragment_character,
            container,
            false
        )

        ivImageCharacter = root.findViewById(R.id.iv_image_character)
        tvDescriptionCharacter = root.findViewById(R.id.tv_description_character)
        tvNameCharacter = root.findViewById(R.id.tv_name_character)

        return  root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recoveryActivity()
        populateViews()
    }

    private fun recoveryActivity() {
        characterActivity = activity as CharacterActivity
    }

    private fun populateViews() {
        val character = arguments?.getSerializable("character") as Character

        val imageCharacterURL = "${character.thumbnail.path}.${character.thumbnail.extension}"
        ivImageCharacter.loadImageWithGlide(characterActivity, imageCharacterURL)

        tvDescriptionCharacter.text = character.description
        tvNameCharacter.text = character.name
    }

}