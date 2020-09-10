package org.joaqbarcena.repository

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager
import org.joaqbarcena.model.Site


const val COUNTRY_NAME = "COUNTRY_NAME"
const val COUNTRY_ID = "COUNTRY_ID"
const val COUNTRY_CURRENCY = "COUNTRY_CURRENCY"

class Repository(private val context: Context) {

    fun saveSite(site: Site) =
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(COUNTRY_ID, site.id)
            .putString(COUNTRY_NAME, site.name)
            .putString(COUNTRY_CURRENCY, site.defaultCurrencyId)
            .apply()

    fun loadSite() = PreferenceManager
        .getDefaultSharedPreferences(context)
        .let {
            Site(it.getString(COUNTRY_CURRENCY, "")!!,
                it.getString(COUNTRY_ID, "")!!,
                it.getString(COUNTRY_NAME, "")!!)
        }

}