package test2.dyc.com.testdownload;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DownloadService extends Service {

    private ArrayList<DownloadItem> pauseItems = new ArrayList<DownloadItem>();

    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
     return   new MyBinder().asBinder();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

  private  class MyBinder extends  Idownload.Stub{
        @Override
        public void start(final DownloadItem item) throws RemoteException {
                pauseItems.remove(item);
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        downLoadDatas(item);
                    }
                }.start();

        }
      @Override
      public void pause(DownloadItem item) throws RemoteException {
          pauseItems.add(item);
      }

      @Override
      public void stop(DownloadItem item) throws RemoteException {
          pauseItems.add(item);
      }

      @Override
      public void resume() throws RemoteException {

      }

      @Override
      public void stopAll() throws RemoteException {

      }
        }

     private void downLoadDatas(DownloadItem item){

         try {
           URL url = new URL(item.url);



           URLConnection urlConnection =  url.openConnection();

             urlConnection.setConnectTimeout(8000);
             urlConnection.setReadTimeout(8000);

             long startRange = 0;
             int fileSize = urlConnection.getContentLength();
               File file = FileUitls.getFile(item.url, this) ;
             if(file.exists()){
                 startRange = file.length();
             }
             else {
                 startRange = 0;
             }
            if(startRange == fileSize) {
                return ;
            }
           urlConnection.setRequestProperty("Range", "bytes=" + startRange + "-" + fileSize);
           InputStream inputStream =   urlConnection.getInputStream();
             byte[]b = new byte[1024];
             int lenth = 0;
             FileOutputStream fileout = new FileOutputStream(file,true);
             Log.i("download","download begin");
             while ((lenth=inputStream.read(b))!=-1) {
                 item.currentSize +=lenth;
                 fileout.write(b,0,lenth);
                 fileout.flush();
                 if(pauseItems.contains(item)) {
                     fileout.close();
                     break;
                 }
             }
             fileout.close();
             if(file.exists()){
                if(fileSize == file.length()) {

                }
             }
             Log.i("download", "download success");
         } catch (Exception e) {
             e.printStackTrace();

             return ;
         }
     }


    }


