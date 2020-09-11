package org.joaqbarcena

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import org.joaqbarcena.ui.CountrySelectionFragment
import org.joaqbarcena.ui.ProductFragment
import org.joaqbarcena.ui.SearchFragment
import org.joaqbarcena.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {
    var first = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val mainViewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)

        if (savedInstanceState == null) {
            val currentSite = mainViewModel.site!!
            showFragment(if(currentSite.isEmpty())
                Fragments.COUNTRY_SELECTION else Fragments.SEARCH)
        }
    }


    fun showFragment(fragment: Fragments) {
        val fragmentToShow: Fragment = when(fragment){
            Fragments.COUNTRY_SELECTION -> CountrySelectionFragment.newInstance()
            Fragments.SEARCH -> SearchFragment.newInstance()
            Fragments.PRODUCT -> ProductFragment.newInstance()
        }

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.in_right, R.anim.out_left, R.anim.in_left, R.anim.out_right)
            .replace(R.id.container, fragmentToShow)
            .apply {
                if(!first && fragment != Fragments.COUNTRY_SELECTION) {
                    addToBackStack(null)
                } else first = false
            }
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }


}
