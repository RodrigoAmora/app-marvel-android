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
import br.com.rodrigoamora.marvellapp.model.Comic

class ComicFragment: Fragment() {

    private lateinit var tvTitleComic: TextView
    private lateinit var tvDescriptionComic: TextView
    private lateinit var ivImageComic: ImageView

    private lateinit var comic: Comic

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_comic, container, false)

        tvTitleComic = root.findViewById(R.id.tv_title_comic)
        tvDescriptionComic = root.findViewById(R.id.tv_description_comic)
        ivImageComic = root.findViewById(R.id.iv_image_comic)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateViews()
    }

    private fun populateViews() {
        comic = arguments?.getSerializable("comic") as Comic

        tvTitleComic.text = comic.title
        tvDescriptionComic.text = comic.description

        val imageCharacterURL = "${comic.thumbnail.path}.${comic.thumbnail.extension}"
        context?.let { ivImageComic.loadImageWithGlide(it, imageCharacterURL) }
    }

}