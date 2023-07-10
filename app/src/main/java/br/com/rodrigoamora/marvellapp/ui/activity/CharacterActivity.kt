package br.com.rodrigoamora.marvellapp.ui.activity

import android.annotation.TargetApi
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.factory.ShortcutFactory
import br.com.rodrigoamora.marvellapp.model.Character
import br.com.rodrigoamora.marvellapp.ui.fragment.CharacterFragment
import br.com.rodrigoamora.marvellapp.ui.fragment.ListCharactersFragment
import br.com.rodrigoamora.marvellapp.ui.viewmodel.CharacterViewModel
import br.com.rodrigoamora.marvellapp.ui.viewmodel.ComicViewModel
import br.com.rodrigoamora.marvellapp.util.FragmentUtil
import br.com.rodrigoamora.marvellapp.util.NetworkUtil
import com.google.android.material.navigation.NavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel


class CharacterActivity : BaseActivity(),
                            NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout

    private val characterViewModel: CharacterViewModel by viewModel()
    private val comicViewModel: ComicViewModel by viewModel()

    private lateinit var characterFragment: CharacterFragment
    private val listCharactersFragment: ListCharactersFragment by lazy {
        ListCharactersFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        createToolbarAndNavigationView()
        changeFragment(listCharactersFragment, null, false)
        createShortcut()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            val count = supportFragmentManager.backStackEntryCount
            if (count == 0) {
                finish()
                moveTaskToBack(true)
            } else {
                supportFragmentManager.popBackStack()
            }
        }
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

    fun getCharacters(offset: Int) {
        characterViewModel.getCharacters(offset).observe(this,
            Observer { characters ->
                characters.result?.let {
                    listCharactersFragment.populateRecyclerView(it)
                }
                characters.error?.let { showError(it) }

                if (!NetworkUtil.checkConnection(this)) {
                    showToast(getString(R.string.error_no_internet))
                }
            }
        )
    }

    fun getCharacterByName(name: String) {
        if (NetworkUtil.checkConnection(this)) {
            characterViewModel.getCharacterByName(name).observe(this,
                Observer { characters ->
                    characters.result?.let {
                        listCharactersFragment.replaceRecyclerView(it)
                    }
                    characters.error?.let { showError(it) }
                }
            )
        } else {
            showToast(getString(R.string.error_no_internet))
        }
    }

    fun getComicsByCharacterId(characterId: Int) {
        comicViewModel.getComicsByCharacterId(characterId).observe(this,
            Observer{ comics ->
                comics.result?.let {
                    characterFragment.populateSpinner(it)
                }
                comics.error?.let {
                    showError(it)
                }
            }
        )
    }

    fun viewDetails(character: Character) {
        characterFragment = CharacterFragment()
        val bundle = Bundle()
        bundle.putSerializable("character", character)

        changeFragment(characterFragment, bundle, true)
    }

    @TargetApi(26)
    private fun createShortcut() {
        if (Build.VERSION.SDK_INT >= 26) {
            val shortcutManager = getSystemService(ShortcutManager::class.java)
            val shortLabels = arrayOf<String>(
                getString(R.string.shortcut_characters),
                getString(R.string.shortcut_comics)
            )

            val icons = arrayOf<Int>(
                R.drawable.ic_menu_characters,
                R.drawable.ic_menu_comics
            )
            val shortcutInfoList: List<ShortcutInfo?> = ShortcutFactory.createShortcutInfo(this,
                shortLabels,
                icons)
            shortcutManager.dynamicShortcuts = shortcutInfoList
        } else {
            changeFragment(listCharactersFragment, null, false)
        }
    }
}