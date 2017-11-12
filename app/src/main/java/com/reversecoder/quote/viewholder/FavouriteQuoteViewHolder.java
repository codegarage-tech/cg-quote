package com.reversecoder.quote.viewholder;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.reversecoder.library.bang.SmallBang;
import com.reversecoder.library.bang.SmallBangListener;
import com.reversecoder.library.event.OnSingleClickListener;
import com.reversecoder.quote.R;
import com.reversecoder.quote.activity.HomeActivity;
import com.reversecoder.quote.adapter.FavouriteAuthorDetailAdapter;
import com.reversecoder.quote.fragment.FavouriteFragmentNew;
import com.reversecoder.quote.model.Quote;
import com.reversecoder.quote.util.DataHandler;
import com.reversecoder.quote.util.FragmentUtilsManager;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class FavouriteQuoteViewHolder extends BaseViewHolder<Quote> {

    TextView txtPersonName;
    Button btnFavourite;

    public FavouriteQuoteViewHolder(ViewGroup parent) {
        super(parent, R.layout.recyclerview_item_favourite_quote);

        txtPersonName = $(R.id.tv_quote_name);
        btnFavourite = $(R.id.btn_favourite);
    }

    @Override
    public void setData(final Quote data) {

        final FavouriteAuthorDetailAdapter mAdpater = (FavouriteAuthorDetailAdapter) getOwnerAdapter();
        final int mPosition = mAdpater.getPosition(data);

        txtPersonName.setText(data.getQuoteDescription());
        if (data.isFavourite()) {
            btnFavourite.setBackgroundResource(R.drawable.icn_6);
        } else {
            btnFavourite.setBackgroundResource(R.drawable.icn_4);
        }

        btnFavourite.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {

                SmallBang smallBang = SmallBang.attach2Window(((HomeActivity) getContext()));
                smallBang.bang(view, new SmallBangListener() {
                    @Override
                    public void onAnimationStart() {
                        data.setFavourite(false);
                        //update data into database
                        new UpdateQuoteIntoDatabase(getContext().getApplicationContext(), data).execute();
                    }

                    @Override
                    public void onAnimationEnd() {
                        //Update removed data into adapter
                        Quote updatedData = mAdpater.update(data, mPosition);
                        Log.d("UpdatedQuote:", updatedData.toString());
                        mAdpater.remove(mPosition);
                        mAdpater.notifyDataSetChanged();
                        if (mAdpater.getCount() == 0) {
                            ((FavouriteFragmentNew) FragmentUtilsManager.getVisibleSupportFragment(((HomeActivity) getContext()), getContext().getString(R.string.ribble_menu_item_favourite))).onFragmentBackPressed();
                        }
                    }
                });
            }
        });
    }

    class UpdateQuoteIntoDatabase extends AsyncTask<String, String, Quote> {

        private Context mContext;
        private Quote mQuote;

        UpdateQuoteIntoDatabase(Context context, Quote quote) {
            mContext = context;
            mQuote = quote;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Quote doInBackground(String... params) {
            Quote updatedDataIntoDatabase = DataHandler.setFavouriteForFavouriteFragment(mQuote, false);
            Log.d("updatedDataIntoDatabase", updatedDataIntoDatabase.toString());
            return updatedDataIntoDatabase;
        }

        @Override
        protected void onPostExecute(Quote result) {
        }
    }
}
