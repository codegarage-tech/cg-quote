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
import com.lombokcyberlab.android.multicolortextview.MultiColorTextView;

import java.util.ArrayList;

import br.com.stickyindex.StickyIndex;
import cc.solart.wave.WaveSideBarView;
import spencerstudios.com.bungeelib.Bungee;
import tech.codegarage.quotes.R;
import tech.codegarage.quotes.activity.AuthorDetailActivity;
import tech.codegarage.quotes.adapter.AuthorAdapter;
import tech.codegarage.quotes.interfaces.OnFragmentBackPressedListener;
import tech.codegarage.quotes.interfaces.RecyclerViewOnItemClickListener;
import tech.codegarage.quotes.model.AppDataBuilder;
import tech.codegarage.quotes.util.AllConstants;

import static tech.codegarage.quotes.model.AppDataHandler.getAllQuotes;
//import static DataHandler.mAllMappedQuotes;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class AuthorFragment extends Fragment implements OnFragmentBackPressedListener {

    private View parentView;

    MultiColorTextView tvLoadingMessage;
    ImageView ivLoading;
    RelativeLayout rlLoadingView;
    RecyclerView recyclerViewPersonInfo;
    AuthorAdapter personAdapter;
    //    GridLayoutManager gridLayoutManager;
    InputData inputData;
    private static String TAG = AuthorFragment.class.getSimpleName();

    //sticky index
    StickyIndex indexContainer;

    //waveside bar
    private WaveSideBarView mSideBarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_author, container, false);

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
        recyclerViewPersonInfo = (RecyclerView) parentView.findViewById(R.id.rv_person_info);
        indexContainer = (StickyIndex) parentView.findViewById(R.id.sticky_index_container);
        mSideBarView = (WaveSideBarView) parentView.findViewById(R.id.wave_sidebar);
        personAdapter = new AuthorAdapter(getActivity());
        recyclerViewPersonInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewPersonInfo.setAdapter(personAdapter);
    }

    private void initData(ArrayList<AppDataBuilder> appDataBuilders) {
        personAdapter.clear();
        personAdapter.addAll(appDataBuilders);
        personAdapter.notifyDataSetChanged();

        //sticky header
        setStickyIndex(appDataBuilders);

        //waveside bar
        setWaveSideBar(appDataBuilders);

        initActions();
    }

    private void setStickyIndex(ArrayList<AppDataBuilder> stickyIndexes) {
        if (stickyIndexes.size() > 1) {
            indexContainer.setDataSet(getIndexList(stickyIndexes));
            indexContainer.setOnScrollListener(recyclerViewPersonInfo);
        } else if (stickyIndexes.size() == 1) {
            indexContainer.setVisibility(View.INVISIBLE);
        }
    }

    private void setWaveSideBar(final ArrayList<AppDataBuilder> waveSideBar) {
        if (waveSideBar.size() > 0) {
            mSideBarView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
                @Override
                public void onLetterChange(String letter) {
                    for (int i = 0; i < waveSideBar.size(); i++) {
                        if (String.valueOf(waveSideBar.get(i).getAuthor().getAuthorName().charAt(0)).equals(letter)) {
                            ((LinearLayoutManager) recyclerViewPersonInfo.getLayoutManager()).scrollToPositionWithOffset(i, 0);
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
    public static char[] getIndexList(ArrayList<AppDataBuilder> list) {
        char[] result = new char[list.size()];
        int i = 0;
        for (AppDataBuilder c : list) {
            result[i] = Character.toUpperCase(c.getAuthor().getAuthorName().charAt(0));
            i++;
        }
        return result;
    }

    public void initDatabase() {
        if (inputData == null) {
            inputData = new InputData();
        } else if (inputData.getStatus() == AsyncTask.Status.RUNNING) {
            inputData.cancel(true);
//            mAllMappedQuotes.clear();
        }
        inputData.execute();
    }

    /***********************
     * Recyclerview action *
     ***********************/
    private void implementsRecyclerViewOnItemClickListener() {
        recyclerViewPersonInfo.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getActivity(),
                new RecyclerViewOnItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        AppDataBuilder appDataBuilder = ((AuthorAdapter) recyclerViewPersonInfo.getAdapter()).getItem(position);
                        if (appDataBuilder.getAuthor().isAuthor()) {
                            Intent intentQuoteList = new Intent(getActivity(), AuthorDetailActivity.class);
                            intentQuoteList.putExtra(AllConstants.INTENT_KEY_AUTHOR, appDataBuilder);
                            intentQuoteList.putExtra(AllConstants.INTENT_KEY_AUTHOR_POSITION, position);
                            getActivity().startActivity(intentQuoteList);
                            Bungee.slideUp(getActivity());
                        }
                    }
                }));
    }

    /******************************
     * Methods for database input *
     ******************************/
    public class InputData extends AsyncTask<String, String, ArrayList<AppDataBuilder>> {

        @Override
        protected void onPreExecute() {
            setUpViews();

            rlLoadingView.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<AppDataBuilder> doInBackground(String... params) {
//            if (AllSettingsManager.isNullOrEmpty(SessionManager.getStringSetting(getGlobalContext(), SESSION_DATA_DATA_BUILDER))) {
//                return initAllQuotes(null);
//            } else {
//                return getAllQuotes();
//            }
            return getAllQuotes();
        }

        @Override
        protected void onPostExecute(ArrayList<AppDataBuilder> result) {

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
            Log.d(TAG, "Destroying author fragment");
            inputData.cancel(true);
//            mAllMappedQuotes.clear();
//            Log.d(TAG, "data size: " + mAllMappedQuotes.size());
        }

        super.onDestroy();
    }

    @Override
    public void onFragmentBackPressed() {
        Bungee.slideDown(getActivity());
        getActivity().finish();
    }
}


