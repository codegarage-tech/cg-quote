//package tech.codegarage.quotes.activity;
//
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//
//import com.patryk1007.fillme.FillMe;
//
//import co.mobiwise.library.ProgressLayout;
//import io.armcha.ribble.presentation.widget.AnimatedImageView;
//import io.armcha.ribble.presentation.widget.AnimatedTextView;
//import io.armcha.ribble.presentation.widget.ArcView;
//import spencerstudios.com.bungeelib.Bungee;
//import tech.codegarage.quotes.R;
//
///**
// * @author Md. Rashadul Alam
// *         Email: rashed.droid@gmail.com
// */
//public class ProgressLayoutTestActivity extends BaseActivity {
//
//    //toolbar
//    ArcView arcMenuView;
//    AnimatedImageView arcMenuImage;
//    AnimatedTextView toolbarTitle;
//    Toolbar toolbar;
//
//    ProgressLayout progressLayout;
//    private int currentDuration = 0;
//    private Handler mHandler = new Handler();
//    private static final int SECOND_MS = 1000;
//    private final Runnable mRunnable = new Runnable() {
//        @Override
//        public void run() {
//            currentDuration += 1;
//            mHandler.postDelayed(mRunnable, SECOND_MS);
//        }
//    };
//
//    private String TAG = ProgressLayoutTestActivity.class.getSimpleName();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_progress_layout_test);
//
//        initView();
//        initActions();
//    }
//
//    public void initView() {
//        initToolBar();
//
//        progressLayout = (ProgressLayout) findViewById(R.id.progressLayout);
//        progressLayout.start();
//        mHandler.postDelayed(mRunnable, 0);
//    }
//
//    private void initActions() {
//    }
//
//    private void initToolBar() {
//        toolbarTitle = (AnimatedTextView) findViewById(R.id.toolbarTitle);
//        arcMenuImage = (AnimatedImageView) findViewById(R.id.arcImage);
//        arcMenuView = (ArcView) findViewById(R.id.arcView);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//
//        toolbarTitle.setAnimatedText(getString(R.string.title_activity_amazing_today), 0L);
//
//        arcMenuImage.setAnimatedImage(R.drawable.arrow_left, 0L);
//        arcMenuView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Bungee.slideDown(ProgressLayoutTestActivity.this);
//    }
//}