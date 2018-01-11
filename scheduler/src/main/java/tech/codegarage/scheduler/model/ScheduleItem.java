package tech.codegarage.scheduler.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Locale;

import tech.codegarage.scheduler.enumeration.REPEAT_TYPE;

/**
 * @author Md. Rashadul Alam
 */
public class ScheduleItem implements Parcelable {

    private int mId = -1;
    private String mTitle = "";
    private String mContent = "";
    private String mAlarmTime = "";
    private long mTimeInMillis = 0;
    private REPEAT_TYPE mFrequency = REPEAT_TYPE.NONE;

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm, MMM d ''yy", Locale.US);

    public ScheduleItem() {
    }

    public ScheduleItem(int mId, String mTitle, String mContent, String mAlarmTime, long mTimeInMillis, REPEAT_TYPE mFrequency) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mAlarmTime = mAlarmTime;
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

    public String getAlarmTime() {
        return mAlarmTime;
    }

    public void setAlarmTime(String mAlarmTime) {
        this.mAlarmTime = mAlarmTime;
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

    public REPEAT_TYPE getFrequency() {
        return mFrequency;
    }

    public void setFrequency(REPEAT_TYPE mFrequency) {
        this.mFrequency = mFrequency;
    }

    @Override
    public String toString() {
        return "{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mContent='" + mContent + '\'' +
                ", mAlarmTime='" + mAlarmTime + '\'' +
                ", mTimeInMillis=" + mTimeInMillis +
                ", mFrequency=" + mFrequency.name() +
                '}';
    }

    /**************************
     * Methods for parcelable *
     **************************/
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mContent);
        dest.writeString(this.mAlarmTime);
        dest.writeLong(this.mTimeInMillis);
        dest.writeString((this.mFrequency == null) ? "" : this.mFrequency.name());
    }

    // Creator
    public static final Creator CREATOR
            = new Creator() {
        public ScheduleItem createFromParcel(Parcel in) {
            return new ScheduleItem(in);
        }

        public ScheduleItem[] newArray(int size) {
            return new ScheduleItem[size];
        }
    };

    // "De-parcel object
    public ScheduleItem(Parcel in) {
        this.mId = in.readInt();
        this.mTitle = in.readString();
        this.mContent = in.readString();
        this.mAlarmTime = in.readString();
        this.mTimeInMillis = in.readLong();
        this.mFrequency = REPEAT_TYPE.valueOf(in.readString());
    }

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
