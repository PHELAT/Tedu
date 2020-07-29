# Kotlin
-dontwarn kotlin.**
-dontnote kotlin.**

# Crashlytics
-keep class com.google.firebase.crashlytics.** { *; }
-dontwarn com.google.firebase.crashlytics.**

# Moshi
-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}
-keep @com.squareup.moshi.JsonQualifier interface *
-keepclassmembers @com.squareup.moshi.JsonClass class * extends java.lang.Enum {
    <fields>;
    **[] values();
}

# Required rules for Dagger
-dontwarn com.google.errorprone.annotations.**

# Required rules for Crashlytics
-keepattributes SourceFile,LineNumberTable

# Required rules for Sardine
-keep class org.xmlpull.v1.* { *; }
-dontwarn org.xmlpull.v1.**
-dontwarn javax.xml.namespace.**

# Required rules for Moshi
-dontwarn javax.annotation.**