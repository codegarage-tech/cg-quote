package com.reversecoder.quote.viewholder;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.reversecoder.library.bang.SmallBang;
import com.reversecoder.library.bang.SmallBangListener;
import com.reversecoder.quote.R;
import com.reversecoder.quote.activity.HomeActivity;
import com.reversecoder.quote.adapter.FavouriteQuoteAdapter;
import com.reversecoder.quote.fragment.FavouriteFragmentNew;
import com.reversecoder.quote.model.Quote;
import com.reversecoder.quote.util.DataHandler;
import com.reversecoder.quote.util.FragmentUtilsManager;
import com.reversecoder.quote.view.CanaroTextView;

import java.util.Random;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class FavouriteQuoteViewHolder extends BaseViewHolder<Quote> {

    View parentView;
    CanaroTextView txtPersonName;
    Button btnFavouriteQuoteLike, btnFavouriteQuoteCopyToClipboard, btnFavouriteQuoteShare;

    Quote mQuote;
    FavouriteQuoteAdapter mAdpater;
    int mPosition = -1;

    public FavouriteQuoteViewHolder(ViewGroup parent) {
        super(parent, R.layout.recyclerview_item_favourite_quote);

        parentView = $(R.id.rl_slider_card);
        txtPersonName = $(R.id.tv_quote_name);
        btnFavouriteQuoteLike = $(R.id.btn_favourite_quote_like);
        btnFavouriteQuoteCopyToClipboard = $(R.id.btn_favourite_quote_copy_to_clipboard);
        btnFavouriteQuoteShare = $(R.id.btn_favourite_quote_share);

        TypedArray images = getContext().getResources().obtainTypedArray(R.array.paintings_images);
        final int imageId = images.getResourceId(new Random().nextInt(images.length()), -1);
        parentView.setBackgroundResource(imageId);
        images.recycle();
    }

    @Override
    public void setData(final Quote data) {

        mQuote = data;
        mAdpater = (FavouriteQuoteAdapter) getOwnerAdapter();
        mPosition = mAdpater.getPosition(mQuote);

        txtPersonName.setText(data.getQuoteDescription());

        btnFavouriteQuoteLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
