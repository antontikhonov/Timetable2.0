package site.antontikhonov.android.timetableistu

import android.content.SharedPreferences
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

const val EXCEPTION_MESSAGE = "there is no such menu item"
const val THEME_LOADED_TAG = "theme_loaded_tag"
const val THEME_IMAGE_NAME = "theme.png"

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentNavigator: FragmentNavigator
    private val preferences: SharedPreferences by inject()
    private val bottomNavigationView: BottomNavigationView by lazy { findViewById(R.id.bottom_navigation) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavigation()

        bottomNavigationView.setOnItemSelectedListener {
            fragmentNavigator.navigateByItemId(it.itemId)
            true
        }
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.timetable
        }
        if (preferences.getBoolean(THEME_LOADED_TAG, false)) {
            updateUi()
        }
    }

    private fun updateUi() {
        openFileInput(THEME_IMAGE_NAME).use { fos ->
            val bitmap = BitmapFactory.decodeStream(fos)
            findViewById<ImageView>(R.id.imageView).setImageBitmap(bitmap)
            fos.close()
        }
    }

    fun restartActivity() {
        finish()
        startActivity(intent)
    }

    private fun initNavigation() {
        (supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment)
            .navController.also {
                AppBarConfiguration(
                    setOf(
                        R.id.timetableFragment,
                        R.id.chatFragment,
                        R.id.newsFragment,
                        R.id.settingsFragment
                    )
                )
                fragmentNavigator = FragmentNavigator(it)
                bottomNavigationView.setupWithNavController(it)
            }
    }
}