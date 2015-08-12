package test2.dyc.com.testdownload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2015/8/3.
 */
public class DownloadReciver  extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            System.out.println( intent.getStringArrayExtra("successUrl"));
            System.out.println( intent.getStringArrayExtra("downSign"));
        } catch (Exception e) {


        }

    }


}
