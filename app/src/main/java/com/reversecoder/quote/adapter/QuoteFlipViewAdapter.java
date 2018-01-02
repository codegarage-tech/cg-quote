package com.reversecoder.quote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.reversecoder.quote.R;
import com.reversecoder.quote.activity.HomeActivity;
import com.reversecoder.quote.model.Quote;
import com.reversecoder.quote.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.flipview.FlipView;

public class QuoteFlipViewAdapter extends BaseAdapter {

    public interface Callback {
        public void onPageRequested(int page);
    }

    private LayoutInflater inflater;
    private Callback callback;
    private List<Quote> mData;
    private Context mContext;
    private FlipView mFlipView;

    public QuoteFlipViewAdapter(Context context) {
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = new ArrayList<Quote>();
    }

    public void setData(List<Quote> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public List<Quote> getData() {
        return mData;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Quote getItem(int position) {
        return mData.get(position);
    }

    public int getItemPosition(Quote item) {
        for (int i = 0; i < mData.size(); i++) {
            if ((mData.get(i)).getId() == item.getId()) {
                return i;
            }
        }
        return -1;
    }

    public void updateItem(Quote item) {
        int position = getItemPosition(item);
        mData.remove(position);
        mData.add(position, item);
        notifyDataSetChanged();
    }

    public void updateItem(int position, Quote item) {
        mData.remove(position);
        mData.add(position, item);
        notifyDataSetChanged();
    }

    public void removeItem(Quote item) {
        int mPosition = getItemPosition(item);

        if (mPosition < getCount() - 1) {
            mData.remove(mPosition);
            notifyDataSetChanged();

            if ((mFlipView.getCurrentPage() == (getCount() - 1)) && (getCount() > 1)) {
                mFlipView.smoothFlipTo(0);
            } else if (getCount() == 1) {
                if (mData.get(0).getQuoteDescription().equalsIgnoreCase(mContext.getString(R.string.txt_dummy_quote))) {
                    mFlipView.setAdapter(null);
                    mData.clear();
                    //invisible context menu for no item, as it is empty.
                    ((HomeActivity) mContext).btnContextMenu.setVisibility(View.GONE);
                    notifyDataSetChanged();
                }
            }
        }
    }

    public void removeItem(int position, Quote item) {
        int mPosition = position;

        if (mPosition < getCount() - 1) {
            mData.remove(mPosition);
            notifyDataSetChanged();

            if ((mFlipView.getCurrentPage() == (getCount() - 1)) && (getCount() > 1)) {
                mFlipView.smoothFlipTo(0);
            } else if (getCount() == 1) {
                if (mData.get(0).getQuoteDescription().equalsIgnoreCase(mContext.getString(R.string.txt_dummy_quote))) {
                    mFlipView.setAdapter(null);
                    mData.clear();
                    //invisible context menu for no item, as it is empty.
                    ((HomeActivity) mContext).btnContextMenu.setVisibility(View.GONE);
                    notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.flipview_item_quote, null);
            mFlipView = (FlipView) parent.findViewById(R.id.flipview_quote_detail);
        }

        final Quote mItem = getItem(position);

        TextView text = (TextView) vi.findViewById(R.id.tv_quote);
        if (mItem.getQuoteDescription().equalsIgnoreCase("420")) {
            text.setText("END");
        } else {
            text.setText(mItem.getQuoteDescription());
        }

        vi.setBackgroundColor(AppUtils.getRandomPastelColor());

        return vi;
    }
}