# Nine Old Android
# This is due to ObjectAnimator using reflection to access get/sets
-keep class tech.codegarage.quotes.ClassThatUsesObjectAnimator { *; }

# Litepal
# Ensures entities remain un-obfuscated so table and columns are named correctly
# GSON
# Application classes that will be serialized/deserialized over Gson
-keep class tech.codegarage.quotes.model.** { *; }
-keepnames class tech.codegarage.quotes.model.** { *; }

# Keep the BuildConfig
-keep class tech.codegarage.quotes.BuildConfig { *; }