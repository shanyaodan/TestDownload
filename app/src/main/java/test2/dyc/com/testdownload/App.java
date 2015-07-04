package test2.dyc.com.testdownload;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2015/7/3.
 */
public class App extends Application {


    private  static  Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
            return context;

    }

}
