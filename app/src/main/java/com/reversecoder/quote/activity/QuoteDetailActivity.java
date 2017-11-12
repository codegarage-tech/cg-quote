package com.reversecoder.quote.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextSwitcher;

import com.alexvasilkov.foldablelayout.FoldableListLayout;
import com.reversecoder.library.event.OnSingleClickListener;
import com.reversecoder.quote.R;
import com.reversecoder.quote.adapter.QuoteDetailAdapter;
import com.reversecoder.quote.factory.TextViewFactory;
import com.reversecoder.quote.model.Author;
import com.reversecoder.quote.model.FoldableQuote;
import com.reversecoder.quote.model.Quote;
import com.reversecoder.quote.util.AllConstants;
import com.reversecoder.quote.util.DataHandler;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.armcha.ribble.presentation.widget.AnimatedImageView;
import io.armcha.ribble.presentation.widget.AnimatedTextView;
import io.armcha.ribble.presentation.widget.ArcView;

import static com.reversecoder.quote.util.AppUtils.flashingView;
import static com.reversecoder.quote.util.DataHandler.mAllMappedQuotes;

public class QuoteDetailActivity extends BaseActivity {

    Author mAuthor;
    int mSelectedPosition = -1;
    private ArrayList<Quote> mAllQuotes = new ArrayList<Quote>();
    //    private ArrayList<MappedQuote> mAllMappedQuotes = new ArrayList<MappedQuote>();
    private String TAG = QuoteDetailActivity.class.getSimpleName();
    TextSwitcher tsQuoteCounter;

    //FoldableListLayout
    FoldableListLayout foldableListLayout;
    QuoteDetailAdapter quoteDetailAdapter;
    private int lastPagePosition = 0;

    //toolbar
    ArcView arcMenuView;
    AnimatedImageView arcMenuImage;
    AnimatedTextView toolbarTitle;
    Toolbar toolbar;
    Button btnContextMenu;

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

        tsQuoteCounter = (TextSwitcher) findViewById(R.id.ts_quote_counter);
        tsQuoteCounter.setFactory(new TextViewFactory(QuoteDetailActivity.this, R.style.CounterTextView, true));

        foldableListLayout = findViewById(R.id.foldable_list);

        initMenuFragment(false);

        //get quote list in background
        Intent intent = getIntent();
        GetQuoteTask getQuoteTask = new GetQuoteTask(QuoteDetailActivity.this, intent);
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

        tsQuoteCounter.setInAnimation(QuoteDetailActivity.this, animV[0]);
        tsQuoteCounter.setOutAnimation(QuoteDetailActivity.this, animV[1]);
        tsQuoteCounter.setText((currentPosition + 1) + "/" + totalCount);

        lastPagePosition = currentPosition;
    }

    private void initActions() {
        foldableListLayout.setOnFoldRotationListener(new FoldableListLayout.OnFoldRotationListener() {
            @Override
            public void onFoldRotation(float rotation, boolean isFromUser) {
                if (lastPagePosition == foldableListLayout.getPosition()) {
                    return;
                }
                switchCounter(foldableListLayout.getPosition(), quoteDetailAdapter.getCount());
            }
        });

        btnContextMenu.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                FoldableQuote foldableQuote = quoteDetailAdapter.getItem(foldableListLayout.getPosition());
                if (foldableQuote.isQuote()) {
                    initMenuFragment(foldableQuote.isFavourite());
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
        btnContextMenu = (Button) findViewById(R.id.btn_context_menu);
        btnContextMenu.setVisibility(View.VISIBLE);
        //Animate context menu
        flashingView(btnContextMenu, 2000);
        fragmentManager = getSupportFragmentManager();
    }

    public class GetQuoteTask extends AsyncTask<String, String, FoldableQuote[]> {

        private Context mContext;
        private Intent mIntent;

        public GetQuoteTask(Context context, Intent intent) {
            mContext = context;
            mIntent = intent;
        }

        @Override
        protected void onPreExecute() {
            mSelectedPosition = mIntent.getIntExtra(AllConstants.INTENT_KEY_AUTHOR_POSITION, -1);
            mAuthor = mIntent.getParcelableExtra(AllConstants.INTENT_KEY_AUTHOR);
//            mAllMappedQuotes = mIntent.getParcelableArrayListExtra(AllConstants.INTENT_KEY_MAPPED_QUOTE);

            if (mAuthor != null) {
                toolbarTitle.setAnimatedText(mAuthor.getAuthorName(), 0L);
            }
        }

        @Override
        protected FoldableQuote[] doInBackground(String... params) {
            FoldableQuote[] foldableQuotes = new FoldableQuote[10];

            if (mAllMappedQuotes != null && mAuthor != null) {
                if (mAllMappedQuotes.size() > 0) {
                    mAllQuotes = DataHandler.getAllQuotes(mAuthor, mAllMappedQuotes);

                    int size = mAllQuotes.size();
                    foldableQuotes = new FoldableQuote[size];
                    TypedArray images = mContext.getResources().obtainTypedArray(R.array.paintings_images);

                    for (int i = 0; i < size; i++) {
                        Quote quote = mAllQuotes.get(i);
                        final int imageId = images.getResourceId(new Random().nextInt(images.length()), -1);
                        foldableQuotes[i] = new FoldableQuote(quote.getQuoteDescription(), quote.isFavourite(), quote.isQuote(), quote.getLanguage(), quote.getAuthor(), imageId);
                    }

                    images.recycle();
                }
            }

            return foldableQuotes;
        }

        @Override
        protected void onPostExecute(FoldableQuote[] result) {
            if (result != null && result.length > 0) {
                quoteDetailAdapter = new QuoteDetailAdapter(mContext, result);
                foldableListLayout.setAdapter(quoteDetailAdapter);

                switchCounter(foldableListLayout.getPosition(), quoteDetailAdapter.getCount());
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
                switch (position) {
                    case 0:
                        break;
                    case 1: {
                        Log.d(TAG, "FoldableItemPosition: " + foldableListLayout.getPosition() + "");
                        FoldableQuote foldableQuote = quoteDetailAdapter.getItem(foldableListLayout.getPosition());
                        Log.d(TAG, "FoldableItem: " + foldableQuote.toString());
                        FoldableQuote updatedFoldableQuote;
                        if (foldableQuote.isFavourite()) {
                            updatedFoldableQuote = DataHandler.setFavouriteForAuthorFragment(foldableQuote, false);
                            Log.d(TAG, "if favourite: " + foldableQuote.toString());
                        } else {
                            updatedFoldableQuote = DataHandler.setFavouriteForAuthorFragment(foldableQuote, true);
                            Log.d(TAG, "else favourite: " + foldableQuote.toString());
                        }
                        Log.d(TAG, "FoldableItem updated: " + updatedFoldableQuote.toString());

                        //Update current foldable list
                        quoteDetailAdapter.updateItem(foldableListLayout.getPosition(), updatedFoldableQuote);

                        break;
                    }
                    case 2:
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
        close.setResource(R.drawable.icn_close);

        MenuObject addToFavourite = new MenuObject(getString(R.string.context_menu_add_to_favourite));
        addToFavourite.setResource(isFavourite ? R.drawable.icn_6 : R.drawable.icn_4);

        MenuObject copyToClipboard = new MenuObject(getString(R.string.context_menu_copy_to_clipboard));
        copyToClipboard.setResource(R.drawable.icn_1);

        MenuObject shareToFriend = new MenuObject(getString(R.string.share_to_friends));
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.icn_3));
        shareToFriend.setDrawable(bd);

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
            finish();
        }
    }
}