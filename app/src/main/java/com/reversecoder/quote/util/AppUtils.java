package com.reversecoder.quote.util;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.View;

import java.util.Random;

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
}