package br.com.rodrigoamora.marvellapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.model.Comic
import br.com.rodrigoamora.marvellapp.util.FragmentUtil

class ComicActivity: AppCompatActivity() {

    private val comicFragment: ComicFragment by lazy {
        ComicFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        val comic = intent?.getSerializableExtra("comic") as Comic
        comic.let {
            val bundle = Bundle()
            bundle.putSerializable("comic", it)
            changeFragment(comicFragment, bundle, false)
        }
    }

    fun changeFragment(fragment: Fragment, bundle: Bundle?, backstack: Boolean) {
        FragmentUtil.changeFragment(R.id.container,
            fragment,
            supportFragmentManager,
            backstack,
            bundle)
    }
}