package com.reversecoder.quote.util;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.reversecoder.quote.R;
import com.reversecoder.quote.model.Quote;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rashed on 03-Oct-17.
 */

public class AppUtils {

    public static int toPx(Context context, int value) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (value * density);
    }

    public static void flashingView(final View viewGroup, long time) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                viewGroup.setAlpha((Float) animation.getAnimatedValue());
            }
        });
        animator.setDuration(time);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(-1);
        animator.start();
    }

    public static int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static int getRandomPastelColor() {

        final Random mRandom = new Random(System.currentTimeMillis());

        // This is the base color which will be mixed with the generated one
        final int baseColor = Color.LTGRAY;

        final int baseRed = Color.red(baseColor);
        final int baseGreen = Color.green(baseColor);
        final int baseBlue = Color.blue(baseColor);

        final int red = (baseRed + mRandom.nextInt(256)) / 2;
        final int green = (baseGreen + mRandom.nextInt(256)) / 2;
        final int blue = (baseBlue + mRandom.nextInt(256)) / 2;

        return Color.rgb(red, green, blue);
    }

    public static int getDrawableResourceId(int resourceId) {
        return resourceId;
    }

    public static String getShareQuoted(Context context, Quote quote) {
        String shareQuote = "\"" + quote.getQuoteDescription() +
                "\"\n----- " + quote.getAuthor().getAuthorName() +
                "\n\n     <<*=*=*=*=*>>     " +
                "\n" + context.getString(R.string.txt_quote_is_shared_by_the_app) +
                "\nhttps://play.google.com/store/apps/details?id=" + context.getApplicationContext().getPackageName() + "&hl=en";

        return shareQuote;
    }

    public static String modifySpecialCharacter(String text) {

        String tempText = text;

        Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
        Matcher match = pt.matcher(text);

        int counter = 0;

        while (match.find()) {
            String s = match.group();

            tempText = tempText.substring(0, match.start() + counter) + "\\\\" + tempText.substring(match.start() + counter, tempText.length());
            counter = counter + 2;
        }

        return tempText;
    }
}