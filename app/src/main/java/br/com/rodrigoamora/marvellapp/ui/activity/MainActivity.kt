package br.com.rodrigoamora.marvellapp.ui.activity

import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.databinding.ActivityMainBinding
import br.com.rodrigoamora.marvellapp.factory.ShortcutFactory
import com.google.android.material.navigation.NavigationView


class MainActivity: AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: NavigationView

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.instantiateBinding()
        this.setupToolbarNavigationViewNavController()
        this.createShortcut()
        this.checkOptionInIntent()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_about -> {
                this.navController.navigate(R.id.nav_about)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return this.navController.navigateUp(this.appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun instantiateBinding() {
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)
    }

    private fun setupToolbarNavigationViewNavController() {
        val toolbar = this.binding.appBarMain.toolbar
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = this.binding.drawerLayout
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        this.navView = this.binding.navView
        this.navView.itemIconTintList = null

        this.navController = findNavController(R.id.nav_host_fragment_content_main)

        this.appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_list_characters, R.id.nav_comics
            ), drawerLayout
        )

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)

        this.navView.setupWithNavController(navController)
    }

    private fun createShortcut() {
        val shortcutManager = getSystemService(ShortcutManager::class.java)
        val shortLabels = arrayOf<String>(getString(R.string.shortcut_characters),
                                                       getString(R.string.shortcut_comics))

        val icons = arrayOf<Int>(R.drawable.ic_menu_characters,
                                            R.drawable.ic_menu_comics)

        val options = arrayOf<String> (getString(R.string.shortcut_characters),
                                                    getString(R.string.shortcut_comics))

        val shortcutInfoList: List<ShortcutInfo?> = ShortcutFactory.createShortcutInfo(this,
            shortLabels,
            icons,
            options)

        shortcutManager.dynamicShortcuts = shortcutInfoList
    }

    private fun checkOptionInIntent() {
        val option = intent.getStringExtra("option")
        val destination = when(option) {
            "characters" -> R.id.action_nav_list_characters_to_nav_character
            else -> {
                R.id.action_nav_list_characters_to_nav_character
            }
        }

        if (!option.isNullOrEmpty()) {
            val bundle = Bundle()
            bundle.putString("option", option)
            Navigation.findNavController(this.navView)
                .navigate(destination, bundle)
        }
    }
}
