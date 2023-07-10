package br.com.rodrigoamora.marvellapp.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.model.Comic

class ComicFragment: Fragment() {

    private lateinit var tvTitleComic: TextView

    private lateinit var comic: Comic

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_comic, container, false)

        tvTitleComic = root.findViewById(R.id.tv_title_comic)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateViews()
    }

    private fun populateViews() {
        comic = arguments?.getSerializable("comic") as Comic

        //val imageCharacterURL = "${character.thumbnail.path}.${character.thumbnail.extension}"
        //context?.let { ivImageCharacter.loadCircleImageWithGlide(it, imageCharacterURL) }

        tvTitleComic.text = comic.title

    }
}