package com.app.onebuy.util;

import android.support.annotation.Nullable;

/**
 * Created by otis on 2018/6/8.
 */

public class TextLengthUtil {

    private static final int MIXLENGTH = 6;

    public static boolean mixLength(CharSequence str) {
        if(str.length() >= MIXLENGTH){
            return true;
        }else {
            return false;
        }

    }

}
