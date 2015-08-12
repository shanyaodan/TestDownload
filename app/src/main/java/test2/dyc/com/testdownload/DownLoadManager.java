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
    private Idownload mDownLoader;
    public static final String ACTION_DOWNLOAD = "com.dyc.testdownload";
    /**
     * broadcast
     * 下载标识符 start 0
     */
    public static final int    DOWNLOAD_START = 0;

    /**
     * broadcast
     * 下载标识符 UPDATE_STATE 1
     */
    public static final int    DOWNLOAD_UPDATE_STATE = 1;

    /**
     *
     * broadcast
     *载标识符 PAUSE 2
     *
     */
    public static final int    DOWNLOAD_PAUSE = 2;

    /**
     * broadcast
     *载标识符 FINISH 3
     *
     */
    public static final int    DOWNLOAD_FINISH = 3;



    public DownLoadManager() {

    }

    public void start(final String url,final String nameStr) {
        Intent intent = new Intent(App.getContext(), DownloadService.class);
        App.getContext().bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mDownLoader = Idownload.Stub.asInterface(service);

                try {
                    mDownLoader.start(url,nameStr);
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
            Intent intent = new Intent(ACTION_DOWNLOAD);
            Bundle bundle = new Bundle();
            bundle.putString("successUrl",url);
            bundle.putInt("downSign", sign);
            App.getContext().sendBroadcast(intent);
    }

    public static void sendDownloadUpdateBroadCast(DownloadItem item) {
        Intent intent = new Intent(ACTION_DOWNLOAD);
        Bundle bundle = new Bundle();
        bundle.putParcelable("DownloadItem", item);
        bundle.putInt("downSign", DOWNLOAD_UPDATE_STATE);
        App.getContext().sendBroadcast(intent);
    }






}
