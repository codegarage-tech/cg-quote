package io.armcha.ribble.presentation.widget.navigation_view

import io.armcha.ribble.presentation.utils.extensions.emptyString

/**
 * Created by Chatikyan on 21.08.2017.
 */
sealed class NavigationId(val name: String = emptyString, val fullName: String = emptyString) {

    //For English
    object HOME : NavigationId("HOME")
    object FAVOURITE : NavigationId("FAVOURITE")
    object AMAZING_TODAY : NavigationId("AMAZING TODAY")
    object RATE_US : NavigationId("RATE US")
    object ABOUT : NavigationId("ABOUT")

    //For German
//    object HOME : NavigationId("ZUHAUSE")
//    object FAVOURITE : NavigationId("FAVORIT")
//    object RATE_US : NavigationId("BEWERTEN SIE UNS")
//    object AMAZING_TODAY : NavigationId("ANDERE APPS")
//    object ABOUT : NavigationId("ÜBER")
}