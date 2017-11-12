package com.danielstone.materialaboutlibrary;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.danielstone.materialaboutlibrary.adapters.MaterialAboutListAdapter;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.danielstone.materialaboutlibrary.util.DefaultViewTypeManager;
import com.danielstone.materialaboutlibrary.util.ViewTypeManager;
import com.eggheadgames.aboutbox.R;

import java.lang.ref.WeakReference;

public abstract class MaterialAboutActivity extends AppCompatActivity {

    private MaterialAboutList list = new MaterialAboutList.Builder().build();
    //    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MaterialAboutListAdapter adapter;

    @NonNull
    protected abstract MaterialAboutList getMaterialAboutList(@NonNull Context context);

    @Nullable
    protected abstract CharSequence getActivityTitle();

    protected abstract int setLayoutResource();

    protected abstract void setToolbar();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        verifyAttributesExist();

        setContentView(setLayoutResource());

//        CharSequence title = getActivityTitle();
//        if (title == null)
//            setTitle(R.string.mal_title_about);
//        else
//            setTitle(title);


        assignViews();
        initViews();

        ListTask task = new ListTask(this);
        task.execute();
    }

//    private void verifyAttributesExist() {
//        TypedValue typedValue = new TypedValue();
//        boolean malColorPrimaryExists =
//                getTheme().resolveAttribute(R.attr.mal_color_primary, typedValue, true);
//        boolean malColorSecondaryExists =
//                getTheme().resolveAttribute(R.attr.mal_color_secondary, typedValue, true);
//        if (!malColorPrimaryExists || !malColorSecondaryExists) {
//            throw new IllegalStateException(String.format("The current theme doesn't provide %s " +
//                            "and/or %s. Please use a theme provided by the library or an extension.",
//                    getResources().getResourceEntryName(R.attr.mal_color_primary),
//                    getResources().getResourceEntryName(R.attr.mal_color_secondary)));
//        }
//    }

    private void assignViews() {
        setToolbar();
//        toolbar = (Toolbar) findViewById(R.id.mal_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.mal_recyclerview);
        recyclerView.setAlpha(0f);
        recyclerView.setTranslationY(20);
    }

    private void initViews() {
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
        adapter = new MaterialAboutListAdapter(list, getViewTypeManager());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @NonNull
    protected ViewTypeManager getViewTypeManager() {
        return new DefaultViewTypeManager();
    }

    @NonNull
    protected MaterialAboutList getMaterialAboutList() {
        return list;
    }

    protected boolean shouldAnimate() {
        return true;
    }

    protected void refreshMaterialAboutList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onTaskFinished(@Nullable MaterialAboutList materialAboutList) {
        if (materialAboutList != null) {
            list = materialAboutList;
            adapter.swapData(list);

            if (shouldAnimate()) {
                recyclerView.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setDuration(400)
                        .setInterpolator(new FastOutSlowInInterpolator()).start();
            } else {
                recyclerView.setAlpha(1f);
                recyclerView.setTranslationY(0f);
            }
        } else {
            finish();//?? why we remain here anyway?
        }
    }

    protected void setMaterialAboutList(MaterialAboutList materialAboutList) {
        list = materialAboutList;
        adapter.swapData(materialAboutList);
    }

//    protected void setScrollToolbar(boolean scrollToolbar) {
//        if (toolbar != null) {
//            AppBarLayout.LayoutParams params =
//                    (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
//            if (scrollToolbar) {
//                params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
//                        | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
//            } else {
//                params.setScrollFlags(0);
//            }
//        }
//    }

    private static class ListTask extends AsyncTask<String, String, MaterialAboutList> {

        private WeakReference<MaterialAboutActivity> context;

        ListTask(MaterialAboutActivity context) {
            this.context = new WeakReference<>(context);
        }

        @Override
        protected MaterialAboutList doInBackground(String... params) {
            return isCancelled() || context.get() == null ? null : context.get().getMaterialAboutList(context.get());
        }

        @Override
        protected void onPostExecute(MaterialAboutList materialAboutList) {
            super.onPostExecute(materialAboutList);
            if (context.get() != null) {
                if (!context.get().isFinishing()) {
                    context.get().onTaskFinished(materialAboutList);
                }
            }
            context = null;
        }
    }
}
