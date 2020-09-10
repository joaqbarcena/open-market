package org.joaqbarcena

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import org.joaqbarcena.ui.CountrySelectionFragment
import org.joaqbarcena.ui.SearchFragment
import org.joaqbarcena.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

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
        val fragmentToShow: Fragment =
            when(fragment){
                Fragments.COUNTRY_SELECTION -> CountrySelectionFragment.newInstance()
                Fragments.SEARCH -> SearchFragment.newInstance()
                //Fragments.PRODUCT ->
                else -> CountrySelectionFragment.newInstance()
            }

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.in_right, R.anim.out_left, R.anim.in_left, R.anim.out_right)
            .replace(R.id.container, fragmentToShow)
            .commitNow()
    }


}
