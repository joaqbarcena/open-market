package org.joaqbarcena

import androidx.fragment.app.Fragment
import org.joaqbarcena.ui.CountrySelectionFragment
import org.joaqbarcena.ui.SearchFragment
import java.util.*

//Background fetch statuses
enum class Status {
    START, LOADING, DONE, NO_CONNECTED, CONNECTED, INTERNAL_ERROR
}

enum class Fragments {
    COUNTRY_SELECTION, SEARCH, PRODUCT
}


fun String.toFlagEmoji(): String {
    Locale.getISOCountries()
        .map { Locale("", it) }
        .find { it.displayName == this }
        ?.run {
            val firstLetter = Character.codePointAt(country, 0) - 0x41 + 0x1F1E6
            val secondLetter = Character.codePointAt(country, 1) - 0x41 + 0x1F1E6
            return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
        }
    //TODO negrada hecha, resolver bien
    when(this){
        "Mexico" -> "MX"
        "Dominicana" -> "DO"
        else -> ""
    }.let {
        if(it.isNotEmpty()){
            val firstLetter = Character.codePointAt(it, 0) - 0x41 + 0x1F1E6
            val secondLetter = Character.codePointAt(it, 1) - 0x41 + 0x1F1E6
            return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
        }
    }
    return ""
}



