package br.com.rodrigoamora.marvellapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.rodrigoamora.marvellapp.databinding.FragmentComicBinding
import br.com.rodrigoamora.marvellapp.extension.loadImageWithGlide
import br.com.rodrigoamora.marvellapp.model.Comic


class ComicFragment: Fragment() {

    private var _binding: FragmentComicBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvTitleComic: TextView
    private lateinit var tvDescriptionComic: TextView
    private lateinit var ivImageComic: ImageView

    private lateinit var comic: Comic

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this._binding = FragmentComicBinding.inflate(inflater, container, false)
        val root: View = this.binding.root

        this.tvTitleComic = this.binding.tvTitleComic
        this.tvDescriptionComic = this.binding.tvDescriptionComic
        this.ivImageComic = this.binding.ivImageComic

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.populateViews()
    }

    private fun populateViews() {
        this.comic = arguments?.getSerializable("comic") as Comic

        this.tvTitleComic.text = this.comic.title
        this.tvDescriptionComic.text = this.comic.description

        val imageComicURL = this.comic.thumbnail.formatURL()
        context?.let { ivImageComic.loadImageWithGlide(it, imageComicURL) }
    }

}