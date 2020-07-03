# Kotlin
-dontwarn kotlin.**
-dontnote kotlin.**

# Crashlytics
-keep class com.google.firebase.crashlytics.** { *; }
-dontwarn com.google.firebase.crashlytics.**

# Required rules for Dagger
-dontwarn com.google.errorprone.annotations.**

# Required rules for Crashlytics
-keepattributes SourceFile,LineNumberTable

# Required rules for Sardine
-keep class org.xmlpull.v1.* { *; }
-dontwarn org.xmlpull.v1.**
-dontwarn javax.xml.namespace.**