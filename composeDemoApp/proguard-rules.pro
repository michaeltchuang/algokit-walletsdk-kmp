# Add project specific ProGuard rules here.

# Keep all resources including launcher icons
-keepclassmembers class **.R$* {
    public static <fields>;
}

# Keep drawable and mipmap resources
-keep class **.R$drawable { *; }
-keep class **.R$mipmap { *; }

# Preserve all native method names and the names of their classes.
-keepclasseswithmembernames class * {
    native <methods>;
}

# Ignore warnings for missing classes that are not used on Android
-dontwarn com.goterl.resourceloader.**
-dontwarn java.awt.**
-dontwarn java.beans.**
-dontwarn java.lang.invoke.MethodHandleProxies
-dontwarn java.lang.management.**
-dontwarn java.lang.reflect.AnnotatedType
-dontwarn javax.swing.**
-dontwarn com.sun.jna.**
-dontwarn org.bouncycastle.jsse.**
-dontwarn org.conscrypt.**
-dontwarn org.openjsse.**
-dontwarn net.pwall.json.pointer.JSONPointer
-dontwarn net.pwall.json.schema.JSONSchema
-dontwarn net.pwall.json.schema.output.BasicOutput
-dontwarn org.joda.time.**
-dontwarn org.ktorm.entity.**
-dontwarn sun.nio.ch.DirectBuffer
