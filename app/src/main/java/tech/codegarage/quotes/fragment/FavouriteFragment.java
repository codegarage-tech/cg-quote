package tech.codegarage.quotes.fragment;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import tech.codegarage.quotes.activity.FavouriteQuoteDetailActivity;
import tech.codegarage.quotes.adapter.AuthorAdapter;
import tech.codegarage.quotes.model.database.LitePalDataBuilder;
import tech.codegarage.quotes.model.database.LitePalDataHandler;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lombokcyberlab.android.multicolortextview.MultiColorTextView;
import tech.codegarage.quotes.R;
import tech.codegarage.quotes.interfaces.OnFragmentBackPressedListener;
import tech.codegarage.quotes.util.AllConstants;

import java.util.ArrayList;

import br.com.stickyindex.StickyIndex;
import cc.solart.wave.WaveSideBarView;

import static android.app.Activity.RESULT_OK;
import static tech.codegarage.quotes.util.AllConstants.INTENT_KEY_FAVOURITE_UPDATED_QUOTES;
import static tech.codegarage.quotes.util.AllConstants.REQUEST_CODE_FAVOURITE_FRAGMENT;
//import static DataHandler.mAllMappedFavouriteQuotes;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class FavouriteFragment extends Fragment implements OnFragmentBackPressedListener {

    private View parentView;
    MultiColorTextView tvLoadingMessage;
    ImageView ivLoading;
    RelativeLayout rlLoadingView;
    RecyclerView recyclerViewFavouriteAuthor;
    AuthorAdapter favouriteAuthorAdapter;
    InputData inputData;
    private static String TAG = FavouriteFragment.class.getSimpleName();
    private int mLastSelectedAuthor = -1;

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

        tvLoadingMessage = (MultiColorTextView) parentView.findViewById(R.id.tv_loading_message);
        tvLoadingMessage.setText(getString(R.string.txt_loading_message));

        //Activity content view
        recyclerViewFavouriteAuthor = (RecyclerView) parentView.findViewById(R.id.rv_person_info);
        indexContainer = (StickyIndex) parentView.findViewById(R.id.sticky_index_container);
        mSideBarView = (WaveSideBarView) parentView.findViewById(R.id.wave_sidebar);
        favouriteAuthorAdapter = new AuthorAdapter(getActivity());
        recyclerViewFavouriteAuthor.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewFavouriteAuthor.setAdapter(favouriteAuthorAdapter);
    }

    private void initData(ArrayList<LitePalDataBuilder> litePalDataBuilders) {
        favouriteAuthorAdapter.clear();
        favouriteAuthorAdapter.addAll(litePalDataBuilders);
        favouriteAuthorAdapter.notifyDataSetChanged();

        //sticky header
        setStickyIndex(litePalDataBuilders);

        //waveside bar
        setWaveSideBar(litePalDataBuilders);

        initActions();
    }

    private void setStickyIndex(ArrayList<LitePalDataBuilder> stickyIndexes) {
        if (stickyIndexes.size() > 1) {
            indexContainer.setDataSet(getIndexList(stickyIndexes));
            indexContainer.setOnScrollListener(recyclerViewFavouriteAuthor);
        } else if (stickyIndexes.size() == 1) {
            indexContainer.setVisibility(View.INVISIBLE);
        }
    }

    private void setWaveSideBar(final ArrayList<LitePalDataBuilder> waveSideBar) {
        if (waveSideBar.size() > 0) {
            mSideBarView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
                @Override
                public void onLetterChange(String letter) {
                    for (int i = 0; i < waveSideBar.size(); i++) {
                        if (String.valueOf(waveSideBar.get(i).getLitePalAuthor().getAuthorName().charAt(0)).equals(letter)) {
                            ((LinearLayoutManager) recyclerViewFavouriteAuthor.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                            return;
                        }
                    }
                }
            });
        }
    }

    private void initActions() {
        implementsRecyclerViewOnItemClickListener();
    }

    /*****************
     * Sticky header *
     *****************/
    public static char[] getIndexList(ArrayList<LitePalDataBuilder> list) {
        char[] result = new char[0];
        if (list.size() > 0) {
            Log.d(TAG, "List size: " + list.size());
            result = new char[list.size()];
            Log.d(TAG, "result size: " + result.length);
            for (int i = 0; i < list.size(); i++) {
                result[i] = Character.toUpperCase(list.get(i).getLitePalAuthor().getAuthorName().charAt(0));
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
//            mAllMappedFavouriteQuotes.clear();
        }
        inputData.execute();
    }

    /***********************
     * Recyclerview action *
     ***********************/
    private void implementsRecyclerViewOnItemClickListener() {

        favouriteAuthorAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //set selected items info globally
                mLastSelectedAuthor = position;
                LitePalDataBuilder litePalDataBuilder = favouriteAuthorAdapter.getItem(mLastSelectedAuthor);

                Intent intentFavouriteQuoteDetail = new Intent(getActivity(), FavouriteQuoteDetailActivity.class);
                intentFavouriteQuoteDetail.putExtra(AllConstants.INTENT_KEY_FAVOURITE_AUTHOR_POSITION, mLastSelectedAuthor);
                intentFavouriteQuoteDetail.putExtra(AllConstants.INTENT_KEY_FAVOURITE_AUTHOR, litePalDataBuilder);
                startActivityForResult(intentFavouriteQuoteDetail, REQUEST_CODE_FAVOURITE_FRAGMENT);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult");

        switch (requestCode) {
            case REQUEST_CODE_FAVOURITE_FRAGMENT: {
                if (data != null && resultCode == RESULT_OK) {
                    Log.d(TAG, "RESULT_OK");

                    ArrayList<LitePalDataBuilder.LitePalQuoteBuilder> tempUpdatedQuoted = data.getParcelableArrayListExtra(INTENT_KEY_FAVOURITE_UPDATED_QUOTES);
                    if (tempUpdatedQuoted != null) {
                        if (tempUpdatedQuoted.size() > 0) {
                            for (int i = 0; i < tempUpdatedQuoted.size(); i++) {
                                Log.d(TAG, "tempUpdatedQuoted " + i + ": " + tempUpdatedQuoted.get(i).toString());
                            }

                            //set updated favourite quote into the author list
                            favouriteAuthorAdapter.getItem(mLastSelectedAuthor).setLitePalQuoteBuilders(tempUpdatedQuoted);
                        } else if (tempUpdatedQuoted.size() == 0) {
                            //remove author if there is no favourite quotes
                            favouriteAuthorAdapter.remove(mLastSelectedAuthor);
                            favouriteAuthorAdapter.notifyDataSetChanged();

                            //Refresh author listview
                            ArrayList<LitePalDataBuilder> tempMappedQuotes = new ArrayList<>(favouriteAuthorAdapter.getAllData());
                            initData(tempMappedQuotes);

                            //show empty author list if there is not author
                            if (favouriteAuthorAdapter.getCount() == 0) {
                                indexContainer.setVisibility(View.GONE);
                                rlLoadingView.setVisibility(View.VISIBLE);
                                ivLoading.setVisibility(View.GONE);
                                tvLoadingMessage.setVisibility(View.VISIBLE);
                                tvLoadingMessage.setText(getString(R.string.txt_no_data_found));
                            }
                        }
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
    public class InputData extends AsyncTask<String, String, ArrayList<LitePalDataBuilder>> {

        @Override
        protected void onPreExecute() {
            setUpViews();

            rlLoadingView.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<LitePalDataBuilder> doInBackground(String... params) {
//            return DataHandler.initAllFavouriteData();
            return LitePalDataHandler.getAllFavouriteQuotes();
        }

        @Override
        protected void onPostExecute(ArrayList<LitePalDataBuilder> result) {

            if (result != null && result.size() > 0) {
                //This checking for avoiding "Fragment not attached to Activity when finish AsyncTask & Fragment"
                if (getActivity() != null && isAdded() && (!isCancelled())) {
                    rlLoadingView.setVisibility(View.GONE);
                    initData(result);
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
//            mAllMappedFavouriteQuotes.clear();
//            Log.d(TAG, "data size: " + mAllMappedFavouriteQuotes.size());
        }

        super.onDestroy();
    }

    @Override
    public void onFragmentBackPressed() {
        getActivity().finish();
    }
}