# The FlexboxLayoutManager may be set from a layout xml, in that situation the RecyclerView
# tries to instantiate the layout manager using reflection.
# This is to prevent the layout manager from being obfuscated.
-keep public class com.google.android.flexbox.FlexboxLayoutManager