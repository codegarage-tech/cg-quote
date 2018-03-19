# Annotation, Exceptions, Signature- remove from obfuscation targets etc
-keepattributes *Annotation*,Exceptions,Signature,SourceFile,LineNumberTable,InnerClass

#Parcelable
-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}

#Enum
-keepclassmembers class * extends java.lang.Enum {
    <fields>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Activity, Application, Service, BroadcastReceiver
# Exclude classes that can not be obfuscated on the system
-keep public class * extends android.app.*
-keep public class * extends android.content.*
-keep public class * extends android.os.Binder
-keep public class * extends android.widget.*
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v7.app.AppCompatActivity

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.content.Context {
   public void *(android.view.View);
   public void *(android.view.MenuItem);
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-dontwarn android.security.**
-dontwarn android.databinding.**