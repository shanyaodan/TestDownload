package test2.dyc.com.testdownload;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class DownloadService extends Service {



    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

     return   new MyBinder().asBinder();


//        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    class MyBinder extends  Idownload.Stub{

        @Override
        public void start(DownloadItem item) throws RemoteException {



        }

        @Override
        public void pause(String url) throws RemoteException {

        }

        @Override
        public void stop(String url) throws RemoteException {

        }

        @Override
        public void resume() throws RemoteException {

        }

        @Override
        public void stopAll() throws RemoteException {

        }
    }




}
