package com.example.weatherforecast.model

import androidx.annotation.DrawableRes
import com.example.weatherforecast.R


enum class SupportedLanguage(
    val languageName: String,
    @DrawableRes val iconResId: Int,
    val languageValue: String
) {
    AFRIKAANS("Afrikaans", R.drawable.ic_language, "af"),
    ALBANIAN("Albanian", R.drawable.ic_language, "al"),
    ARABIC("Arabic", R.drawable.ic_language, "ar"),
    AZERBAIJANI("Azerbaijani", R.drawable.ic_language, "az"),
    BULGARIAN("Bulgarian", R.drawable.ic_language, "bg"),
    CATALAN("Catalan", R.drawable.ic_language, "ca"),
    CZECH("Czech", R.drawable.ic_language, "cz"),
    DANISH("Danish", R.drawable.ic_language, "da"),
    GERMAN("German", R.drawable.ic_language, "de"),
    GREEK("Greek", R.drawable.ic_language, "el"),
    ENGLISH("English", R.drawable.ic_language, "en"),
    BASQUE("Basque", R.drawable.ic_language, "eu"),
    PERSIAN("Persian (Farsi)", R.drawable.ic_language, "fa"),
    FINNISH("Finnish", R.drawable.ic_language, "fi"),
    FRENCH("French", R.drawable.ic_language, "fr"),
    GALICIAN("Galician", R.drawable.ic_language, "gl"),
    HEBREW("Hebrew", R.drawable.ic_language, "he"),
    HINDI("Hindi", R.drawable.ic_language, "hi"),
    CROATIAN("Croatian", R.drawable.ic_language, "hr"),
    HUNGARIAN("Hungarian", R.drawable.ic_language, "hu"),
    INDONESIAN("Indonesian", R.drawable.ic_language, "id"),
    ITALIAN("Italian", R.drawable.ic_language, "it"),
    JAPANESE("Japanese", R.drawable.ic_language, "ja"),
    KOREAN("Korean", R.drawable.ic_language, "kr"),
    LATVIAN("Latvian", R.drawable.ic_language, "la"),
    LITHUANIAN("Lithuanian", R.drawable.ic_language, "lt"),
    MACEDONIAN("Macedonian", R.drawable.ic_language, "mk"),
    NORWEGIAN("Norwegian", R.drawable.ic_language, "no"),
    DUTCH("Dutch", R.drawable.ic_language, "nl"),
    POLISH("Polish", R.drawable.ic_language, "pl"),
    PORTUGUESE("Portuguese", R.drawable.ic_language, "pt"),
    PORTUGUESE_BRAZIL("Português Brasil", R.drawable.ic_language, "pt_br"),
    ROMANIAN("Romanian", R.drawable.ic_language, "ro"),
    RUSSIAN("Russian", R.drawable.ic_language, "ru"),
    SWEDISH("Swedish", R.drawable.ic_language, "sv, se"),
    SLOVAK("Slovak", R.drawable.ic_language, "sk"),
    SLOVANIAN("Slovenian", R.drawable.ic_language, "sl"),
    SPANISH("Spanish", R.drawable.ic_language, "sp, es"),
    SERBIAN("Serbian", R.drawable.ic_language, "sr"),
    THAI("Thai", R.drawable.ic_language, "th"),
    TURKISH("Turkish", R.drawable.ic_language, "tr"),
    UKRAINIAN("Ukrainian", R.drawable.ic_language, "ua, uk"),
    VIETNAMESE("Vietnamese", R.drawable.ic_language, "vi"),
    CHINESE_SIMPLIFIED("Chinese Simplified", R.drawable.ic_language, "zh_cn"),
    CHINESE_TRADITIONAL("Chinese Traditional", R.drawable.ic_language, "zh_tw"),
    ZULU("Zulu", R.drawable.ic_language, "zu");

    companion object {
        fun getLanguageSpinnerItem(): Array<SpinnerItem> {
            return SupportedLanguage.values().map {
                SpinnerItem(it.languageName, it.iconResId)
            }.toTypedArray()
        }
    }
}