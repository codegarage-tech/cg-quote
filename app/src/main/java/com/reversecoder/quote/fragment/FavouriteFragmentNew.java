package com.reversecoder.quote.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lombokcyberlab.android.multicolortextview.MultiColorTextView;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;
import com.reversecoder.library.util.AllSettingsManager;
import com.reversecoder.quote.R;
import com.reversecoder.quote.activity.HomeActivity;
import com.reversecoder.quote.adapter.AuthorAdapter;
import com.reversecoder.quote.adapter.FavouriteQuoteAdapter;
import com.reversecoder.quote.interfaces.OnFragmentBackPressedListener;
import com.reversecoder.quote.model.MappedQuote;
import com.reversecoder.quote.model.Quote;
import com.reversecoder.quote.util.DataHandler;

import java.util.ArrayList;

import br.com.stickyindex.StickyIndex;
import cc.solart.wave.WaveSideBarView;

import static com.reversecoder.quote.util.DataHandler.mAllMappedFavouriteQuotes;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class FavouriteFragmentNew extends Fragment implements OnFragmentBackPressedListener {

    private View parentView;
    MultiColorTextView tvLoadingMessage;
    ImageView ivLoading;
    RelativeLayout rlLoadingView;
    RecyclerView recyclerViewFavouriteAuthor;
    AuthorAdapter favouriteAuthorAdapter;
    InputData inputData;
    private static String TAG = FavouriteFragmentNew.class.getSimpleName();

    //sticky index
    StickyIndex indexContainer;

    //waveside bar
    private WaveSideBarView mSideBarView;

    //foldablelayout
    private View listTouchInterceptor;
    private FrameLayout detailsLayout;
    private UnfoldableView unfoldableView;
    int mLastSelectedAuthor = -1;

    //Author detail in foldablelayout
    RecyclerView recyclerViewFavouriteQuote;
    FavouriteQuoteAdapter favouriteQuoteAdapter;

    //Card slider
    private CardSliderLayoutManager cardSliderLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_favourite_new, container, false);

        initDatabase();

        return parentView;
    }

    private void setUpViews() {
        //Loading view
        rlLoadingView = (RelativeLayout) parentView.findViewById(R.id.rl_loading_view);
        ivLoading = (ImageView) parentView.findViewById(R.id.iv_loading);
        Glide
                .with(getActivity())
                .load(R.drawable.gif_loading)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .into(ivLoading);

        tvLoadingMessage = (MultiColorTextView) parentView.findViewById(R.id.tv_loading_message);
        tvLoadingMessage.setText(getString(R.string.txt_loading_message));

        //Activity content view
        recyclerViewFavouriteAuthor = (RecyclerView) parentView.findViewById(R.id.rv_person_info);
        indexContainer = (StickyIndex) parentView.findViewById(R.id.sticky_index_container);
        mSideBarView = (WaveSideBarView) parentView.findViewById(R.id.wave_sidebar);
        favouriteAuthorAdapter = new AuthorAdapter(getActivity());
        recyclerViewFavouriteAuthor.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewFavouriteAuthor.setAdapter(favouriteAuthorAdapter);

        //foldablelayout
        initFoldableLayout();
    }

    private void initData() {
        favouriteAuthorAdapter.addAll(mAllMappedFavouriteQuotes);
        favouriteAuthorAdapter.notifyDataSetChanged();

        //sticky header
        if (mAllMappedFavouriteQuotes.size() > 1) {
            indexContainer.setDataSet(getIndexList(mAllMappedFavouriteQuotes));
            indexContainer.setOnScrollListener(recyclerViewFavouriteAuthor);
        }

        //waveside bar
        mSideBarView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                for (int i = 0; i < mAllMappedFavouriteQuotes.size(); i++) {
                    if (String.valueOf(mAllMappedFavouriteQuotes.get(i).getAuthor().getAuthorName().charAt(0)).equals(letter)) {
                        ((LinearLayoutManager) recyclerViewFavouriteAuthor.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });

        initActions();
    }

    private void initActions() {
        implementsRecyclerViewOnItemClickListener();
    }

    /*****************
     * Sticky header *
     *****************/
    public static char[] getIndexList(ArrayList<MappedQuote> list) {
        char[] result = new char[0];
        if (list.size() > 0) {
            Log.d(TAG, "List size: " + list.size());
            result = new char[list.size()];
            Log.d(TAG, "result size: " + result.length);
            for (int i = 0; i < list.size(); i++) {
                result[i] = Character.toUpperCase(list.get(i).getAuthor().getAuthorName().charAt(0));
                Log.d(TAG, "result " + i + " is: " + result[i]);
            }
            Log.d(TAG, "result size final : " + result.length);
        }
        return result;
    }

    public void initDatabase() {
        if (inputData == null) {
            inputData = new InputData();
        } else if (inputData.getStatus() == AsyncTask.Status.RUNNING) {
            inputData.cancel(true);
            mAllMappedFavouriteQuotes.clear();
        }
        inputData.execute();
    }

    /***********
     * Recyclerview action
     ***************/
    private void implementsRecyclerViewOnItemClickListener() {
//        recyclerViewFavouriteAuthor.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getActivity(),
//                new RecyclerViewOnItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
        favouriteAuthorAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mLastSelectedAuthor = position;
                MappedQuote mappedQuote = favouriteAuthorAdapter.getItem(position);
//                        if (mappedQuote.getAuthor().isAuthor()) {
//                            Language language = getLanguage(SELECTED_LANGUAGE);
//                            Log.d(TAG, mappedQuote.toString() + "\nId: " + mappedQuote.getAuthor().getId() + "");
//                            Log.d(TAG, language.toString() + "\nId: " + language.getId() + "");
//
//                            Intent intentQuoteList = new Intent(getActivity(), FavouriteAuthorDetailActivity.class);
//                            intentQuoteList.putExtra(AllConstants.INTENT_KEY_FAVOURITE_AUTHOR, mappedQuote.getAuthor());
////                            intentQuoteList.putParcelableArrayListExtra(AllConstants.INTENT_KEY_MAPPED_QUOTE, mAllMappedQuotes);
//                            intentQuoteList.putExtra(AllConstants.INTENT_KEY_FAVOURITE_AUTHOR_POSITION, position);
//                            getActivity().startActivityForResult(intentQuoteList, REQUEST_CODE_FAVOURITE_FRAGMENT);
//                        }

                openAuthorDetails(detailsLayout, (view.findViewById(R.id.item_view)), mappedQuote);
            }
        });
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult");

        switch (requestCode) {
            case REQUEST_CODE_FAVOURITE_FRAGMENT: {
                if (data != null && resultCode == RESULT_OK) {
                    Log.d(TAG, "RESULT_OK");

                    boolean isBackPressedAutomatically = data.getBooleanExtra(INTENT_KEY_FAVOURITE_UPDATED_AUTHOR_BACK_PRESSED_AUTOMATICALLY, false);
                    Log.d(TAG, "isBackPressedAutomatically: " + isBackPressedAutomatically);

//                    Author updatedAuthorResult = data.getParcelableExtra(INTENT_KEY_FAVOURITE_UPDATED_AUTHOR_DETAIL);
//                    Log.d(TAG, "updatedAuthorResult: " + updatedAuthorResult.toString());
//
//                    if (isBackPressedAutomatically) {
//                        favouriteAuthorAdapter.remove(favouriteAuthorAdapter.getItemPosition(updatedAuthorResult));
//                    }else{
//                    }

                    ArrayList<MappedQuote> removedItem = new ArrayList<>();
                    for (int i = 0; i < favouriteAuthorAdapter.getAllData().size(); i++) {
                        if (isFavouriteItemFound(favouriteAuthorAdapter.getAllData().get(i).getAuthor()) == null) {
                            removedItem.add(favouriteAuthorAdapter.getAllData().get(i));
                        }
                    }

                    for (int i = 0; i < removedItem.size(); i++) {
                        favouriteAuthorAdapter.remove(removedItem.get(i));
                    }

                    if (favouriteAuthorAdapter.getCount() == 0) {
                        indexContainer.setVisibility(View.GONE);
                        rlLoadingView.setVisibility(View.VISIBLE);
                        ivLoading.setVisibility(View.GONE);
                        tvLoadingMessage.setVisibility(View.VISIBLE);
                        tvLoadingMessage.setText(getString(R.string.txt_no_data_found));
                    }

                }
                break;
            }
            default:
                break;
        }
    }*/

    /******************************
     * Methods for database input *
     ******************************/
    public class InputData extends AsyncTask<String, String, ArrayList<MappedQuote>> {

        @Override
        protected void onPreExecute() {
            setUpViews();

            rlLoadingView.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<MappedQuote> doInBackground(String... params) {
            return DataHandler.initAllFavouriteData();
        }

        @Override
        protected void onPostExecute(ArrayList<MappedQuote> result) {

            if (result != null && result.size() > 0) {
                //This checking for avoiding "Fragment not attached to Activity when finish AsyncTask & Fragment"
                if (getActivity() != null && isAdded() && (!isCancelled())) {
                    rlLoadingView.setVisibility(View.GONE);
                    initData();
                }
            } else {
                //This checking for avoiding "Fragment not attached to Activity when finish AsyncTask & Fragment"
                if (getActivity() != null && isAdded() && (!isCancelled())) {
                    ivLoading.setVisibility(View.GONE);
                    tvLoadingMessage.setText(getString(R.string.txt_no_data_found));
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        if (inputData.getStatus() == AsyncTask.Status.RUNNING) {
            Log.d(TAG, "Destroying favourite fragment");
            inputData.cancel(true);
            mAllMappedFavouriteQuotes.clear();
            Log.d(TAG, "data size: " + mAllMappedFavouriteQuotes.size());
        }

        super.onDestroy();
    }

    /******************
     * FoldableLayout *
     ******************/
    private void initFoldableLayout() {

        listTouchInterceptor = (View) parentView.findViewById(R.id.touch_interceptor_view);
        listTouchInterceptor.setClickable(false);

        detailsLayout = (FrameLayout) parentView.findViewById(R.id.details_layout);
        detailsLayout.setVisibility(View.INVISIBLE);

        unfoldableView = (UnfoldableView) parentView.findViewById(R.id.unfoldable_view);
        unfoldableView.setGesturesEnabled(false);

        Bitmap glance = BitmapFactory.decodeResource(getResources(), R.drawable.unfold_glance);
        unfoldableView.setFoldShading(new GlanceFoldShading(glance));

        unfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {

            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
                detailsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUnfolded(final UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
                detailsLayout.setVisibility(View.INVISIBLE);

                updateToolbar(getActivity().getString(R.string.ribble_menu_item_favourite), true);
            }
        });
    }

    public void openAuthorDetails(View detailLayout, View rowItemView, MappedQuote mappedQuote) {

        initAuthorDetailRecyclerView(detailLayout, mappedQuote);

        unfoldableView.unfold(rowItemView, detailsLayout);

        updateToolbar(mappedQuote.getAuthor().getAuthorName(), false);
    }

    private void updateToolbar(String title, boolean isHamburger) {
        if (isHamburger) {
            ((HomeActivity) getActivity()).setArcHamburgerIconState();
        } else {
            ((HomeActivity) getActivity()).arcMenuView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onFragmentBackPressed();
                }
            });
            ((HomeActivity) getActivity()).arcMenuImage.setAnimatedImage(R.drawable.arrow_left, 0L);
        }
        if (!AllSettingsManager.isNullOrEmpty(title)) {
            ((HomeActivity) getActivity()).setToolBarTitle(title);
        }
    }

    public void updateFavouriteAuthorList() {
        if (mLastSelectedAuthor != -1) {
            Log.d("AuthorAdapter: ", favouriteAuthorAdapter.getItem(mLastSelectedAuthor).getQuotes().size() + "");
            Log.d("AuthorAdapterDetail: ", favouriteQuoteAdapter.getCount() + "");
            //If favourite data are changed for the selected author
            if (favouriteAuthorAdapter.getItem(mLastSelectedAuthor).getQuotes().size() != favouriteQuoteAdapter.getCount()) {
                //update favourite data into specific author
                favouriteAuthorAdapter.getItem(mLastSelectedAuthor).setQuotes(new ArrayList<Quote>(favouriteQuoteAdapter.getAllData()));

                //If favourite item are empty for the selected author
                if (favouriteQuoteAdapter.getCount() == 0) {
                    favouriteAuthorAdapter.remove(mLastSelectedAuthor);
                    favouriteAuthorAdapter.notifyDataSetChanged();

                    //Update author for favourite data changes
                    if (favouriteAuthorAdapter.getCount() == 0) {
                        //update sticky index
                        indexContainer.setVisibility(View.GONE);
                        rlLoadingView.setVisibility(View.VISIBLE);
                        ivLoading.setVisibility(View.GONE);
                        tvLoadingMessage.setVisibility(View.VISIBLE);
                        tvLoadingMessage.setText(getString(R.string.txt_no_data_found));
                    }
                }
            }
        }
    }

    @Override
    public void onFragmentBackPressed() {
        if (unfoldableView.isUnfolded() || unfoldableView.isUnfolding()) {
            unfoldableView.foldBack();
            //release detail view from recyclerview
            if (recyclerViewFavouriteQuote != null) {
                new CardSnapHelper().setOnFlingListener(recyclerViewFavouriteQuote, null);
            }
            updateFavouriteAuthorList();

            //Change title and action of hamburger
            ((HomeActivity) getActivity()).setArcHamburgerIconState();
            ((HomeActivity) getActivity()).setToolBarTitle(getActivity().getString(R.string.ribble_menu_item_favourite));
        } else {
            getActivity().finish();
        }
    }

    /****************************
     * EasyRecyclerView methods *
     ****************************/
    private void initAuthorDetailRecyclerView(View detailLayout, final MappedQuote mappedQuote) {
        recyclerViewFavouriteQuote = (RecyclerView) detailLayout.findViewById(R.id.rv_favourite_quote);
        favouriteQuoteAdapter = new FavouriteQuoteAdapter(getActivity());
        recyclerViewFavouriteQuote.setAdapter(favouriteQuoteAdapter);
        favouriteQuoteAdapter.addAll(mappedQuote.getQuotes());
        favouriteQuoteAdapter.notifyDataSetChanged();
        recyclerViewFavouriteQuote.setHasFixedSize(true);
        cardSliderLayoutManager = new CardSliderLayoutManager(getActivity());
        recyclerViewFavouriteQuote.setLayoutManager(cardSliderLayoutManager);
        new CardSnapHelper().attachToRecyclerView(recyclerViewFavouriteQuote);

        recyclerViewFavouriteQuote.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    onActiveCardChange();
                }
            }
        });

//        favouriteQuoteAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Toast.makeText(getActivity(), "Clicked " + position, Toast.LENGTH_SHORT).show();
//
//                BlurLayout blurLayout = (BlurLayout)view.findViewById(R.id.blur_layout_favourite_quote);
//                View hover = LayoutInflater.from(getActivity()).inflate(R.layout.layout_hover_favourite_quote, null);
//                blurLayout.setHoverView(hover);
//                blurLayout.showHover();
//            }
//        });
    }
}