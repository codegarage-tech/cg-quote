package com.reversecoder.localechanger.data;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by Rashed on 08-Nov-17.
 */

public class Locales {

    //Types of Locale
    public static final List<Locale> getAllLocales() {
        return Arrays.asList(
                new Locale("en", "US"),
                new Locale("de", "Germany"),
                new Locale("bn", "Bangladesh"),
                new Locale("hi", "India")
        );
    }
}
