package com.reversecoder.quote.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.reversecoder.quote.R;
import com.reversecoder.quote.model.Quote;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class AdvertiseFavouriteQuoteViewHolder extends BaseViewHolder<Quote> {

    public TextView txtAdvertiseName;

    public AdvertiseFavouriteQuoteViewHolder(ViewGroup parent) {
        super(parent, R.layout.recyclerview_item_advertise_favourite_quote);

        txtAdvertiseName = $(R.id.txt_advertise_name);
    }

    @Override
    public void setData(final Quote data) {
        txtAdvertiseName.setText(data.getQuoteDescription());
    }
}
