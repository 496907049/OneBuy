package com.app.onebuy.basis;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import java.util.Locale;

import my.LogUtil;
import my.MySharedPreferences;

/**
 * Created by Yif on 2017/8/16.
 */

public class ChangeLanguageHelper {
    private static Context mContext = null;
    private static String country = null;

    public static final int CHANGE_LANGUAGE_CHINA = 1;
    public static final int CHANGE_LANGUAGE_ENGLISH = 2;
    public static final int CHANGE_LANGUAGE_DEFAULT = 0;
    private static String mLanguage = "";

    private static Resources mResources;
    private static Locale mDefaultLocale;

    public static void init(Context context) {
        mResources = context.getResources();
        country = context.getResources().getConfiguration().locale.getCountry();

        mContext = context;
        int appLanguage = new MySharedPreferences(mContext).getAppLanguage();
        changeLanguage(appLanguage);
    }

    /**
     * 获取当前字符串资源的内容
     *
     * @param id
     * @return
     */
    public static String getStringById(int id) {
        String string ;
        if (mLanguage != null && !"".equals(mLanguage)){
            string = mResources.getString(id,mLanguage);
        }else {
            string = mResources.getString(id,"");
        }

        if (string != null && string.length() > 0) {
            return string;
        }
        return "";
    }

    public static void changeLanguage(int language) {

        Configuration config = mResources.getConfiguration();     // 获得设置对象
        DisplayMetrics dm = mResources.getDisplayMetrics();
        switch (language) {
            case CHANGE_LANGUAGE_CHINA:
                config.locale = Locale.SIMPLIFIED_CHINESE;     // 中文
                config.setLayoutDirection(Locale.SIMPLIFIED_CHINESE);
                mLanguage = "zh-CN";
                country = "CN";
                new MySharedPreferences(mContext).setAppLanguage(CHANGE_LANGUAGE_CHINA);
                break;
            case CHANGE_LANGUAGE_ENGLISH:
                config.locale = Locale.ENGLISH;   // 英文
                config.setLayoutDirection(Locale.ENGLISH);
                mLanguage = "en";
                country = "US";
                new MySharedPreferences(mContext).setAppLanguage(CHANGE_LANGUAGE_ENGLISH);
                break;
            case CHANGE_LANGUAGE_DEFAULT:
                country = Locale.getDefault().getCountry();
                LogUtil.i("country----->"+country);
//                if ("CN".equals(country)){
//                    mDefaultLocale = Locale.SIMPLIFIED_CHINESE;
//                }else {
//                    mDefaultLocale =  Locale.ENGLISH;
//                }
//                config.locale = mDefaultLocale;         // 系统默认语言
                config.locale = getRealDefault();
                LogUtil.i("country----->"+config.locale.getCountry() );
                config.setLayoutDirection( config.locale );
                new MySharedPreferences(mContext).setAppLanguage(CHANGE_LANGUAGE_DEFAULT);
                break;
        }
//        mResources.updateConfiguration(config, dm);
//        EventBus.getDefault().post(new AppEvent.ChangeLanguage());

    }

    private static Locale getRealDefault(){
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else locale = Locale.getDefault();
        return  locale;
    }

//    public static boolean getDefaultLanguage() {
//        return ("CN".equals(country));
//    }

    public static String getLanguageHeader(){
       int lanint = new MySharedPreferences(BasisApp.mContext).getAppLanguage();
       switch (lanint){
           case CHANGE_LANGUAGE_CHINA:
               return "cn";
           case CHANGE_LANGUAGE_ENGLISH:

               return "en";
           case CHANGE_LANGUAGE_DEFAULT:

               return "en";
       }
       return "en";
    }
    public static String getLanguageLocalStr(){
       int lanint = new MySharedPreferences(BasisApp.mContext).getAppLanguage();
       switch (lanint){
           case CHANGE_LANGUAGE_CHINA:
               return "zh-cn";
           case CHANGE_LANGUAGE_ENGLISH:

               return "en";
           case CHANGE_LANGUAGE_DEFAULT:

               return "en";
       }
       return "en";
    }
}
