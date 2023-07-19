package br.com.rodrigoamora.marvellapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.databinding.FragmentCharacterBinding
import br.com.rodrigoamora.marvellapp.extension.loadCircleImageWithGlide
import br.com.rodrigoamora.marvellapp.model.Character
import br.com.rodrigoamora.marvellapp.model.Comic
import br.com.rodrigoamora.marvellapp.ui.activity.MainActivity
import br.com.rodrigoamora.marvellapp.ui.viewmodel.ComicViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterFragment: BaseFragment() {

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!

    private val comicViewModel: ComicViewModel by viewModel()

    private lateinit var ivImageCharacter: ImageView
    private lateinit var tvDescriptionCharacter: TextView
    private lateinit var tvNameCharacter: TextView
    private lateinit var spComics: Spinner

    private lateinit var character: Character
    private lateinit var mainActivity: MainActivity
    private val comics: MutableList<Comic> = mutableListOf()
    private val titles: MutableList<String> = mutableListOf()
    private var currentSelection: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        ivImageCharacter = binding.ivImageCharacter
        tvDescriptionCharacter = binding.tvDescriptionCharacter
        tvNameCharacter = binding.tvNameCharacter

        spComics = binding.spComics
        spComics.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                currentSelection++
                if (currentSelection > 1) {
                    viewComic(comics[position-1])
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
        //_binding = null
    }

    private fun recoveryActivity() {
        mainActivity = activity as MainActivity
    }

    private fun populateViews() {
        character = arguments?.getSerializable("character") as Character

        val imageCharacterURL = "${character.thumbnail.path}.${character.thumbnail.extension}"
        context?.let { ivImageCharacter.loadCircleImageWithGlide(it, imageCharacterURL) }

        tvDescriptionCharacter.text = character.description
        tvNameCharacter.text = character.name
    }

    private fun getComicsByCharacterId() {
        val id = character.id
        comicViewModel.getComicsByCharacterId(id).observe(mainActivity,
            Observer{ comics ->
                comics.result?.let {
                    currentSelection = 0
                    populateSpinner(it)
                }
                comics.error?.let {
                    showError(mainActivity, it)
                }
            }
        )
    }

    private fun populateSpinner(comics: List<Comic>) {
        this.comics.clear()
        this.comics.addAll(comics)

        titles.clear()
        titles.add(mainActivity.getString(R.string.comic_label))
        for (comic in comics) {
            titles.add(comic.title)
        }

        val arrayAdapter = context?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, titles) }
        spComics.adapter = arrayAdapter
    }

    private fun viewComic(comic: Comic) {
        val comicBundle = Bundle()
        comicBundle.putSerializable("comic", comic)
        Navigation.findNavController(spComics)
            .navigate(R.id.action_nav_character_to_nav_comic, comicBundle)

    }
}