package com.reversecoder.quote.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.reversecoder.quote.R;
import com.reversecoder.quote.activity.FavouriteAuthorDetailActivity;
import com.reversecoder.quote.adapter.AuthorAdapter;
import com.reversecoder.quote.interfaces.RecyclerViewOnItemClickListener;
import com.reversecoder.quote.model.Language;
import com.reversecoder.quote.model.MappedQuote;
import com.reversecoder.quote.util.AllConstants;
import com.reversecoder.quote.util.DataHandler;

import java.util.ArrayList;

import br.com.stickyindex.StickyIndex;
import cc.solart.wave.WaveSideBarView;

import static android.app.Activity.RESULT_OK;
import static com.reversecoder.quote.util.AllConstants.INTENT_KEY_FAVOURITE_UPDATED_AUTHOR_BACK_PRESSED_AUTOMATICALLY;
import static com.reversecoder.quote.util.AllConstants.REQUEST_CODE_FAVOURITE_FRAGMENT;
import static com.reversecoder.quote.util.AllConstants.SELECTED_LANGUAGE;
import static com.reversecoder.quote.util.DataHandler.getLanguage;
import static com.reversecoder.quote.util.DataHandler.isFavouriteItemFound;
import static com.reversecoder.quote.util.DataHandler.mAllMappedFavouriteQuotes;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class FavouriteFragment extends Fragment {

    private View parentView;

    TextView tvLoadingMessage;
    ImageView ivLoading;
    RelativeLayout rlLoadingView;
    RecyclerView recyclerViewPersonInfo;
    AuthorAdapter personAdapter;
    //    GridLayoutManager gridLayoutManager;
    InputData inputData;
    private static String TAG = FavouriteFragment.class.getSimpleName();

    //sticky index
    StickyIndex indexContainer;

    //waveside bar
    private WaveSideBarView mSideBarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_favourite, container, false);

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

        tvLoadingMessage = (TextView) parentView.findViewById(R.id.tv_loading_message);
        tvLoadingMessage.setText(getString(R.string.txt_loading_message));

        //Activity content view
        recyclerViewPersonInfo = (RecyclerView) parentView.findViewById(R.id.rv_person_info);
        indexContainer = (StickyIndex) parentView.findViewById(R.id.sticky_index_container);
        mSideBarView = (WaveSideBarView) parentView.findViewById(R.id.wave_sidebar);
        personAdapter = new AuthorAdapter(getActivity());
//        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewPersonInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewPersonInfo.setAdapter(personAdapter);
    }

    private void initData() {
        personAdapter.addAll(mAllMappedFavouriteQuotes);
        personAdapter.notifyDataSetChanged();

        //sticky header
        if (mAllMappedFavouriteQuotes.size() > 1) {
            indexContainer.setDataSet(getIndexList(mAllMappedFavouriteQuotes));
            indexContainer.setOnScrollListener(recyclerViewPersonInfo);
        }

        //waveside bar
        mSideBarView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                for (int i = 0; i < mAllMappedFavouriteQuotes.size(); i++) {
                    if (String.valueOf(mAllMappedFavouriteQuotes.get(i).getAuthor().getAuthorName().charAt(0)).equals(letter)) {
                        ((LinearLayoutManager) recyclerViewPersonInfo.getLayoutManager()).scrollToPositionWithOffset(i, 0);
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
        recyclerViewPersonInfo.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getActivity(),
                new RecyclerViewOnItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        MappedQuote mappedQuote = ((AuthorAdapter) recyclerViewPersonInfo.getAdapter()).getItem(position);
                        if (mappedQuote.getAuthor().isAuthor()) {
                            Language language = getLanguage(SELECTED_LANGUAGE);
                            Log.d(TAG, mappedQuote.toString() + "\nId: " + mappedQuote.getAuthor().getId() + "");
                            Log.d(TAG, language.toString() + "\nId: " + language.getId() + "");

                            Intent intentQuoteList = new Intent(getActivity(), FavouriteAuthorDetailActivity.class);
                            intentQuoteList.putExtra(AllConstants.INTENT_KEY_FAVOURITE_AUTHOR, mappedQuote.getAuthor());
//                            intentQuoteList.putParcelableArrayListExtra(AllConstants.INTENT_KEY_MAPPED_QUOTE, mAllMappedQuotes);
                            intentQuoteList.putExtra(AllConstants.INTENT_KEY_FAVOURITE_AUTHOR_POSITION, position);
                            getActivity().startActivityForResult(intentQuoteList, REQUEST_CODE_FAVOURITE_FRAGMENT);
                        }
                    }
                }));
    }

    @Override
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
                    for (int i = 0; i < personAdapter.getAllData().size(); i++) {
                        if (isFavouriteItemFound(personAdapter.getAllData().get(i).getAuthor()) == null) {
                            removedItem.add(personAdapter.getAllData().get(i));
                        }
                    }

                    for (int i = 0; i < removedItem.size(); i++) {
                        personAdapter.remove(removedItem.get(i));
                    }

                    if (personAdapter.getCount() == 0) {
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
    }

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
}