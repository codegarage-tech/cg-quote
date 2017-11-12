package com.reversecoder.quote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.reversecoder.quote.R;
import com.reversecoder.quote.model.FoldableQuote;
import com.reversecoder.quote.view.CanaroTextView;

import java.util.Arrays;

public class QuoteDetailAdapter extends ItemsAdapter<FoldableQuote, QuoteDetailAdapter.ViewHolder> implements View.OnClickListener {

    Context mContext;
    FoldableQuote[] mData;

    public QuoteDetailAdapter(Context context, FoldableQuote[] data) {
        this.mContext = context;
        this.mData = data;
        setItemsList(Arrays.asList(data));
    }

    @Override
    protected ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        final ViewHolder holder = new ViewHolder(parent);
        holder.image.setOnClickListener(this);
        return holder;
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        final FoldableQuote item = getItem(position);

        holder.image.setTag(R.id.list_item_image, item);
        holder.image.setBackgroundResource(item.getImageId());
//        Glide
//                .with(mContext)
//                .load(item.getImageId())
//                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
//                .into(holder.image);
        holder.title.setText(item.getQuoteDescription());
    }

    @Override
    public void onClick(View view) {
        final FoldableQuote item = (FoldableQuote) view.getTag(R.id.list_item_image);

//        Toast.makeText(mContext, item.getQuoteDescription(), Toast.LENGTH_SHORT).show();
    }

    static class ViewHolder extends ItemsAdapter.ViewHolder {
        final ImageView image;
        final CanaroTextView title;

        ViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_quote_detail, parent, false));
            image = (ImageView) itemView.findViewById(R.id.list_item_image);
            title = (CanaroTextView) itemView.findViewById(R.id.tv_quote);
        }
    }

    public boolean updateItem(int position, FoldableQuote foldableQuote) {
        if (mData == null || position < 0 || position >= mData.length) {
            return false;
        }
        mData[position] = foldableQuote;
        notifyDataSetChanged();
        return true;
    }
}
