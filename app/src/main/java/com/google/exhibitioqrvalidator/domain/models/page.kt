package com.google.exhibitioqrvalidator.domain.models

import androidx.annotation.DrawableRes
import com.google.exhibitioqrvalidator.R

data class Page(
    val title:String,
    val description:String,
    @DrawableRes val image:Int
)
val pages = listOf(
    Page(
        title = "Lorem Ipsum simply Dummy",
        description = "Use windowSplashScreenBackground to fill the background with a single black color. Set the values of postSplashScreenTheme to the theme that the Activity should use and windowSplashScreenAnimatedIcon to a drawable or animated drawable",
        image = R.drawable.onboarding1
    ),
    Page(
        title = "Lorem Ipsum simply Dummy 3",
        description = " to fill the background with a single black color. Set the values of postSplashScreenTheme to the theme that the Activity should use and windowSplashScreenAnimatedIcon to a drawable or animated drawable",
        image = R.drawable.onboarding2
    ),
    Page(
        title = "Lorem Ipsum simply Dummy 3",
        description = "Use Hello lInus. to Set the values of postSplashScreenTheme to the theme that the Activity should use and windowSplashScreenAnimatedIcon to a drawable or animated drawable",
        image = R.drawable.onboarding3
    ),
)