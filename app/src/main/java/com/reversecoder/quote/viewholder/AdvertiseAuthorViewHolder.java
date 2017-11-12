package com.reversecoder.quote.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.reversecoder.quote.R;
import com.reversecoder.quote.model.MappedQuote;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class AdvertiseAuthorViewHolder extends BaseViewHolder<MappedQuote> {

    public TextView txtAdvertiseName;

    public AdvertiseAuthorViewHolder(ViewGroup parent) {
        super(parent, R.layout.recyclerview_item_advertise_author);

        txtAdvertiseName = $(R.id.tv_author_name);
    }

    @Override
    public void setData(final MappedQuote data) {
//        txtAdvertiseName.setText(data.getAuthorName());
    }
}
