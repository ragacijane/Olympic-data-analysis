#include "olimpicgames_MainFrame.h"
#include "olimpicGames.h"
#include <algorithm>

OlimpicGames* o;

jstring str2jstring(JNIEnv* env, const char* pat)
{
    //Define the java String class strClass
    jclass strClass = (env)->FindClass("Ljava/lang/String;");
    //Get the constructor of String(byte[],String) to convert the local byte[] array into a new String
    jmethodID ctorID = (env)->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
    //Create byte array
    jbyteArray bytes = (env)->NewByteArray(strlen(pat));
    //Convert char* to byte array
    (env)->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte*)pat);
    //Set the String, save the language type, used for the parameters when the byte array is converted to String
    jstring encoding = (env)->NewStringUTF("GB2312");
    //Convert the byte array to java String and output
    return (jstring)(env)->NewObject(strClass, ctorID, bytes, encoding);
}

std::string jstring2str(JNIEnv* env, jstring jstr)
{
    char* rtn = NULL;
    jclass clsstring = env->FindClass("java/lang/String");
    jstring strencode = env->NewStringUTF("GB2312");
    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray)env->CallObjectMethod(jstr, mid, strencode);
    jsize alen = env->GetArrayLength(barr);
    jbyte* ba = env->GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0)
    {
        rtn = (char*)malloc(alen + 1);
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0);
    std::string stemp(rtn);
    free(rtn);
    return stemp;
}

JNIEXPORT jstring JNICALL Java_olimpicgames_MainFrame_loadFiles
(JNIEnv* env, jobject, jstring inputYear){

    float sum = 0, cnt=0;
    o = new OlimpicGames(stoi(jstring2str(env, inputYear)));

    string str = o->getData("none", "none", "none", "none",0,0,"none");
	return str2jstring(env,str.c_str()); // conversion from std:string to const char*

}

JNIEXPORT jstring JNICALL Java_olimpicgames_MainFrame_getSports
(JNIEnv* env, jobject, jstring period) {
    int cnt = 0;
    string str = "";
    if(o->allSports.size() != 0)
        for (const auto& x : o->allSports) {
            str += x.second->getName();
            str.push_back('!');
            cnt++;
        }
    str += to_string(cnt);
    str.push_back('!');

    return str2jstring(env, str.c_str());
}

JNIEXPORT jstring JNICALL Java_olimpicgames_MainFrame_getYears
(JNIEnv* env, jobject,jstring period) {
    int cnt = 0;
    string str = "";
    if (o->allGames.size() != 0)
        for (const auto& x : o->allGames) {
            str += to_string(x.second->getYear());
            str.push_back('!');
            cnt++;
        }
    str += to_string(cnt);
    str.push_back('!');

    return str2jstring(env, str.c_str());
}
//sportFilter, yearFilter, typeFilter, medalFilter

JNIEXPORT jstring JNICALL Java_olimpicgames_MainFrame_updateFiles
(JNIEnv* env, jobject, jstring sf, jstring yf, jstring tf, jstring mf,jstring period) {

    string sportFilter = jstring2str(env, sf);
    string yearFilter = jstring2str(env, yf);
    string typeFilter = jstring2str(env, tf);
    string medalFilter = jstring2str(env, mf);
    string per = jstring2str(env, period);

    string str = o->getData(sportFilter, yearFilter, typeFilter, medalFilter, 0, 0, per);
    return str2jstring(env, str.c_str());
}

JNIEXPORT jstring JNICALL Java_olimpicgames_MainFrame_xyData
(JNIEnv* env, jobject, jstring startY, jstring endY, jstring ty) {
    string startYear = jstring2str(env, startY);
    string endYear = jstring2str(env, endY);
    string summ = "Summer";
    string wint = "Winter";
    int type = stoi(jstring2str(env, ty));
    string str = "", str1 = "", strx="";
    int cnt = 0;
    if (o->allGames.size() != 0){
        for (const auto& x : o->allGames) {
            if (x.second->getYear() >= stoi(startYear) && x.second->getYear() <= stoi(endYear)) {
                strx = o->getData("none", to_string(x.second->getYear()), "none", "none", 1, type,summ);
                if (strx == "")
                    str1.push_back('0');
                else
                    str1 += strx;
                cnt++;
            }
        }
        str += to_string(cnt);
        str.push_back('!');
        str += str1;
        str1 = "";
        cnt = 0;
        for (const auto& x : o->allGames) {
            if (x.second->getYear() >= stoi(startYear) && x.second->getYear() <= stoi(endYear) ) {
                strx = o->getData("none", to_string(x.second->getYear()), "none", "none", 1, type, wint);
                if (strx == "")
                    str1.push_back('0');
                else
                    str1 += strx;
                cnt++;
            }
        }
        str += to_string(cnt);
        str.push_back('!');
        str += str1;
    }
    return str2jstring(env, str.c_str());
}


