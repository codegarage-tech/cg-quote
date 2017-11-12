package com.reversecoder.quote.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kannan.glazy.GlazyCard;
import com.kannan.glazy.utils.Utils;
import com.kannan.glazy.interfaces.FragmentItemClickListener;
import com.kannan.glazy.pager.GlazyFragmentPagerAdapter;
import com.kannan.glazy.pager.GlazyViewPager;
import com.kannan.glazy.transformers.GlazyPagerTransformer;
import com.reversecoder.quote.R;
import com.reversecoder.quote.model.Author;
import com.reversecoder.quote.model.FoldableQuote;
import com.reversecoder.quote.util.AllConstants;
import com.reversecoder.quote.util.DataHandler;

import java.util.ArrayList;

import io.armcha.ribble.presentation.widget.AnimatedImageView;
import io.armcha.ribble.presentation.widget.AnimatedTextView;
import io.armcha.ribble.presentation.widget.ArcView;

import static com.reversecoder.quote.util.AllConstants.INTENT_KEY_FAVOURITE_UPDATED_AUTHOR_BACK_PRESSED_AUTOMATICALLY;
import static com.reversecoder.quote.util.AllConstants.INTENT_KEY_FAVOURITE_UPDATED_QUOTE_BACK_PRESSED_AUTOMATICALLY;
import static com.reversecoder.quote.util.AllConstants.INTENT_KEY_FAVOURITE_UPDATED_QUOTE_DETAIL;
import static com.reversecoder.quote.util.AllConstants.REQUEST_CODE_FAVOURITE_AUTHOR_DETAIL;
import static com.reversecoder.quote.util.DataHandler.mAllMappedFavouriteQuotes;

public class FavouriteAuthorDetailActivity extends BaseActivity implements FragmentItemClickListener {

    Author mAuthor;

    private static String TAG = FavouriteAuthorDetailActivity.class.getSimpleName();
    int mSelectedPosition = -1;
    Intent intent;
    boolean isUpdated = false;
    boolean isBackPressedAutomatically = false;

    //Glazyviewpager
    private GlazyViewPager mPager;
    private GlazyFragmentPagerAdapter mPagerAdapter;
    TextView tvIndicatorNumber;
    int mSelectedPositionForQuoteDetail = -1;

    //toolbar
    ArcView arcMenuView;
    AnimatedImageView arcMenuImage;
    AnimatedTextView toolbarTitle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_author_detail);

        initViews();
        initActions();
    }

    private void initViews() {
        initToolBar();

        //glazyview pager
        tvIndicatorNumber = (TextView) findViewById(R.id.tv_indicator_number);
        mPager = (GlazyViewPager) findViewById(R.id.pager);
        mPagerAdapter = new GlazyFragmentPagerAdapter(getSupportFragmentManager(), FavouriteAuthorDetailActivity.this);

        //get quote list in background
        intent = getIntent();
        GetAuthorTask getAuthorTask = new GetAuthorTask(FavouriteAuthorDetailActivity.this, false);
        getAuthorTask.execute();
    }

    private void initToolBar() {
        toolbarTitle = (AnimatedTextView) findViewById(R.id.toolbarTitle);
        arcMenuImage = (AnimatedImageView) findViewById(R.id.arcImage);
        arcMenuView = (ArcView) findViewById(R.id.arcView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbarTitle.setAnimatedText(getString(R.string.title_activity_author_detail), 0L);

        arcMenuImage.setAnimatedImage(R.drawable.arrow_left, 0L);
        arcMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initActions() {

        //GlazyViewPager
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvIndicatorNumber.setText((position + 1) + "/" + mPagerAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class GetAuthorTask extends AsyncTask<String, String, ArrayList<GlazyCard>> {

        private Context mContext;
        private boolean mIsUpdated;

        public GetAuthorTask(Context context, boolean isUpdate) {
            mContext = context;
            mIsUpdated = isUpdate;
        }

        @Override
        protected void onPreExecute() {
            if (!mIsUpdated) {
                mSelectedPosition = intent.getIntExtra(AllConstants.INTENT_KEY_FAVOURITE_AUTHOR_POSITION, -1);
                mAuthor = intent.getParcelableExtra(AllConstants.INTENT_KEY_FAVOURITE_AUTHOR);
            }
        }

        @Override
        protected ArrayList<GlazyCard> doInBackground(String... params) {

            ArrayList<GlazyCard> mAllGlazyCards = new ArrayList<GlazyCard>();

            if (mIsUpdated) {
                Log.d(TAG, "Finding favourite data: " + mAllMappedFavouriteQuotes.size());
                mAllMappedFavouriteQuotes = DataHandler.getAllFavouriteData();
                Log.d(TAG, "Found favourite data: " + mAllMappedFavouriteQuotes.size());

            }

            if (mAllMappedFavouriteQuotes != null && mAuthor != null) {
                if (mAllMappedFavouriteQuotes.size() > 0) {
                    mAllGlazyCards = DataHandler.getAllGlazyCards(mAllMappedFavouriteQuotes);
                }
            }

            return mAllGlazyCards;
        }

        @Override
        protected void onPostExecute(ArrayList<GlazyCard> result) {

            if (mIsUpdated) {
                isUpdated = true;
            } else {
                isUpdated = false;
            }

            if (result != null && result.size() > 0) {
                isBackPressedAutomatically = false;
                Log.d(TAG, "Glazy data found");
                if (!mIsUpdated) {
                    mPager.setAdapter(mPagerAdapter);
                    mPager.setPageMargin(Utils.dpToPx(getApplicationContext(), 25));
                    mPager.setPageTransformer(false, new GlazyPagerTransformer());

                    mPagerAdapter.setData(result);
                    mPager.setCurrentItem(mSelectedPosition);

                } else {
                    /***************************************
                     * This is done for updating viewpager *
                     ***************************************/
                    mPager = (GlazyViewPager) findViewById(R.id.pager);
                    mPagerAdapter = new GlazyFragmentPagerAdapter(getSupportFragmentManager(), FavouriteAuthorDetailActivity.this);

                    mPager.setAdapter(mPagerAdapter);
                    mPager.setPageMargin(Utils.dpToPx(getApplicationContext(), 25));
                    mPager.setPageTransformer(false, new GlazyPagerTransformer());

                    mPagerAdapter.setData(result);
                    mPager.setCurrentItem(mSelectedPositionForQuoteDetail);

                    //GlazyViewPager
                    mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            tvIndicatorNumber.setText((position + 1) + "/" + mPagerAdapter.getCount());
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                }

                tvIndicatorNumber.setText((mPager.getCurrentItem() + 1) + "/" + mPagerAdapter.getCount());
            } else {
                isBackPressedAutomatically = true;
                Log.d(TAG, "Glazy data not found");
                mPagerAdapter.setData(result);
                Log.d(TAG, "Glazy adapter initialize");
                onBackPressed();
            }
        }
    }

    @Override
    public void onFragmentItemClick(View itemView) {
        mSelectedPositionForQuoteDetail = mPager.getCurrentItem();
        Intent intentQuoteDetail = new Intent(FavouriteAuthorDetailActivity.this, FavouriteQuoteDetailActivity.class);
        intentQuoteDetail.putExtra(AllConstants.INTENT_KEY_FAVOURITE_AUTHOR_POSITION, mSelectedPositionForQuoteDetail);
        intentQuoteDetail.putExtra(AllConstants.INTENT_KEY_FAVOURITE_AUTHOR, mAllMappedFavouriteQuotes.get(mPager.getCurrentItem()).getAuthor());
        startActivityForResult(intentQuoteDetail, REQUEST_CODE_FAVOURITE_AUTHOR_DETAIL);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onAutomaticBackPressed");

        if (isUpdated) {
            Log.d(TAG, "onAutomaticBackPressed update found");

            //prepare intent for sending to the author list
            Intent data = new Intent();
            data.putExtra(INTENT_KEY_FAVOURITE_UPDATED_AUTHOR_BACK_PRESSED_AUTOMATICALLY, isBackPressedAutomatically);
//            data.putParcelableArrayListExtra(INTENT_KEY_FAVOURITE_UPDATED_AUTHOR_DETAIL, mAllMappedFavouriteQuotes);
            setResult(RESULT_OK, data);
        }
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult");

        switch (requestCode) {
            case REQUEST_CODE_FAVOURITE_AUTHOR_DETAIL: {
                if (data != null && resultCode == RESULT_OK) {

                    Log.d(TAG, "RESULT_OK");
                    boolean isAutomaticBackPressedUpdate = data.getBooleanExtra(INTENT_KEY_FAVOURITE_UPDATED_QUOTE_BACK_PRESSED_AUTOMATICALLY, false);
                    Log.d(TAG, "isBackPressedAutomatically: " + isAutomaticBackPressedUpdate);

                    FoldableQuote lastUpdatedFoldableQuote = data.getParcelableExtra(INTENT_KEY_FAVOURITE_UPDATED_QUOTE_DETAIL);
                    Log.d(TAG, "isUpdatedResult: " + lastUpdatedFoldableQuote.toString());

                    if (isAutomaticBackPressedUpdate) {
                        GetAuthorTask getAuthorTask = new GetAuthorTask(FavouriteAuthorDetailActivity.this, true);
                        getAuthorTask.execute();
                    }
                }
                break;
            }
            default:
                break;
        }
    }
}
