package test2.dyc.com.testdownload;

import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/7/3.
 */
public class DownLoadManager {
    private Idownload mDownLoader;
    public DownLoadManager() {

    }

    public void start(final String url,final String nameStr) {
        Intent intent = new Intent(App.getContext(), DownloadService.class);
        App.getContext().bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mDownLoader = Idownload.Stub.asInterface(service);

                try {
                    mDownLoader.start(getDownloadItem(url,nameStr));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, 0);
        App.getContext().startService(intent);
    }

    public void pause(String url) {
        try {
            mDownLoader.pause(getDownloadItem(url,""));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stop(String url) {
        try {
            mDownLoader.stop(getDownloadItem(url,""));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void resume(String url) {

    }

    public void stopAll() {

    }

    public void removeData(String url) {
        getDownloadItem(url,"");


    }


    public void sendBroadCast(){
        Intent intent = new Intent();


    }

    public DownloadItem getDownloadItem(String url,String name){
        DownloadItem  downloadItem = null ;
        if(null == downloadItem) {
         DownDB.getInstance(App.getContext()).InsetData(name,url,null,-1);
        }
        downloadItem   =    DownDB.getInstance(App.getContext()).selectItem(url);
        return downloadItem;

    }




}
