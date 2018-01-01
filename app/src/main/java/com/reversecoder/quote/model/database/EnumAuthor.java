package com.reversecoder.quote.model.database;

public enum EnumAuthor {

    APJ_ABDUL_KALAM("A. P. J. Abdul Kalam"),
    ALBERT_CAMUS("Albert Camus"),
    ARISTOTLE("Aristotle");

    private String author;

    EnumAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }
}
