package br.com.rodrigoamora.marvellapp.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.ui.fragment.ListCharactersFragment
import br.com.rodrigoamora.marvellapp.ui.viewmodel.CharacterViewModel
import br.com.rodrigoamora.marvellapp.util.FragmentUtil
import br.com.rodrigoamora.marvellapp.util.NetworkUtil
import com.google.android.material.navigation.NavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel


class CharacterActivity : AppCompatActivity(),
                            NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout

    private val characterViewModel: CharacterViewModel by viewModel()

    private val listCharactersFragment: ListCharactersFragment by lazy {
        ListCharactersFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        createToolbarAndNavigationView()
        changeFragment(listCharactersFragment, null, false)
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

    private fun changeFragment(fragment: Fragment, bundle: Bundle?, backstack: Boolean) {
        FragmentUtil.changeFragment(R.id.container, fragment,
            supportFragmentManager, backstack, bundle)
    }

    fun getCharacters() {
        if (NetworkUtil.checkConnection(this)) {
            characterViewModel.getCharacters().observe(this,
                Observer { charactersList ->
                    charactersList.result?.let { listCharactersFragment.populateRecyclerView(it) }
                }
            )
        } else {
            Toast.makeText(this, getString(R.string.error_no_internet), Toast.LENGTH_LONG).show()
        }
    }

    fun getCharacterByName(name: String) {
        if (NetworkUtil.checkConnection(this)) {
            characterViewModel.getCharacterByName(name).observe(this,
                Observer { charactersList ->
                    charactersList.result?.let { listCharactersFragment.populateRecyclerView(it) }
                }
            )
        } else {
            Toast.makeText(this, getString(R.string.error_no_internet), Toast.LENGTH_LONG).show()
        }
    }

}