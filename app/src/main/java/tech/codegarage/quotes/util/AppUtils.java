package tech.codegarage.quotes.util;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.reversecoder.library.util.AllSettingsManager;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tech.codegarage.quotes.R;
import tech.codegarage.quotes.model.Quote;
import tech.codegarage.quotes.model.database.LitePalDataBuilder;
import tech.codegarage.quotes.model.database.QuoteOfTheDay;

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

    public static String getSharedQuote(Context context, Quote quote) {
        String shareQuote = "\"" + quote.getQuoteDescription() +
                "\"\n--- " + quote.getAuthor().getAuthorName() +
                "\n\n     <<*=*=*=*=*>>     " +
                "\n" + context.getString(R.string.txt_quote_is_shared_by_the_app) +
                "\nhttps://play.google.com/store/apps/details?id=" + context.getApplicationContext().getPackageName() + "&hl=en";

        return shareQuote;
    }

    public static String getSharedQuote(Context context, LitePalDataBuilder litePalDataBuilder, LitePalDataBuilder.LitePalQuoteBuilder quote) {
        String shareQuote = "\"" + quote.getLitePalQuote().getQuoteDescription() +
                "\"\n--- " + litePalDataBuilder.getLitePalAuthor().getAuthorName() +
                "\n\n     <<*=*=*=*=*>>     " +
                "\n" + context.getString(R.string.txt_quote_is_shared_by_the_app) +
                "\nhttps://play.google.com/store/apps/details?id=" + context.getApplicationContext().getPackageName() + "&hl=en";

        return shareQuote;
    }

    public static String getSharedQuote(Context context, QuoteOfTheDay quoteOfTheDay) {
        String shareQuote = "\"" + quoteOfTheDay.getLitePalQuoteBuilder().getLitePalQuote().getQuoteDescription() +
                "\"\n--- " + quoteOfTheDay.getLitePalAuthor().getAuthorName() +
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

    /*******
     * MD5 *
     *******/
    private static char[] Digit = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    public static String getMD5String(String src) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(src.getBytes());
            return toHexString(messageDigest.digest());
        } catch (Exception e) {
        }
        return null;
    }

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(Digit[(b[i] & 0xf0) >>> 4]);
            sb.append(Digit[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    public static boolean isDateEqual(String date1, String date2, String dateFormat) {
        if (!AllSettingsManager.isNullOrEmpty(date1) && !AllSettingsManager.isNullOrEmpty(date2) && !AllSettingsManager.isNullOrEmpty(dateFormat)) {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            if ((date1.trim().length() != sdf.toPattern().length()) && (date2.trim().length() != sdf.toPattern().length())) {
                return false;
            }
            sdf.setLenient(false);

            try {
                if (sdf.parse(date1.trim()).equals(sdf.parse(date2.trim()))) {
                    return true;
                }
            } catch (ParseException pe) {
                return false;
            }
        }
        return false;
    }
}