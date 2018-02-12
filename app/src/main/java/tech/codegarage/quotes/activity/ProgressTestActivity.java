package tech.codegarage.quotes.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.patryk1007.fillme.FillMe;

import io.armcha.ribble.presentation.widget.AnimatedImageView;
import io.armcha.ribble.presentation.widget.AnimatedTextView;
import io.armcha.ribble.presentation.widget.ArcView;
import spencerstudios.com.bungeelib.Bungee;
import tech.codegarage.quotes.R;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class ProgressTestActivity extends BaseActivity {

    //toolbar
    ArcView arcMenuView;
    AnimatedImageView arcMenuImage;
    AnimatedTextView toolbarTitle;
    Toolbar toolbar;

    FillMe fillMeView;
    TextView tvProgress;
    int counter = 0;

    private String TAG = ProgressTestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_test);

        initView();
        initActions();
    }

    public void initView() {
        initToolBar();

        initFillMeView();
    }

    private void initFillMeView() {
        tvProgress = (TextView) findViewById(R.id.tv_progress);
        fillMeView = (FillMe) findViewById(R.id.fill_me_view);
        fillMeView.setImageDrawableId(R.drawable.ic_transparent_border_bg);
        fillMeView.setFillColour(getResources().getColor(R.color.colorFillMeDark));
//        fillMeView.setFillPercentHorizontalAndVertical(0, 0);
        fillMeView.setFillPercentVertical(1.0f);

        new LongOperation().execute();
    }

    private void initActions() {
    }

    private void initToolBar() {
        toolbarTitle = (AnimatedTextView) findViewById(R.id.toolbarTitle);
        arcMenuImage = (AnimatedImageView) findViewById(R.id.arcImage);
        arcMenuView = (ArcView) findViewById(R.id.arcView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbarTitle.setAnimatedText(getString(R.string.title_activity_amazing_today), 0L);

        arcMenuImage.setAnimatedImage(R.drawable.arrow_left, 0L);
        arcMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bungee.slideDown(ProgressTestActivity.this);
    }

    private class LongOperation extends AsyncTask<String, Float, String> {

        @Override
        protected String doInBackground(String... params) {
            for (counter = 0; counter <= 100; counter++) {
                try {
                    float progress = ((float) counter / (float) 100);
                    Log.d(TAG, "Progress:(i) = " + counter);
                    Log.d(TAG, "Progress:(i) = " + progress);
                    publishProgress(progress);
                    Thread.sleep(10);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onProgressUpdate(Float... progress) {
            Log.d(TAG, "Progress:(i) found =  " + progress[0]);
            tvProgress.setText(progress[0] + "%");
            fillMeView.setFillPercentHorizontal(progress[0]);
        }
    }
}