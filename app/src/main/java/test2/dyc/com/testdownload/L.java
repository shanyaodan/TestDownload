package test2.dyc.com.testdownload;

import android.util.Log;


/**
 * Created by win7 on 2015/8/17.
 */
public class L {
    public static final boolean RELEASE = false;

    //    public static final boolean RELEASE = true;
    public static void i(String tag, Object... info) {
     printLog(0,tag,info);
    }
    public static void v(String tag, Object... info) {
        printLog(1,tag,info);
    }
    public static void d(String tag, Object... info) {
        printLog(2,tag,info);
    }
    public static void w(String tag, Object... info) {
        printLog(3,tag,info);
    }
    public static void e(String tag, Object... info) {
        printLog(4,tag,info);
    }

    public static void i(Object tag, Object... info) {
        printLog(0,tag.getClass().getSimpleName(),info);
    }
    public static void v(Object tag, Object... info) {
        printLog(1,tag.getClass().getSimpleName(),info);
    }
    public static void d(Object tag, Object... info) {
        printLog(2,tag.getClass().getSimpleName(),info);
    }
    public static void w(Object tag, Object... info) {
        printLog(3,tag.getClass().getSimpleName(),info);
    }
    public static void e(Object tag, Object... info) {
        printLog(4,tag.getClass().getSimpleName(),info);
    }
    public static void printLog(int type,String tag,Object...info) {

        if (RELEASE == true) {
            return;
        }
        String infoStr = "";
        if (null != info) {

            for (Object obj : info) {
                infoStr += obj.toString()+"  ";
            }

            switch (type){
                case 0: {
                    Log.i(tag, infoStr);
                    break;
                }
                case 1: {
                    Log.v(tag, infoStr);
                    break;
                }
                case 2:{
                    Log.d(tag, infoStr);
                    break;
                }
                case 3:{
                    Log.w(tag, infoStr);
                    break;
                }
                case 4:{
                    Log.e(tag, infoStr);
                    break;
                }
            }


        }

    }



}
