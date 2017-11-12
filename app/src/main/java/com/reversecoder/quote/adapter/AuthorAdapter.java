package com.reversecoder.quote.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.reversecoder.quote.model.Author;
import com.reversecoder.quote.model.MappedQuote;
import com.reversecoder.quote.viewholder.AdvertiseAuthorViewHolder;
import com.reversecoder.quote.viewholder.AuthorViewHolder;

import java.security.InvalidParameterException;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class AuthorAdapter extends RecyclerArrayAdapter<MappedQuote> {

    public static final int TYPE_INVALID = 0;
    public static final int TYPE_ADMOB = 1;
    public static final int TYPE_AUTHOR = 2;

    public AuthorAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewType(int position) {
        MappedQuote mappedQuote = getItem(position);
        if (!mappedQuote.getAuthor().isAuthor()) {
            return TYPE_ADMOB;
        } else if (mappedQuote.getAuthor().isAuthor()) {
            return TYPE_AUTHOR;
        }
        return TYPE_INVALID;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_AUTHOR:
                return new AuthorViewHolder(parent);
            case TYPE_ADMOB:
                return new AdvertiseAuthorViewHolder(parent);
            default:
                throw new InvalidParameterException();
        }
    }
}