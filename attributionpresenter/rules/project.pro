
-keep class com.example.BuildConfig { *; }

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class android.graphics.Canvas

-keep class org.apache.harmony.awt.**               { *; }
-keep class org.apache.harmony.mics.**               { *; }

-keep class com.sun.mail.**               { *; }
-keep class javax.mail.**               { *; }
-keep class javax.activation.**

-keep class com.reversecoder.library.**               { *; }

-keep class android.support.v8.renderscript.** { *; }

-keep class com.android.support.annotation.**               { *; }

-keep class org.apache.**               { *; }
-keep class android.net.**               { *; }
-keep class com.android.internal.http.multipart.**               { *; }

-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclassmembers class * {
	native <methods>;
}

-keepclassmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
	public boolean performClick();
}

-keepclassmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclassmembers class * extends andriod.app.Activity {
public void *On*Click(android.view.View);
public void *on*Click(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}