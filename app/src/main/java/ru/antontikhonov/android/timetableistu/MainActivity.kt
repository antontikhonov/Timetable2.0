package ru.antontikhonov.android.timetableistu

import android.content.SharedPreferences
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.inject
import ru.antontikhonov.android.timetableistu.databinding.ActivityMainBinding

const val EXCEPTION_MESSAGE = "there is no such menu item"
const val THEME_LOADED_TAG = "theme_loaded_tag"
const val GROUP_NUMBER_TAG = "group_number_tag"
const val THEME_IMAGE_NAME = "theme.png"

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentNavigator: FragmentNavigator
    private val preferences: SharedPreferences by inject()
    private val viewBinding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavigation()

        viewBinding.bottomNavigation.setOnItemSelectedListener {
            fragmentNavigator.navigateByItemId(it.itemId)
            true
        }
        if (savedInstanceState == null) {
            viewBinding.bottomNavigation.selectedItemId = R.id.timetable
        }
        if (preferences.getBoolean(THEME_LOADED_TAG, false)) {
            updateUi()
        }
    }

    private fun updateUi() {
        openFileInput(THEME_IMAGE_NAME).use { fos ->
            val bitmap = BitmapFactory.decodeStream(fos)
            viewBinding.imageView.setImageBitmap(bitmap)
            fos.close()
        }
    }

    fun restartActivity() {
        finish()
        startActivity(intent)
    }

    private fun initNavigation() {
        AppBarConfiguration(
            setOf(
                R.id.timetableFragment,
                R.id.chatFragment,
                R.id.newsFragment,
                R.id.settingsFragment
            )
        )
        (supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment)
            .navController.also { navController ->
                fragmentNavigator = FragmentNavigator(navController)
                viewBinding.bottomNavigation.setupWithNavController(navController)
            }
    }
}
