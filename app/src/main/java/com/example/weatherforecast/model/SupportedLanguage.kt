package com.example.weatherforecast.model

import androidx.annotation.DrawableRes
import com.example.weatherforecast.R

enum class SupportedLanguage(
    val languageName: String,
    @DrawableRes val iconResId: Int,
    val languageValue: String
) {
    AFRIKAANS("Afrikaans", R.drawable.flag_of_african, "af"),
    ALBANIAN("Albanian", R.drawable.flag_of_albania, "al"),
    ARABIC("Arabic", R.drawable.flag_of_arabic, "ar"),
    AZERBAIJANI("Azerbaijani", R.drawable.flag_of_azerbaijan, "az"),
    BULGARIAN("Bulgarian", R.drawable.flag_of_bulgaria, "bg"),
    CATALAN("Catalan", R.drawable.flag_of_andorra, "ca"),
    CZECH("Czech", R.drawable.flag_of_czech, "cz"),
    DANISH("Danish", R.drawable.flag_of_denmark, "da"),
    GERMAN("German", R.drawable.flag_of_germany, "de"),
    GREEK("Greek", R.drawable.flag_of_greece, "el"),
    ENGLISH("English", R.drawable.flag_of_united_kingdom, "en"),
    BASQUE("Basque", R.drawable.flag_of_spain, "eu"),
    PERSIAN("Persian (Farsi)", R.drawable.flag_of_iran, "fa"),
    FINNISH("Finnish", R.drawable.flag_of_finland, "fi"),
    FRENCH("French", R.drawable.flag_of_france, "fr"),
    GALICIAN("Galician", R.drawable.flag_of_spain, "gl"),
    HEBREW("Hebrew", R.drawable.flag_of_israel, "he"),
    HINDI("Hindi", R.drawable.flag_of_india, "hi"),
    CROATIAN("Croatian", R.drawable.flag_of_croatia, "hr"),
    HUNGARIAN("Hungarian", R.drawable.flag_of_hungary, "hu"),
    INDONESIAN("Indonesian", R.drawable.flag_of_indonesia, "id"),
    ITALIAN("Italian", R.drawable.flag_of_italy, "it"),
    JAPANESE("Japanese", R.drawable.flag_of_japan, "ja"),
    KOREAN("Korean", R.drawable.flag_of_south_korea, "kr"),
    LATVIAN("Latvian", R.drawable.flag_of_latvia, "la"),
    LITHUANIAN("Lithuanian", R.drawable.flag_of_lithuania, "lt"),
    MACEDONIAN("Macedonian", R.drawable.flag_of_north_macedonia, "mk"),
    NORWEGIAN("Norwegian", R.drawable.flag_of_norway, "no"),
    DUTCH("Dutch", R.drawable.flag_of_netherlands, "nl"),
    POLISH("Polish", R.drawable.flag_of_poland, "pl"),
    PORTUGUESE("Portuguese", R.drawable.flag_of_portugal, "pt"),
    PORTUGUESE_BRAZIL("Português Brasil", R.drawable.flag_of_brazil, "pt_br"),
    ROMANIAN("Romanian", R.drawable.flag_of_romania, "ro"),
    RUSSIAN("Russian", R.drawable.flag_of_russia, "ru"),
    SWEDISH("Swedish", R.drawable.flag_of_sweden, "sv, se"),
    SLOVAK("Slovak", R.drawable.flag_of_slovakia, "sk"),
    SLOVENIAN("Slovenian", R.drawable.flag_of_slovenia, "sl"),
    SPANISH("Spanish", R.drawable.flag_of_spain, "sp, es"),
    SERBIAN("Serbian", R.drawable.flag_of_serbia, "sr"),
    THAI("Thai", R.drawable.flag_of_thailand, "th"),
    TURKISH("Turkish", R.drawable.flag_of_turkey, "tr"),
    UKRAINIAN("Ukrainian", R.drawable.flag_of_ukraine, "ua, uk"),
    VIETNAMESE("Vietnamese", R.drawable.flag_of_vietnam, "vi"),
    CHINESE_SIMPLIFIED("Chinese Simplified", R.drawable.flag_of_reoples_republic_of_china, "zh_cn"),
    CHINESE_TRADITIONAL("Chinese Traditional", R.drawable.flag_of_reoples_republic_of_china, "zh_tw"),
    ZULU("Zulu", R.drawable.flag_of_south_africa, "zu");

    companion object {
        fun getLanguageSpinnerItem(): Array<SpinnerItem> {
            return SupportedLanguage.values().map {
                SpinnerItem(it.languageName, it.iconResId)
            }.toTypedArray()
        }
    }
}