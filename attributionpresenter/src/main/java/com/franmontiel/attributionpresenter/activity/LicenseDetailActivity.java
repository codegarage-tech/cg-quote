package com.franmontiel.attributionpresenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.franmontiel.attributionpresenter.R;
import com.franmontiel.attributionpresenter.entities.Attribution;
import com.franmontiel.attributionpresenter.view.AnimatedImageView;
import com.franmontiel.attributionpresenter.view.AnimatedTextView;
import com.franmontiel.attributionpresenter.view.ArcView;

import static com.franmontiel.attributionpresenter.util.Constants.INTENT_KEY_ATTRIBUTION;

public class LicenseDetailActivity extends AppCompatActivity {

    Attribution mAttribution;

    //toolbar
    ArcView arcMenuView;
    AnimatedImageView arcMenuImage;
    AnimatedTextView toolbarTitle;
    Toolbar toolbar;

    //License info
    TextView tvLicenseName, tvLicenseCopyright, tvLicenseDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_detail);

        initView();
    }

    private void initView() {

        Intent intentData = getIntent();
        mAttribution = intentData.getParcelableExtra(INTENT_KEY_ATTRIBUTION);
        Log.d("mAttribution", mAttribution.toString());

        initToolBar();

        tvLicenseName = (TextView) findViewById(R.id.tv_license_name);
        tvLicenseCopyright = (TextView) findViewById(R.id.tv_license_copyright);
        tvLicenseDetail = (TextView) findViewById(R.id.tv_license_detail);

        tvLicenseName.setText((mAttribution.getLicensesInfo().size() > 0) ? mAttribution.getLicensesInfo().get(0).getName() : "");
        tvLicenseCopyright.setText((mAttribution.getCopyrightNotices().size() > 0) ? mAttribution.getCopyrightNotices().get(0) : "");
        tvLicenseDetail.setText((mAttribution.getLicensesInfo().size() > 0) ? mAttribution.getLicensesInfo().get(0).getText() : "");
    }

    private void initToolBar() {
        toolbarTitle = (AnimatedTextView) findViewById(R.id.toolbarTitle);
        arcMenuImage = (AnimatedImageView) findViewById(R.id.arcImage);
        arcMenuView = (ArcView) findViewById(R.id.arcView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (mAttribution != null) {
            toolbarTitle.setAnimatedText(mAttribution.getName(), 0L);
        }
        arcMenuImage.setAnimatedImage(R.drawable.arrow_left, 0L);
        arcMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
