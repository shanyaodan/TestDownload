package test2.dyc.com.testdownload;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by Administrator on 2015/7/3.
 */
public class MyDownload {

 //注册信息
/*    <receiver android:name=".MyDownload$CompleteReceiver" >
    <intent-filter>
    <action android:name="android.intent.action.DOWNLOAD_COMPLETE" />
    <action android:name="android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED" />
    </intent-filter>
    </receiver>*/
    private static Context context;
    private DownloadManager dm;
    static private long downloadId;
    private String url;

    public MyDownload() {

    }


    MyDownload(Context context, String url) {
        this.context = context;
        dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        this.url = url;
    }

    @SuppressLint("NewApi")
    public void startDownload() {

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        String apkName = "mytest";
        request.setDestinationInExternalPublicDir("mytest", Constants.UPDATE_FILE_NAME);
        System.out.println("apk addddddd path");
        request.setTitle(apkName);
        request.setDescription("正在下载...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(false);
        // 加入下载队列
        downloadId = dm.enqueue(request);
    }

    public static class CompleteReceiver extends BroadcastReceiver {

        /**
         * 查看下载状态
         *
         * @param downloadId
         * @return
         */
        DownloadManager dm = null;
        private String path;

        public CompleteReceiver() {
            super();
        }

        @SuppressLint("NewApi")
        public int getStatusById(long downloadId) {
            DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
            int result = -1;
            Cursor c = null;
            try {
                c = dm.query(query);
                if (c != null && c.moveToFirst()) {
                    result = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                }
                path = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            } finally {
                if (c != null) {
                    c.close();
                }
            }
            return result;
        }


        public boolean install(Context context) {
            if (TextUtils.isEmpty(path) && !path.endsWith(".apk")) {
                return false;
            }
            try {
                Intent i = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse("file://" + path);
                i.setDataAndType(uri, "application/vnd.android.package-archive");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        @SuppressLint("NewApi")
        @Override
        public void onReceive(Context context, Intent intent) {
            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            if (getStatusById(completeDownloadId) == DownloadManager.STATUS_SUCCESSFUL) {

                install(context);
            }
        }
    }

    ;

}
