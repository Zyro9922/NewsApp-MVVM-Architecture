package com.alihasan.newsapp_mvvm_architecture.utils

import com.alihasan.newsapp_mvvm_architecture.data.model.Country
import com.alihasan.newsapp_mvvm_architecture.data.model.Language

object AppConstant {

    const val API_KEY = "0843ed33be504df5b1827c1ffeed161f"
    const val COUNTRY = "us"
    const val LANGUAGE = "en"
    const val TopHeadlineDatabaseUpdateInterval: Long = 4

    val COUNTRIES = listOf(
        Country("ae", "United Arab Emirates"),
        Country("ar", "Argentina"),
        Country("at", "Austria"),
        Country("be", "Belgium"),
        Country("bg", "Bulgaria"),
        Country("br", "Brazil"),
        Country("ca", "Canada"),
        Country("ch", "Switzerland"),
        Country("cn", "China"),
        Country("co", "Colombia"),
        Country("cu", "Cuba"),
        Country("cz", "Czechia"),
        Country("de", "Germany"),
        Country("eg", "Egypt"),
        Country("fr", "France"),
        Country("gb", "United Kingdom of Great Britain and Northern Ireland"),
        Country("gr", "Greece"),
        Country("hk", "Hong Kong"),
        Country("hu", "Hungary"),
        Country("id", "Indonesia"),
        Country("ie", "Ireland"),
        Country("il", "Israel"),
        Country("in", "India"),
        Country("it", "Italy"),
        Country("jp", "Japan"),
        Country("kr", "Korea"),
        Country("lt", "Lithuania"),
        Country("lv", "Latvia"),
        Country("ma", "Morocco"),
        Country("mx", "Mexico"),
        Country("my", "Malaysia"),
        Country("ng", "Nigeria"),
        Country("nl", "Netherlands"),
        Country("no", "Norway"),
        Country("nz", "New Zealand"),
        Country("ph", "Philippines"),
        Country("pl", "Poland"),
        Country("pt", "Portugal"),
        Country("ro", "Romania"),
        Country("rs", "Serbia"),
        Country("ru", "Russian Federation"),
        Country("sa", "Saudi Arabia"),
        Country("se", "Sweden"),
        Country("sg", "Singapore"),
        Country("si", "Slovenia"),
        Country("sk", "Slovakia"),
        Country("th", "Thailand"),
        Country("tr", "Turkiye"),
        Country("tw", "Taiwan, Province of China"),
        Country("ua", "Ukraine"),
        Country("us", "United States of America"),
        Country("ve", "Venezuela"),
        Country("za", "South Africa")
    )

    val LANGUAGES = listOf(
        Language("ar", "Arabic"),
        Language("de", "German"),
        Language("en", "English"),
        Language("fr", "French"),
        Language("he", "Hebrew"),
        Language("it", "Italian"),
        Language("nl", "Dutch"),
        Language("no", "Norwegian"),
        Language("pt", "Portuguese"),
        Language("ru", "Russian"),
        Language("sv", "Swedish"),
        Language("zh", "Chinese")
    )
}