package com.franmontiel.attributionpresenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.franmontiel.attributionpresenter.R;
import com.franmontiel.attributionpresenter.entities.Attribution;
import com.franmontiel.attributionpresenter.entities.LicenseInfo;
import com.franmontiel.attributionpresenter.listeners.OnAttributionClickListener;
import com.franmontiel.attributionpresenter.listeners.OnLicenseClickListener;
import com.franmontiel.attributionpresenter.util.AttributionPresenterCreator;
import com.franmontiel.attributionpresenter.view.AnimatedImageView;
import com.franmontiel.attributionpresenter.view.AnimatedTextView;
import com.franmontiel.attributionpresenter.view.ArcView;

import static com.franmontiel.attributionpresenter.util.Constants.INTENT_KEY_ATTRIBUTION;

public class LicenseActivity extends AppCompatActivity {

    //toolbar
    ArcView arcMenuView;
    AnimatedImageView arcMenuImage;
    AnimatedTextView toolbarTitle;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        initView();
    }

    private void initView() {

        initToolBar();

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(AttributionPresenterCreator.create(
                this,
                new OnAttributionClickListener() {
                    @Override
                    public boolean onAttributionClick(Attribution attribution) {
                        Intent intentLicenseDetail = new Intent(LicenseActivity.this, LicenseDetailActivity.class);
                        intentLicenseDetail.putExtra(INTENT_KEY_ATTRIBUTION, attribution);
                        startActivity(intentLicenseDetail);
                        return true;
                    }
                },
                new OnLicenseClickListener() {
                    @Override
                    public boolean onLicenseClick(LicenseInfo licenseInfo) {
                        return true;
                    }
                }).getAdapter());
    }

    private void initToolBar() {
        toolbarTitle = (AnimatedTextView) findViewById(R.id.toolbarTitle);
        arcMenuImage = (AnimatedImageView) findViewById(R.id.arcImage);
        arcMenuView = (ArcView) findViewById(R.id.arcView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbarTitle.setAnimatedText(getString(R.string.title_activity_third_party_notice), 0L);

        arcMenuImage.setAnimatedImage(R.drawable.arrow_left, 0L);
        arcMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
