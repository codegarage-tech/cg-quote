package com.reversecoder.quote.model.database;

import android.util.Log;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import static com.reversecoder.quote.util.AppUtils.getMD5String;

public class LitePalQuoteLanguageAuthorTag extends DataSupport {

    private long id;
    private long quoteId;
    private long languageId;
    private long authorId;
    private long tagId;
    @Column(unique = true)
    private String md5 = "";

    public LitePalQuoteLanguageAuthorTag(long quoteId, long languageId, long authorId, long tagId) {
        this.quoteId = quoteId;
        this.languageId = languageId;
        this.authorId = authorId;
        this.tagId = tagId;
        this.md5 = getMD5String(toString());
        Log.d("MD5 for: ", toString());
        Log.d("MD5: ", md5);
    }

    public long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(long quoteId) {
        this.quoteId = quoteId;
    }

    public long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(long languageId) {
        this.languageId = languageId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", quoteId=" + quoteId +
                ", languageId=" + languageId +
                ", authorId=" + authorId +
                ", tagId=" + tagId +
                ", md5='" + md5 + '\'' +
                '}';
    }
}