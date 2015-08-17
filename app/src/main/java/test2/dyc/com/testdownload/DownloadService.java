package test2.dyc.com.testdownload;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class DownloadService extends Service {

    private ArrayList<String> pauseItems = new ArrayList<String>();
    private ArrayList<String> DownloadingItems = new ArrayList<String>();
    private HashMap<String, Notification> notificationMap = new HashMap<String, Notification>();
    private RemoteViews mRemoveViews;
    private NotificationManager mNotificationManager;


    /**
     * handler params
     */
    public static final int DOWNLOAD_UPDATE = 1; // 更新下载进度
    public static final int DOWNLOAD_COMPLETE = 2; // 下载完成
    public static final int DOWNLOAD_FAIL = 3; // 下载失败
    public static final int REQEUSTFAIL = 4;
    public static final int SDCARD_NOSPACE = 5;
    public static final int NETWORK_ERROR = 6;

    public static final int DONWLOAD_URL_ERROR = 7;

    public DownloadService() {

    }


    private Handler mHandler = new Handler() {
        @Override
        public synchronized void handleMessage(Message msg) {
            super.handleMessage(msg);
            DownloadItem downitem = (DownloadItem) msg.obj;
            if (msg != null) {

                switch (msg.what) {

                    case DOWNLOAD_UPDATE:

                        if (mNotificationManager == null) {
                            // L.i("init mNotificationManager", "1111111111");
                            mNotificationManager = (NotificationManager) App.getContext()
                                    .getSystemService(Context.NOTIFICATION_SERVICE);
                        }

                        Notification notifcation = getNotifcation(downitem.url);
                        notifcation.tickerText = downitem.name + "";
                        mRemoveViews = new RemoteViews(App.getContext().getPackageName(),
                                R.layout.update);
                        mRemoveViews.setTextViewText(R.id.down_title,
                                downitem.name);
                        mRemoveViews.setTextViewText(R.id.tvProcess, "已下载"
                                + downitem.downloadPercent + "%");
                        mRemoveViews.setProgressBar(R.id.pbDownload, 100,
                                downitem.downloadPercent, false);

//					if (null != downitem.type
//							&& DOWNLOAD_MM.equals(downitem.type)) {
                        mRemoveViews.setImageViewResource(R.id.ivLogo,
                                R.mipmap.ic_launcher);
//					}
                        // else {
                        // mRemoveViews.setImageViewResource(R.id.ivLogo,
                        // R.drawable.about_tv_logo);
                        // }
                        notifcation.contentView = mRemoveViews;
                        mNotificationManager.notify(downitem.url.hashCode(),
                                notifcation);

                        // if (mNotificationManager != null) {
                        // mNotification.tickerText = msg.obj.toString();
                        // mRemoveViews.setTextViewText(R.id.tvProcess, "已下载"+
                        // mDownloadPrecent + "%");
                        // mRemoveViews.setProgressBar(R.id.pbDownload,
                        // 100,mDownloadPrecent, false);
                        // mNotification.contentView = mRemoveViews;
                        // mNotificationManager.notify(mNotificationId,mNotification);
                        // }
                        break;
                    case DOWNLOAD_COMPLETE:

                        if (mNotificationManager != null
                                && notificationMap.get(downitem.url) != null) {
                            mNotificationManager.cancel(downitem.url.hashCode());
                            notificationMap.remove(downitem.url);
                        }
                        File newFile = FileUitls.getCompleteSaveFile(downitem.name);
//                        install(newFile, App.getContext());
                        break;
                    case DOWNLOAD_FAIL:
                        if (mNotificationManager != null
                                && notificationMap.get(downitem.url) != null) {
                            mNotificationManager.cancel(downitem.url.hashCode());
                            notificationMap.remove(downitem.url);
                        }
//                        if (customDialog != null)
//                            customDialog.dismiss();
//                        CommonUtils.showToast(R.string.downloadfail);
                        break;
                    case NETWORK_ERROR:
                        if (mNotificationManager != null
                                && notificationMap.get(downitem.url) != null) {
                            mNotificationManager.cancel(downitem.url.hashCode());
                            notificationMap.remove(downitem.url);
                        }
//                        if (customDialog != null)
//                            customDialog.dismiss();
//                        CommonUtils.showToast(R.string.network_error);
                        break;
                    // case SHOW_WAITING:
                    // customDialog.show();
                    // break;
                    // case DISMISS_WAITINGL:
                    // customDialog.dismiss();
                    // break;
//                    case REQEUSTFAIL:
//                        CommonUtils.showToast(R.string.downloadfail);

                    case SDCARD_NOSPACE:

                        if (mNotificationManager != null
                                && notificationMap.get(downitem.url) != null) {
                            mNotificationManager.cancel(downitem.url.hashCode());
                            notificationMap.remove(downitem.url);
                        }
//                        if (customDialog != null)
//                            customDialog.dismiss();
//                        // CommonUtil.showToast(R.string.downloadfail, 0);
//                        CommonUtils.showToast(R.string.nofreespace);
                        break;
                    default:
                        break;
                }
            }
        }
    };

    private Notification getNotifcation(String url) {
        Notification notification = null;
        if (notificationMap.get(url) == null) {
            notification = new Notification();
            notification.icon = android.R.drawable.stat_sys_download;
            // mNotification.tickerText = mContext.getString(R.string.app_name)
            // + "更新";
            notification.when = System.currentTimeMillis();
            notification.defaults = Notification.DEFAULT_LIGHTS;
            Intent intent = new Intent(App.getContext().getApplicationContext(),
                    App.getContext().getClass());
            PendingIntent contentIntent = PendingIntent.getActivity(App.getContext(),
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.contentIntent = contentIntent;
            notificationMap.put(url, notification);
        } else {
            notification = notificationMap.get(url);
        }

        return notification;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder().asBinder();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private class MyBinder extends Idownload.Stub {
        @Override
        public void start(final String url, final String name) throws RemoteException {

            new Thread() {
                @Override
                public void run() {
                    super.run();
                    downLoadDatas(url, name);
                }
            }.start();

        }

        @Override
        public void stop(String url) throws RemoteException {
            if(pauseItems.contains(url)) {
                pauseItems.add(url);
            }
            if (DownloadingItems.contains(url)) {
                DownloadingItems.remove(url);
            }
        }


        @Override
        public void stopAll() throws RemoteException {

        }
    }

    private void downLoadDatas(String urlStr, String name) {
        if (pauseItems.contains(urlStr)) {
            pauseItems.remove(urlStr);
            L.i(this, "pause delet ", urlStr, name);
        }
        L.i(this, "before download",DownloadingItems.toString());
        if (DownloadingItems.contains(urlStr)) {
            L.i(this, "is download",urlStr,name);
            return;
        }
        L.i(this, "start download ", urlStr, name);
        DownloadingItems.add(urlStr);
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            L.e(this, "url", "error",urlStr,name);
            DownLoadManager.sendDownloadBroadCast(urlStr, DownLoadManager.ERROR_URL);
            return;
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            L.e(this, "url", "openConnection error",urlStr,name);
            e.printStackTrace();
            DownLoadManager.sendDownloadBroadCast(urlStr, DownLoadManager.ERROR_NETWORK);

            return;
        }
        urlConnection.setConnectTimeout(8000);
        urlConnection.setReadTimeout(8000);
        long startRange = 0;
        long currentSize = 0;
        File file = FileUitls.getFile(urlStr, this);
        if (file.exists()) {
            startRange = file.length();
        } else {
            startRange = 0;
        }
        urlConnection.setRequestProperty("Range", "bytes=" + startRange + "-");
        float fileSize = 0;
        DownloadItem downLoadDatas = DownDB.getInstance(App.getContext()).selectItem(urlStr);
        if (startRange == 0) {
            fileSize = urlConnection.getContentLength();
            if (null == downLoadDatas) {
                if (startRange == 0) {
                    DownDB.getInstance(App.getContext()).InsetData(name, urlStr, fileSize + "", DownloadItem.DOWNLOAD_STATE_NOTINT);
                }
            }
        } else {
            downLoadDatas = DownDB.getInstance(App.getContext()).selectItem(urlStr);
            fileSize = Float.parseFloat(TextUtils.isEmpty(downLoadDatas.totalSize) ? "0" : downLoadDatas.totalSize);
        }
        InputStream inputStream = null;
        try {
            inputStream = urlConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            L.e(this, "network error",urlStr,name);
            DownLoadManager.sendDownloadBroadCast(urlStr, DownLoadManager.ERROR_NETWORK);
            return;
        }
        byte[] b = new byte[1024];
        int lenth = 0;
        int shouldNotifySize = 0;
        FileOutputStream fileout = null;
        try {
            fileout = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            L.e("download", " ERROR_FILE_NOFIND",urlStr,name);
            DownLoadManager.sendDownloadBroadCast(urlStr, DownLoadManager.ERROR_FILE_NOFIND);
        }
        L.i("download", "download begin");
        DownLoadManager.sendDownloadBroadCast(urlStr, DownLoadManager.DOWNLOAD_BEGIN);

        try {
            while (null != inputStream && (lenth = inputStream.read(b)) != -1) {
                if (file.exists() && file.length() != 0 && file.length() < currentSize) {
                    L.i("TAG", file.length() + ":" + currentSize + "",urlStr,name);
                    L.i(this, file.length() + ":" + currentSize + "","redownload");
                    downLoadDatas.state = DownloadItem.DOWNLOAD_STATE_ERROR;
                    updateDownloadState(downLoadDatas, DOWNLOAD_FAIL);
                    downLoadDatas(urlStr, name);
                    return;
                }
                currentSize += lenth;
                fileout.write(b, 0, lenth);
                fileout.flush();
                shouldNotifySize += lenth;
                if (pauseItems.contains(urlStr)) {
                    fileout.close();
                    downLoadDatas.state = DownloadItem.DOWNLOAD_STATE_PAUSE;
                    updateDownloadState(downLoadDatas, -1);
                    return;
                }
                if (shouldNotifySize * 100 / fileSize >= 5) {
                    shouldNotifySize = 0;
                    downLoadDatas.currentSize = file.length() + "";
                    downLoadDatas.state = DownloadItem.DOWNLOAD_STATE_DOING;
                    downLoadDatas.downloadPercent = (int) (((double) file.length() / fileSize) * 100);
                    updateDownloadState(downLoadDatas, DOWNLOAD_UPDATE);
                }
                file = FileUitls.getFile(urlStr, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
            downLoadDatas.state = DownloadItem.DOWNLOAD_STATE_ERROR;
            updateDownloadState(downLoadDatas, DOWNLOAD_FAIL);
            L.e("TAG", urlStr, name, "redownload");

            downLoadDatas(urlStr, name);
            return;
        }
        try {
            fileout.close();
        } catch (IOException e) {
            e.printStackTrace();
            L.e(this,"close error",urlStr,name);
        }
        if (file.exists()) {
            if (file.length() != 0 && file.length() < currentSize) {
                downLoadDatas.state = DownloadItem.DOWNLOAD_STATE_ERROR;
                updateDownloadState(downLoadDatas, DOWNLOAD_FAIL);
                L.e("TAG", urlStr, name, "redownload");

                downLoadDatas(urlStr, name);
                return;
            }
            /**
             * finsh donwload
             */
            if (fileSize == file.length()) {
                L.i(this, "fileSize == file.length()DOWNLOAD_STATE_FINISH",urlStr,name);
                int downloadPercent = (int) (((double) file.length() / fileSize) * 100);
                downLoadDatas.state = DownloadItem.DOWNLOAD_STATE_FINISH;
                downLoadDatas.downloadPercent = downloadPercent;
                updateDownloadState(downLoadDatas, DOWNLOAD_COMPLETE);
            } else {
                downLoadDatas.state = DownloadItem.DOWNLOAD_STATE_ERROR;
                updateDownloadState(downLoadDatas, DOWNLOAD_FAIL);
                L.e("TAG", urlStr, name, "redownload");
                downLoadDatas(downLoadDatas.url, downLoadDatas.name);
            }

        }
        L.i("download", "download success",urlStr,name);

    }




    /**
     * @param item
     * @param messageState -1 不发送信息
     */
    private void updateDownloadState(DownloadItem item, int messageState) {
        if (null == item) {
            return;
        }
        String url = item.url;
        switch (item.state) {
            case DownloadItem.DOWNLOAD_STATE_FINISH:
                DownDB.getInstance(App.getContext()).upDate(item.name, url, "" + "", DownloadItem.DOWNLOAD_STATE_FINISH);
                FileUitls.moveFile(url, item.name, App.getContext());
                if (DownloadingItems.contains(url)) {
                    DownloadingItems.remove(url);
                }
                if (pauseItems.contains(url)) {
                    pauseItems.remove(url);
                }
                break;
            case DownloadItem.DOWNLOAD_STATE_ERROR:
                DownDB.getInstance(App.getContext()).delItem(item.url);
                FileUitls.delFile(item.url, App.getContext());
                if (DownloadingItems.contains(url)) {
                    DownloadingItems.remove(url);
                }
                if (pauseItems.contains(url)) {
                    pauseItems.remove(url);
                }
                break;
        }
        if (messageState != -1) {
            Message message = new Message();
            message.what = messageState;
            message.obj = item;
            mHandler.sendMessage(message);
        }
        DownLoadManager.sendDownloadUpdateBroadCast(item);

    }


}


