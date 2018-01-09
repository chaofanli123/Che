#include <jni.h>
#include <string>

/**
 * 获取APP_KEY
 */
extern "C"
jstring
Java_com_victor_che_app_MyApplication_getAppKey(
        JNIEnv *env,
        jobject /* this */) {
    std::string str = "fee6db1d46f038bc7bdbf3f7d32857f9";
    return env->NewStringUTF(str.c_str());
}

/**
 * 获取APP_SECRET
 */
extern "C"
jstring
Java_com_victor_che_app_MyApplication_getAppSecret(
        JNIEnv *env,
        jobject /* this */) {
    std::string str = "0ffd650fd58944e5cc08188b64e60b4f";
    return env->NewStringUTF(str.c_str());
}