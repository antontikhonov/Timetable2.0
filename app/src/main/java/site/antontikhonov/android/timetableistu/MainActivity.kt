package site.antontikhonov.android.timetableistu

import android.content.SharedPreferences
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject
import site.antontikhonov.android.timetableistu.databinding.ActivityMainBinding

const val EXCEPTION_MESSAGE = "there is no such menu item"
const val THEME_LOADED_TAG = "theme_loaded_tag"
const val THEME_IMAGE_NAME = "theme_image.png"

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentNavigator: FragmentNavigator
    private val preferences: SharedPreferences by inject()
    private val bottomNavigationView: BottomNavigationView by lazy { findViewById(R.id.bottom_navigation) }
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        initNavigation()

        bottomNavigationView.setOnItemSelectedListener {
            fragmentNavigator.navigateByItemId(it.itemId)
            true
        }
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.timetable
        }

        if (preferences.getBoolean(THEME_LOADED_TAG, false)) {
            updateBackground()
        }
    }

    fun updateBackground() {
        val fis = openFileInput(THEME_IMAGE_NAME)
        val bitmap = BitmapFactory.decodeStream(fis)
        viewBinding.imageView.setImageBitmap(bitmap)
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