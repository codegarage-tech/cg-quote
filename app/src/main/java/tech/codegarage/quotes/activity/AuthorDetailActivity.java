package tech.codegarage.quotes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;

import com.kannan.glazy.GlazyCard;
import com.kannan.glazy.interfaces.FragmentItemClickListener;
import com.kannan.glazy.pager.GlazyFragmentPagerAdapter;
import com.kannan.glazy.pager.GlazyViewPager;
import com.kannan.glazy.transformers.GlazyPagerTransformer;
import com.kannan.glazy.utils.Utils;

import java.util.ArrayList;

import io.armcha.ribble.presentation.widget.AnimatedImageView;
import io.armcha.ribble.presentation.widget.AnimatedTextView;
import io.armcha.ribble.presentation.widget.ArcView;
import spencerstudios.com.bungeelib.Bungee;
import tech.codegarage.quotes.R;
import tech.codegarage.quotes.factory.TextViewFactory;
import tech.codegarage.quotes.model.database.LitePalDataBuilder;
import tech.codegarage.quotes.model.database.LitePalDataHandler;
import tech.codegarage.quotes.util.AllConstants;

import static tech.codegarage.quotes.model.database.LitePalDataHandler.getAllQuotes;
import static tech.codegarage.quotes.model.database.LitePalDataHandler.getAuthorData;

//import static DataHandler.mAllMappedQuotes;

public class AuthorDetailActivity extends BaseActivity implements FragmentItemClickListener {

    LitePalDataBuilder mLitePalDataBuilder;
    GetAuthorTask getAuthorTask;
    private static String TAG = AuthorDetailActivity.class.getSimpleName();
    int mSelectedPosition = -1;
    private ArrayList<LitePalDataBuilder> litePalDataBuilders = new ArrayList<LitePalDataBuilder>();

    //Glazyviewpager
    private GlazyViewPager mPager;
    private GlazyFragmentPagerAdapter mPagerAdapter;
    private ArrayList<GlazyCard> mAllGlazyCards = new ArrayList<GlazyCard>();

    //toolbar
    ArcView arcMenuView;
    AnimatedImageView arcMenuImage;
    AnimatedTextView toolbarTitle;
    Toolbar toolbar;
    RelativeLayout rlCounter;
    TextSwitcher tsCounter;
    private int lastPagePosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_detail);

        initViews();
        initActions();
    }

    private void initViews() {
        initToolBar();

        //glazyview pager
        mPager = (GlazyViewPager) findViewById(R.id.pager);
        mPagerAdapter = new GlazyFragmentPagerAdapter(getSupportFragmentManager(), AuthorDetailActivity.this);

        //get quote list in background
        Intent intent = getIntent();
        getAuthorTask = new GetAuthorTask(AuthorDetailActivity.this, intent);
        getAuthorTask.execute();
    }

    private void initToolBar() {
        toolbarTitle = (AnimatedTextView) findViewById(R.id.toolbarTitle);
        arcMenuImage = (AnimatedImageView) findViewById(R.id.arcImage);
        arcMenuView = (ArcView) findViewById(R.id.arcView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle.setAnimatedText(getString(R.string.title_activity_author_detail), 0L);
        rlCounter = (RelativeLayout) findViewById(R.id.rl_counter);
        rlCounter.setVisibility(View.VISIBLE);
        tsCounter = (TextSwitcher) findViewById(R.id.ts_counter);
        tsCounter.setFactory(new TextViewFactory(AuthorDetailActivity.this, R.style.CounterTextView, true));

        arcMenuImage.setAnimatedImage(R.drawable.arrow_left, 0L);
        arcMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void switchCounter(int currentPosition, int totalCount) {

        int animH[] = new int[]{R.anim.slide_in_right, R.anim.slide_out_left};
        int animV[] = new int[]{R.anim.slide_in_top, R.anim.slide_out_bottom};

        final boolean left2right = currentPosition < lastPagePosition;

        if (left2right) {
            animH[0] = R.anim.slide_in_left;
            animH[1] = R.anim.slide_out_right;

            animV[0] = R.anim.slide_in_bottom;
            animV[1] = R.anim.slide_out_top;
        }

        tsCounter.setInAnimation(AuthorDetailActivity.this, animH[0]);
        tsCounter.setOutAnimation(AuthorDetailActivity.this, animH[1]);
        tsCounter.setText((currentPosition + 1) + "/" + totalCount);

        if (litePalDataBuilders.size() >= currentPosition) {
            toolbarTitle.setAnimatedText(litePalDataBuilders.get(currentPosition).getLitePalAuthor().getAuthorName(), 0L);
        }

        lastPagePosition = currentPosition;
    }

    private void initActions() {

        //GlazyViewPager
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (lastPagePosition == mPager.getCurrentItem()) {
                        return;
                    }
                    switchCounter(mPager.getCurrentItem(), mPagerAdapter.getCount());
                }
            }
        });
    }

    public class GetAuthorTask extends AsyncTask<String, String, ArrayList<GlazyCard>> {

        private Context mContext;
        private Intent mIntent;

        public GetAuthorTask(Context context, Intent intent) {
            mContext = context;
            mIntent = intent;
        }

        @Override
        protected void onPreExecute() {
            mSelectedPosition = mIntent.getIntExtra(AllConstants.INTENT_KEY_AUTHOR_POSITION, -1);
            mLitePalDataBuilder = mIntent.getParcelableExtra(AllConstants.INTENT_KEY_AUTHOR);
        }

        @Override
        protected ArrayList<GlazyCard> doInBackground(String... params) {
            litePalDataBuilders = getAllQuotes();

            if (litePalDataBuilders.size() > 0) {
                mAllGlazyCards = LitePalDataHandler.getAllGlazyCards(litePalDataBuilders);
            }

            return mAllGlazyCards;
        }

        @Override
        protected void onPostExecute(ArrayList<GlazyCard> result) {
            if (result != null && result.size() > 0) {
                mPager.setAdapter(mPagerAdapter);
                mPager.setPageMargin(Utils.dpToPx(getApplicationContext(), 25));
                mPager.setPageTransformer(false, new GlazyPagerTransformer());

                mPagerAdapter.setData(result);
                mPager.setCurrentItem(mSelectedPosition);

                switchCounter(mPager.getCurrentItem(), mPagerAdapter.getCount());
            }
        }
    }

    @Override
    public void onFragmentItemClick(View itemView) {
        Intent intentQuoteDetail = new Intent(AuthorDetailActivity.this, QuoteDetailActivity.class);
        intentQuoteDetail.putExtra(AllConstants.INTENT_KEY_AUTHOR_POSITION, mPager.getCurrentItem());
//        intentQuoteDetail.putExtra(AllConstants.INTENT_KEY_AUTHOR, litePalDataBuilders.get(mPager.getCurrentItem()));
        intentQuoteDetail.putExtra(AllConstants.INTENT_KEY_AUTHOR, getAuthorData(mPager.getCurrentItem()));
        startActivity(intentQuoteDetail);
        Bungee.slideUp(AuthorDetailActivity.this);
    }

    @Override
    public void onBackPressed() {
        Bungee.slideDown(AuthorDetailActivity.this);
        finish();
    }
}
