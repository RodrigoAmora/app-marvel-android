package br.com.rodrigoamora.marvellapp.ui.activity

import android.annotation.TargetApi
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.databinding.ActivityMainBinding
import br.com.rodrigoamora.marvellapp.factory.ShortcutFactory
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: NavigationView

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instantiateBinding()
        setupToolbarNavigationViewNavController()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun instantiateBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupToolbarNavigationViewNavController() {
        val toolbar = binding.appBarMain.toolbar
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView = binding.navView
        navView.itemIconTintList = null

        navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_list_characters, R.id.nav_comics
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
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
        }
    }
}