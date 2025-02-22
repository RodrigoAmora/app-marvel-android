package br.com.rodrigoamora.marvellapp.ui.fragment

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
    private val comics: MutableList<Comic> = mutableListOf()
    private val titles: MutableList<String> = mutableListOf()
    private val mainActivity: MainActivity by lazy {
        activity as MainActivity
    }

    private var currentSelection: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this._binding = FragmentCharacterBinding.inflate(inflater, container, false)
        val root: View = this.binding.root

        this.ivImageCharacter = this.binding.ivImageCharacter
        this.tvDescriptionCharacter = this.binding.tvDescriptionCharacter
        this.tvNameCharacter = this.binding.tvNameCharacter

        this.spComics = this.binding.spComics
        this.spComics.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
        this.populateViews()
        this.loadSpinner()
        this.getComicsByCharacterId()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }

    private fun populateViews() {
        this.character = arguments?.getSerializable("character", Character::class.java)!!

        val imageCharacterURL = this.character.thumbnail.formatURL()
        this.ivImageCharacter.loadCircleImageWithGlide(this.mainActivity, imageCharacterURL)

        val description = this.character.description
        if (description.isNullOrEmpty()) {
            this.tvDescriptionCharacter.text = getString(R.string.character_without_description)
        } else {
            this.tvDescriptionCharacter.text = description
        }

        this.tvNameCharacter.text = this.character.name
    }

    private fun getComicsByCharacterId() {
        val id = this.character.id
        this.comicViewModel.getComicsByCharacterId(id).observe(mainActivity,
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

    private fun loadSpinner() {
        this.titles.add(mainActivity.getString(R.string.comic_label))
        val arrayAdapter = context?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, titles) }
        this.spComics.adapter = arrayAdapter
    }

    private fun populateSpinner(comics: List<Comic>) {
        this.comics.clear()
        this.comics.addAll(comics)

        this.titles.clear()
        this.titles.add(mainActivity.getString(R.string.comic_label))
        for (comic in this.comics) {
            this.titles.add(comic.title)
        }

        val arrayAdapter = this.mainActivity.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, titles) }
        this.spComics.adapter = arrayAdapter
    }

    private fun viewComic(comic: Comic) {
        val comicBundle = Bundle()
        comicBundle.putSerializable("comic", comic)
        Navigation.findNavController(this.spComics)
                   .navigate(R.id.action_nav_character_to_nav_comic, comicBundle)

    }
}
