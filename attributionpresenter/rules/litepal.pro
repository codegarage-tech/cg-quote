-keep class org.litepal.** { *;}
-keep class * extends org.litepal.crud.DataSupport { *;}

# Ensures entities remain un-obfuscated so table and columns are named correctly
-keep class tech.codegarage.quotes.model.** { *; }