package tech.codegarage.quotes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextSwitcher;

import com.reversecoder.library.event.OnSingleClickListener;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

import io.armcha.ribble.presentation.widget.AnimatedImageView;
import io.armcha.ribble.presentation.widget.AnimatedTextView;
import io.armcha.ribble.presentation.widget.ArcView;
import se.emilsjolander.flipview.FlipView;
import se.emilsjolander.flipview.OverFlipMode;
import spencerstudios.com.bungeelib.Bungee;
import tech.codegarage.quotes.R;
import tech.codegarage.quotes.adapter.QuoteFlipViewAdapter;
import tech.codegarage.quotes.factory.TextViewFactory;
import tech.codegarage.quotes.model.database.LitePalDataBuilder;
import tech.codegarage.quotes.model.database.LitePalDataHandler;
import tech.codegarage.quotes.model.database.LitePalQuote;
import tech.codegarage.quotes.model.database.LitePalTag;
import tech.codegarage.quotes.util.AllConstants;
import tech.codegarage.quotes.util.AppUtils;
import tech.codegarage.quotes.util.ClipboardHandler;
import tech.codegarage.quotes.util.IntentManager;

import static tech.codegarage.quotes.util.AllConstants.FLASHING_DEFAULT_DELAY;

//import static DataHandler.mAllMappedQuotes;

public class QuoteDetailActivity extends BaseActivity {

    LitePalDataBuilder mLitePalDataBuilder;
    GetQuoteTask getQuoteTask;
    int mSelectedPosition = -1;
    private ArrayList<LitePalDataBuilder.LitePalQuoteBuilder> mAllQuotes = new ArrayList<LitePalDataBuilder.LitePalQuoteBuilder>();
    private String TAG = QuoteDetailActivity.class.getSimpleName();
    TextSwitcher tsQuoteCounter;

    //Flipview
    FlipView mQuoteFlipView;
    QuoteFlipViewAdapter mQuoteAdapter;
    private int lastPagePosition = 0;

    //toolbar
    ArcView arcMenuView;
    AnimatedImageView arcMenuImage;
    AnimatedTextView toolbarTitle;
    Toolbar toolbar;
    ImageView imageViewContextMenu;

    //Contextual Menu
    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_detail);

        initView();
        initActions();
    }

    public void initView() {
        initToolBar();

        //counter text switcher
        tsQuoteCounter = (TextSwitcher) findViewById(R.id.ts_quote_counter);
        tsQuoteCounter.setFactory(new TextViewFactory(QuoteDetailActivity.this, R.style.CounterTextView, true));

        //get quote list in background
        Intent intent = getIntent();
        getQuoteTask = new GetQuoteTask(QuoteDetailActivity.this, intent);
        getQuoteTask.execute();
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

        tsQuoteCounter.setInAnimation(QuoteDetailActivity.this, animH[0]);
        tsQuoteCounter.setOutAnimation(QuoteDetailActivity.this, animH[1]);
        tsQuoteCounter.setText((currentPosition + 1) + "/" + totalCount);

        lastPagePosition = currentPosition;
    }

    private void initActions() {
        imageViewContextMenu.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                LitePalDataBuilder.LitePalQuoteBuilder quote = mQuoteAdapter.getItem(mQuoteFlipView.getCurrentPage());
                if (quote.getLitePalQuote().isQuote()) {
                    initMenuFragment(quote.getLitePalQuote().isFavourite());
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
            }
        });
    }

    private void initToolBar() {
        toolbarTitle = (AnimatedTextView) findViewById(R.id.toolbarTitle);
        arcMenuImage = (AnimatedImageView) findViewById(R.id.arcImage);
        arcMenuView = (ArcView) findViewById(R.id.arcView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbarTitle.setAnimatedText(getString(R.string.title_activity_quote_detail), 0L);

        arcMenuImage.setAnimatedImage(R.drawable.arrow_left, 0L);
        arcMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //context menu
        imageViewContextMenu = (ImageView) findViewById(R.id.iv_context_menu);
        imageViewContextMenu.setVisibility(View.VISIBLE);
        //Animate context menu
        AppUtils.flashView(imageViewContextMenu, FLASHING_DEFAULT_DELAY);
        fragmentManager = getSupportFragmentManager();
    }

    public class GetQuoteTask extends AsyncTask<String, String, ArrayList<LitePalDataBuilder.LitePalQuoteBuilder>> {

        private Context mContext;
        private Intent mIntent;

        public GetQuoteTask(Context context, Intent intent) {
            mContext = context;
            mIntent = intent;
        }

        @Override
        protected void onPreExecute() {
            mSelectedPosition = mIntent.getIntExtra(AllConstants.INTENT_KEY_AUTHOR_POSITION, -1);
            mLitePalDataBuilder = mIntent.getParcelableExtra(AllConstants.INTENT_KEY_AUTHOR);

            if (mLitePalDataBuilder != null) {
                toolbarTitle.setAnimatedText(mLitePalDataBuilder.getLitePalAuthor().getAuthorName(), 0L);
            }
        }

        @Override
        protected ArrayList<LitePalDataBuilder.LitePalQuoteBuilder> doInBackground(String... params) {
            if (mLitePalDataBuilder != null) {
                if (mLitePalDataBuilder.getLitePalQuoteBuilders().size() > 0) {
                    //Need to read from session due to getting previous update
                    mAllQuotes = mLitePalDataBuilder.getLitePalQuoteBuilders();
                }
            }

            return mAllQuotes;
        }

        @Override
        protected void onPostExecute(ArrayList<LitePalDataBuilder.LitePalQuoteBuilder> result) {
            if (result != null && result.size() > 0) {
                initQuoteDetailFlipView(result);
            }
        }
    }

    /*******************
     * Contextual Menu *
     *******************/
    private void initMenuFragment(boolean isFavourite) {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects(isFavourite));
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View clickedView, int position) {

                LitePalDataBuilder.LitePalQuoteBuilder quote = mQuoteAdapter.getItem(mQuoteFlipView.getCurrentPage());
                Log.d(TAG, "FoldableItemPosition: " + mQuoteFlipView.getCurrentPage() + "");
                Log.d(TAG, "FoldableItem: " + quote.toString());

                switch (position) {

                    case 0:
                        break;

                    case 1: {
                        if (quote.getLitePalQuote().isFavourite()) {
                            quote.getLitePalQuote().setFavourite(false);
                        } else {
                            quote.getLitePalQuote().setFavourite(true);
                        }
                        new UpdateQuoteIntoDatabase(QuoteDetailActivity.this, quote).execute();
                        break;
                    }

                    case 2:
                        ClipboardHandler.copyToClipboard(QuoteDetailActivity.this, quote.getLitePalQuote().getQuoteDescription());
                        break;

                    case 3:
                        IntentManager.shareToAllAvailableApps(QuoteDetailActivity.this, "", AppUtils.getSharedQuote(QuoteDetailActivity.this, mLitePalDataBuilder, quote));
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private List<MenuObject> getMenuObjects(boolean isFavourite) {
        // You can use any [resource, bitmap, drawable, color] as image:
        // item.setResource(...)
        // item.setBitmap(...)
        // item.setDrawable(...)
        // item.setColor(...)
        // You can set image ScaleType:
        // item.setScaleType(ScaleType.FIT_XY)
        // You can use any [resource, drawable, color] as background:
        // item.setBgResource(...)
        // item.setBgDrawable(...)
        // item.setBgColor(...)
        // You can use any [color] as text color:
        // item.setTextColor(...)
        // You can set any [color] as divider color:
        // item.setDividerColor(...)

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_vector_cancel_empty_rose, getTheme()));
        close.setDividerColor(R.color.colorPrimary);

        MenuObject addToFavourite = new MenuObject(getString(R.string.context_menu_add_to_favourite));
        addToFavourite.setDrawable(VectorDrawableCompat.create(getResources(), (isFavourite ? R.drawable.ic_vector_favourite_fill_rose : R.drawable.ic_vector_favourite_empty_rose), getTheme()));
        addToFavourite.setDividerColor(R.color.colorPrimary);

        MenuObject copyToClipboard = new MenuObject(getString(R.string.context_menu_copy_to_clipboard));
        copyToClipboard.setDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_vector_copy_empty_rose, getTheme()));
        copyToClipboard.setDividerColor(R.color.colorPrimary);

        MenuObject shareToFriend = new MenuObject(getString(R.string.share_to_friends));
        shareToFriend.setDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_vector_share_empty_rose, getTheme()));
        shareToFriend.setDividerColor(R.color.colorPrimary);

        menuObjects.add(close);
        menuObjects.add(addToFavourite);
        menuObjects.add(copyToClipboard);
        menuObjects.add(shareToFriend);

        return menuObjects;
    }

    @Override
    public void onBackPressed() {
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            mMenuDialogFragment.dismiss();
        } else {
            Bungee.slideDown(QuoteDetailActivity.this);
            finish();
        }
    }

    /****************************
     * FlipView methods *
     ****************************/
    private void initQuoteDetailFlipView(ArrayList<LitePalDataBuilder.LitePalQuoteBuilder> data) {

        mQuoteFlipView = (FlipView) findViewById(R.id.flipview_quote_detail);
        mQuoteAdapter = new QuoteFlipViewAdapter(QuoteDetailActivity.this);
        mQuoteAdapter.setData(getModifiedQuotes(data));
        mQuoteAdapter.setCallback(new QuoteFlipViewAdapter.Callback() {
            @Override
            public void onPageRequested(int page) {
                mQuoteFlipView.smoothFlipTo(page);
            }
        });
        mQuoteFlipView.setAdapter(mQuoteAdapter);
        mQuoteFlipView.setOnFlipListener(new FlipView.OnFlipListener() {
            @Override
            public void onFlippedToPage(FlipView v, int position, long id) {
                //invisible context menu for last item, as it is the end.
                if (position == mQuoteAdapter.getCount() - 1) {
                    imageViewContextMenu.setVisibility(View.GONE);
                    tsQuoteCounter.setVisibility(View.GONE);
                } else {
                    imageViewContextMenu.setVisibility(View.VISIBLE);
                    tsQuoteCounter.setVisibility(View.VISIBLE);

                    //switch counter of the flipview
                    switchCounter(position, (mQuoteAdapter.getCount() - 1));
                }
            }
        });
        mQuoteFlipView.peakNext(false);
        mQuoteFlipView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mQuoteFlipView.setOverFlipMode(OverFlipMode.RUBBER_BAND);
        mQuoteFlipView.setEmptyView(findViewById(R.id.empty_view));
        mQuoteFlipView.setOnOverFlipListener(new FlipView.OnOverFlipListener() {
            @Override
            public void onOverFlip(FlipView v, OverFlipMode mode, boolean overFlippingPrevious, float overFlipDistance, float flipDistancePerPage) {
                Log.d(TAG, "overflip overFlipDistance = " + overFlipDistance);
            }
        });

        switchCounter(mQuoteFlipView.getCurrentPage(), (mQuoteAdapter.getCount() - 1));
    }

    private ArrayList<LitePalDataBuilder.LitePalQuoteBuilder> getModifiedQuotes(ArrayList<LitePalDataBuilder.LitePalQuoteBuilder> quotes) {
        boolean isDummyDataFound = false;
        for (LitePalDataBuilder.LitePalQuoteBuilder quote : quotes) {
            if (quote.getLitePalQuote().getQuoteDescription().equalsIgnoreCase(getString(R.string.txt_dummy_quote))) {
                isDummyDataFound = true;
                break;
            }
        }

        if (!isDummyDataFound) {
            LitePalQuote litePalQuote = new LitePalQuote(getString(R.string.txt_dummy_quote), false, false);
            litePalQuote.setId(4200000L);
            LitePalDataBuilder.LitePalQuoteBuilder dummyQuote = new LitePalDataBuilder.LitePalQuoteBuilder(litePalQuote, new ArrayList<LitePalTag>());
            quotes.add(dummyQuote);
        }

        return quotes;
    }

    class UpdateQuoteIntoDatabase extends AsyncTask<String, String, LitePalDataBuilder.LitePalQuoteBuilder> {

        private Context mContext;
        private LitePalDataBuilder.LitePalQuoteBuilder mQuote;

        UpdateQuoteIntoDatabase(Context context, LitePalDataBuilder.LitePalQuoteBuilder updatedQuote) {
            mContext = context;
            mQuote = updatedQuote;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected LitePalDataBuilder.LitePalQuoteBuilder doInBackground(String... params) {
            //Update quote into database and session
            LitePalDataBuilder.LitePalQuoteBuilder updatedQuote = LitePalDataHandler.updateQuote(mLitePalDataBuilder, mQuote);
            if (updatedQuote != null) {
                return updatedQuote;
            }

            return null;
        }

        @Override
        protected void onPostExecute(LitePalDataBuilder.LitePalQuoteBuilder result) {
            if (result != null) {
                mQuoteAdapter.updateItem(mQuoteFlipView.getCurrentPage(), result);
            }
        }
    }
}