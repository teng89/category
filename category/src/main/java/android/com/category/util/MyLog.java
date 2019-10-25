package android.com.category.util;

import android.util.Log;

/*
 * Created by thh on 2019/10/25.
 */
public class MyLog {

    private static final String TAG = "CateGory";

    public static void i(String tag,String msg) {
        Log.i(TAG,tag+">>"+msg);
    }

    public static void d(String tag,String msg) {
        Log.d(TAG,tag+">>"+msg);
    }

    public static void w(String tag,String msg) {
        Log.w(TAG,tag+">>"+msg);
    }

    public static void e(String tag,String msg) {
        Log.e(TAG,tag+">>"+msg);
    }
}
