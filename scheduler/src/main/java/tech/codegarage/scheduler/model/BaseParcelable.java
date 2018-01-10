package tech.codegarage.scheduler.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Md. Rashadul Alam
 */
public class BaseParcelable {

    public static byte[] toByteArray(Parcelable parcelable) {
        Parcel parcel = Parcel.obtain();
        parcelable.writeToParcel(parcel, 0);
        byte[] result = parcel.marshall();
        parcel.recycle();
        return (result);
    }

    public static <T> T toParcelable(byte[] bytes, Parcelable.Creator<T> creator) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);
        T result = creator.createFromParcel(parcel);
        parcel.recycle();
        return (result);
    }
}
