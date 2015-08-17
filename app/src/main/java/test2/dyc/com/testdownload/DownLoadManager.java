package test2.dyc.com.testdownload;

import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/7/3.
 */
public class DownLoadManager {
    public static final String TAG = "DownLoadManager";
    private Idownload mDownLoader;
    public static final String ACTION_DOWNLOAD = "com.dyc.testdownload";

    public static final int ERROR_URL = 0xa;
    public static final int ERROR_NETWORK = 0xb;
    public static final int ERROR_FILE_NOFIND = 0xc;
    public static final int DOWNLOAD_BEGIN = 0xd;

    public DownLoadManager() {
        Intent intent = new Intent(App.getContext(), DownloadService.class);
        App.getContext().startService(intent);
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

    public void start(final String url,final String nameStr) {
        try {
            mDownLoader.start(url, nameStr);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }


    public void stop(String url) {
        try {
            mDownLoader.stop(url);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void resume(String url) {

    }

    public void stopAll() {

    }

    public void removeData(String url) {
        stop(url);
        DownloadItem downloadItem =  getDownloadItem(url,"");
        FileUitls.getFile(url,App.getContext()).delete();
        DownDB.getInstance(App.getContext()).delItem(url);
    }


    public void sendBroadCast(){
        Intent intent = new Intent();


    }

    public DownloadItem getDownloadItem(String url,String name){
        DownloadItem  downloadItem = DownDB.getInstance(App.getContext()).selectItem(url) ;
        if(null == downloadItem) {
         DownDB.getInstance(App.getContext()).InsetData(name,url,null,-1);
        }
        return downloadItem;
    }

    public static void sendDownloadBroadCast(String url,int sign) {
        L.v(TAG,"sendDownloadBroadCast",url,sign);
            Intent intent = new Intent(ACTION_DOWNLOAD);
            Bundle bundle = new Bundle();
            bundle.putString("successUrl",url);
            bundle.putInt("downSign", sign);
            App.getContext().sendBroadcast(intent);
    }

    public static void sendDownloadUpdateBroadCast(DownloadItem item) {
        L.v(TAG,"sendDownloadUpdateBroadCast",item);
        Intent intent = new Intent(ACTION_DOWNLOAD);
        Bundle bundle = new Bundle();
        bundle.putParcelable("DownloadItem", item);
        App.getContext().sendBroadcast(intent);
    }






}
