package com.reversecoder.quote.model.database;

import com.reversecoder.quote.R;
import com.reversecoder.quote.util.AppUtils;

public enum EnumAuthor {

    APJ_ABDUL_KALAM("A. P. J. Abdul Kalam", "October 15, 1931", "July 27, 2015", "Statesman", "Indian", AppUtils.getDrawableResourceId(R.drawable.apj_abdul_kalam), true),
    ALBERT_CAMUS("Albert Camus", "November 7, 1913", "January 4, 1960", "Philosopher", "French", AppUtils.getDrawableResourceId(R.drawable.albert_camus), true),
    ARISTOTLE("Aristotle", "384 BC", "322 BC", "Philosopher", "Greek", AppUtils.getDrawableResourceId(R.drawable.aristotle), true),
    AUDREY_HEPBURN("Audrey Hepburn", "May 4, 1929", "January 20, 1993", "Actress", "Belgian", AppUtils.getDrawableResourceId(R.drawable.audrey_hepburn), true),
    ABRAHAM_LINCOLN("Abraham Lincoln", "February 12, 1809", "April 15, 1865", "President", "American", AppUtils.getDrawableResourceId(R.drawable.abraham_lincoln), true),
    ALDOUS_HUXLEY("Aldous Huxley", "July 26, 1894", "November 22, 1963", "Novelist", "English", AppUtils.getDrawableResourceId(R.drawable.aldous_huxley), true),
    ALEXANDER_HAMILTON("Alexander Hamilton", "January 11, 1755", "July 12, 1804", "Politician", "American", AppUtils.getDrawableResourceId(R.drawable.alexander_hamilton), true),
    ALEXANDER_POPE("Alexander Pope", "May 21, 1688", "May 30, 1744", "Poet", "English", AppUtils.getDrawableResourceId(R.drawable.alexander_pope), true),
    ARNOLD_SCHWARZENEGGER("Arnold Schwarzenegger", "July 30, 1947", "", "Actor", "Austrian", AppUtils.getDrawableResourceId(R.drawable.arnold_schwarzenegger), true);

    private LitePalAuthor litePalAuthor;

    EnumAuthor(String authorName, String birthDate, String deathDate, String occupation, String nationality, int profileImage, boolean isAuthor) {
        this.litePalAuthor = LitePalDataHandler.insetAuthor(new LitePalAuthor(authorName, birthDate, deathDate, occupation, nationality, profileImage, isAuthor));
    }

    public LitePalAuthor getLitePalAuthor() {
        return litePalAuthor;
    }
}
