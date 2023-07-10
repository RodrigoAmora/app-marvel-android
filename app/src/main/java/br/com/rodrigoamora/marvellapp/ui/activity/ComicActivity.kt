package br.com.rodrigoamora.marvellapp.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.model.Comic
import br.com.rodrigoamora.marvellapp.util.FragmentUtil
import com.google.android.material.navigation.NavigationView

class ComicActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout

    private val comicFragment: ComicFragment by lazy {
        ComicFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        createToolbarAndNavigationView()
        goToComicFragmentOrComicsFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_characters -> {

            }
        }

        drawer.closeDrawer(GravityCompat.START)

        return true
    }

    private fun createToolbarAndNavigationView() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)

        val toggle = ActionBarDrawerToggle(
            this,
            drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.itemIconTintList = null
    }

    fun changeFragment(fragment: Fragment, bundle: Bundle?, backstack: Boolean) {
        FragmentUtil.changeFragment(R.id.container,
            fragment,
            supportFragmentManager,
            backstack,
            bundle)
    }
    private fun goToComicFragmentOrComicsFragment() {
        val comic: Comic = intent?.getSerializableExtra("comic") as Comic
        if (comic != null) {
            val bundleComic = Bundle()
            bundleComic.putSerializable("comic", comic)
            changeFragment(comicFragment, bundleComic, false)
        } else {

        }
    }
}