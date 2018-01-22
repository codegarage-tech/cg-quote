package tech.codegarage.quotes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.flipview.FlipView;
import tech.codegarage.quotes.R;
import tech.codegarage.quotes.activity.FavouriteQuoteDetailActivity;
import tech.codegarage.quotes.activity.HomeActivity;
import tech.codegarage.quotes.model.database.LitePalDataBuilder;
import tech.codegarage.quotes.util.AppUtils;

public class FavouriteQuoteFlipViewAdapter extends BaseAdapter {

    public interface Callback {
        public void onPageRequested(int page);
    }

    private LayoutInflater inflater;
    private Callback callback;
    private List<LitePalDataBuilder.LitePalQuoteBuilder> mData;
    private Context mContext;
    private FlipView mFlipView;

    public FavouriteQuoteFlipViewAdapter(Context context) {
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = new ArrayList<LitePalDataBuilder.LitePalQuoteBuilder>();
    }

    public void setData(List<LitePalDataBuilder.LitePalQuoteBuilder> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public List<LitePalDataBuilder.LitePalQuoteBuilder> getData() {
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
    public LitePalDataBuilder.LitePalQuoteBuilder getItem(int position) {
        return mData.get(position);
    }

    public int getItemPosition(LitePalDataBuilder.LitePalQuoteBuilder item) {
        for (int i = 0; i < mData.size(); i++) {
            if ((mData.get(i)).getLitePalQuote().getId() == item.getLitePalQuote().getId()) {
                return i;
            }
        }
        return -1;
    }

    public void updateItem(LitePalDataBuilder.LitePalQuoteBuilder item) {
        int position = getItemPosition(item);
        mData.remove(position);
        mData.add(position, item);
        notifyDataSetChanged();
    }

    public void updateItem(int position, LitePalDataBuilder.LitePalQuoteBuilder item) {
        mData.remove(position);
        mData.add(position, item);
        notifyDataSetChanged();
    }

    public void removeItem(LitePalDataBuilder.LitePalQuoteBuilder item) {
        int mPosition = getItemPosition(item);

        if (mPosition < getCount() - 1) {
            mData.remove(mPosition);
            notifyDataSetChanged();

            if ((mFlipView.getCurrentPage() == (getCount() - 1)) && (getCount() > 1)) {
                mFlipView.smoothFlipTo(0);
            } else if (getCount() == 1) {
                if (mData.get(0).getLitePalQuote().getQuoteDescription().equalsIgnoreCase(mContext.getString(R.string.txt_dummy_quote))) {
                    mFlipView.setAdapter(null);
                    mData.clear();
                    //invisible context menu for no item, as it is empty.
                    ((HomeActivity) mContext).imagViewContextMenu.setVisibility(View.GONE);
                    notifyDataSetChanged();
                }
            }
        }
    }

    public void removeItem(int position, LitePalDataBuilder.LitePalQuoteBuilder item) {
        int mPosition = position;

        if (mPosition < getCount() - 1) {
            mData.remove(mPosition);
            notifyDataSetChanged();

            if ((mFlipView.getCurrentPage() == (getCount() - 1)) && (getCount() > 1)) {
                mFlipView.smoothFlipTo(0);
            } else if (getCount() == 1) {
                if (mData.get(0).getLitePalQuote().getQuoteDescription().equalsIgnoreCase(mContext.getString(R.string.txt_dummy_quote))) {
                    mFlipView.setAdapter(null);
                    mData.clear();
                    //invisible context menu for no item, as it is empty.
                    ((HomeActivity) mContext).imagViewContextMenu.setVisibility(View.GONE);
                    notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getLitePalQuote().getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.flipview_item_favourite_quote, null);
            mFlipView = (FlipView) parent.findViewById(R.id.flipview_favourite_quote_detail);
        }

        final LitePalDataBuilder.LitePalQuoteBuilder mItem = getItem(position);

        TextView text = (TextView) vi.findViewById(R.id.tv_quote);
        if (mItem.getLitePalQuote().getQuoteDescription().equalsIgnoreCase("420")) {
            text.setText("END");
        } else {
            text.setText("\"" + mItem.getLitePalQuote().getQuoteDescription() + "\"");
        }

        vi.setBackgroundColor(AppUtils.getRandomPastelColor());

        return vi;
    }
}
