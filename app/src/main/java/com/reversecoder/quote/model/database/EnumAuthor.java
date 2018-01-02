package com.reversecoder.quote.model.database;

import com.reversecoder.quote.R;
import com.reversecoder.quote.util.AppUtils;

public enum EnumAuthor {

    APJ_ABDUL_KALAM("A. P. J. Abdul Kalam", "October 15, 1931", "July 27, 2015", "Statesman", "Indian", AppUtils.getDrawableResourceId(R.drawable.apj_abdul_kalam), true),
    ALBERT_CAMUS("Albert Camus", "November 7, 1913", "January 4, 1960", "Philosopher", "French", AppUtils.getDrawableResourceId(R.drawable.albert_camus), true),
    ARISTOTLE("Aristotle", "384 BC", "322 BC", "Philosopher", "Greek", AppUtils.getDrawableResourceId(R.drawable.aristotle), true);

    private LitePalAuthor litePalAuthor;

    EnumAuthor(String authorName, String birthDate, String deathDate, String occupation, String nationality, int profileImage, boolean isAuthor) {
        this.litePalAuthor = LitePalDataHandler.insetAuthor(new LitePalAuthor(authorName, birthDate, deathDate, occupation, nationality, profileImage, isAuthor));
    }

    public LitePalAuthor getLitePalAuthor() {
        return litePalAuthor;
    }
}
