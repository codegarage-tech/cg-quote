# Nine Old Android
# This is due to ObjectAnimator using reflection to access get/sets
-keep class tech.codegarage.quotes.en.ClassThatUsesObjectAnimator { *; }

# Litepal
# Ensures entities remain un-obfuscated so table and columns are named correctly
-keep public class tech.codegarage.quotes.en.model.** { *; }

# GSON
# Application classes that will be serialized/deserialized over Gson
-keep public class tech.codegarage.quotes.en.model.** { *; }