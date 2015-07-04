package test2.dyc.com.testdownload;

import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/7/3.
 */
public class DownLoadManager {

    private Idownload mDownLoader;


    public DownLoadManager() {

    }

    public void start(String url) {
        Intent intent = new Intent(App.getContext(), DownloadService.class);
        App.getContext().bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mDownLoader = Idownload.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, 0);
    }

    public void pause(String url) {



    }

    public void stop(String url) {

    }

    public void resume(String url) {

    }

    public void stopAll() {

    }


    public void sendBroadCast(){
        Intent intent = new Intent();


    }


}
