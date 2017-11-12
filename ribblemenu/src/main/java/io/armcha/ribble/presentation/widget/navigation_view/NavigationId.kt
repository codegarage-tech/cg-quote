package io.armcha.ribble.presentation.widget.navigation_view

import io.armcha.ribble.presentation.utils.extensions.emptyString

/**
 * Created by Chatikyan on 21.08.2017.
 */
sealed class NavigationId(val name: String = emptyString, val fullName: String = emptyString) {

    //For English
    object HOME : NavigationId("HOME")
    object FAVOURITE : NavigationId("FAVOURITE")
    object RATE_US : NavigationId("RATE US")
    object OTHER_APPS : NavigationId("OTHER APPS")
    object ABOUT : NavigationId("ABOUT")

    //For German
//    object HOME : NavigationId("ZUHAUSE")
//    object FAVOURITE : NavigationId("FAVORIT")
//    object RATE_US : NavigationId("BEWERTEN SIE UNS")
//    object OTHER_APPS : NavigationId("ANDERE APPS")
//    object ABOUT : NavigationId("ÜBER")
}