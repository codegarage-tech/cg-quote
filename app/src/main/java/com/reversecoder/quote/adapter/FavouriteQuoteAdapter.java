package com.reversecoder.quote.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.reversecoder.quote.model.Quote;
import com.reversecoder.quote.viewholder.AdvertiseFavouriteQuoteViewHolder;
import com.reversecoder.quote.viewholder.FavouriteQuoteViewHolder;

import java.security.InvalidParameterException;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class FavouriteQuoteAdapter extends RecyclerArrayAdapter<Quote> {

    private static final int TYPE_INVALID = 0;
    private static final int TYPE_ADMOB = 1;
    private static final int TYPE_QUOTE = 2;

    public FavouriteQuoteAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewType(int position) {
        Quote quote = getItem(position);
        if (!quote.isQuote()) {
            return TYPE_ADMOB;
        } else if (quote.isQuote()) {
            return TYPE_QUOTE;
        }
        return TYPE_INVALID;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_QUOTE:
                return new FavouriteQuoteViewHolder(parent);
            case TYPE_ADMOB:
                return new AdvertiseFavouriteQuoteViewHolder(parent);
            default:
                throw new InvalidParameterException();
        }
    }

//    public void updateSelection(Quote quote, boolean isHover) {
//        for (int i = 0; i < getAllData().size(); i++) {
//            Quote quoteData = (Quote) getAllData().get(i);
//            if (quoteData.getQuoteDescription().equalsIgnoreCase(quote.getQuoteDescription())) {
//                quoteData.setHover(isHover);
//                Log.d("updateSelection: ", quoteData.toString());
//            } else {
//                quoteData.setHover(false);
//            }
//        }
//        notifyDataSetChanged();
//    }
}