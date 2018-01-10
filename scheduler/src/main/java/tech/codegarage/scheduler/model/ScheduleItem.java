package tech.codegarage.scheduler.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author Md. Rashadul Alam
 */
public class ScheduleItem implements Parcelable {

    private int mId = -1;
    private String mTitle = "";
    private String mContent = "";
    private long mTimeInMillis = -1;
    private int mFrequency = -1;

    private static final SimpleDateFormat TIME_FORMAT =
            new SimpleDateFormat("HH:mm, MMM d ''yy", Locale.US);

    public ScheduleItem(int mId, String mTitle, String mContent, long mTimeInMillis, int mFrequency) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mTimeInMillis = mTimeInMillis;
        this.mFrequency = mFrequency;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public long getTimeInMillis() {
        return mTimeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        mTimeInMillis = timeInMillis;
    }

    public String getFormattedTime() {
        return TIME_FORMAT.format(getTimeInMillis());
    }

    public int getFrequency() {
        return mFrequency;
    }

    public void setFrequency(int frequency) {
        mFrequency = frequency;
    }

    @Override
    public String toString() {
        return "{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mContent='" + mContent + '\'' +
                ", mTimeInMillis=" + mTimeInMillis +
                ", mFrequency=" + mFrequency +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mContent);
        dest.writeLong(this.mTimeInMillis);
        dest.writeInt(this.mFrequency);
    }

    protected ScheduleItem(Parcel in) {
        this.mId = in.readInt();
        this.mTitle = in.readString();
        this.mContent = in.readString();
        this.mTimeInMillis = in.readLong();
        this.mFrequency = in.readInt();
    }

    public static final Creator<ScheduleItem> CREATOR = new Creator<ScheduleItem>() {
        @Override
        public ScheduleItem createFromParcel(Parcel source) {
            return new ScheduleItem(source);
        }

        @Override
        public ScheduleItem[] newArray(int size) {
            return new ScheduleItem[size];
        }
    };

    /**************************
     * Methods for convertion *
     **************************/
    public static <T> T convertFromStringToObject(String jsonString, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, clazz);
    }

    public static <T> String convertFromObjectToString(T object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
