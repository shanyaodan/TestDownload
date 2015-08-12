package test2.dyc.com.testdownload;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/27.
 */
public class DownDB extends SQLiteOpenHelper {
    private static  DownDB mDownloadDB;
    public DownDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    public static DownDB getInstance(Context context) {
        if(null != mDownloadDB) {
            return mDownloadDB;
        }
        mDownloadDB = new DownDB(context,"download_info.db",null ,3);
        return mDownloadDB;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists download_info("
                + "id INTEGER primary key,"
                + "name varchar,"
                + "url varchar,"
                + "totalSize varchar,"
                +"downstate varchar" +
                        ")"
                );
    }

    public void InsetData(String name,String url,String totalSize,int state) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("downstate",state);
        contentValues.put("totalSize",totalSize);
        contentValues.put("url",url);
        contentValues.put("name",name);
        getWritableDatabase().insert("download_info", null, contentValues);
        closeDB();
    }

    public void upDate(String name,String url,String totalSize,int state) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("totalSize",totalSize);
        getWritableDatabase().update("download_info",contentValues,"url=?",new String[]{url});
        closeDB();
    }



    public ArrayList<DownloadItem> getDownloadList() {
        String sql = "select * from download_info";
        Cursor cursor = getWritableDatabase().rawQuery(sql, null);
        if (null == cursor) {
        return null;
    }
        ArrayList<DownloadItem> items = new ArrayList<DownloadItem>();
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
        {
            DownloadItem downloadItem = new DownloadItem();
            downloadItem.url =   cursor.getString(cursor.getColumnIndex("url"));
            downloadItem.name =   cursor.getString(cursor.getColumnIndex("name"));
            downloadItem.totalSize = cursor.getString(cursor.getColumnIndex("totalSize"));
            downloadItem.state = cursor.getInt(cursor.getColumnIndex("downstate"));
            items.add(downloadItem);
         }
            return items;
    }

    public DownloadItem selectItem(String url) {
        String sql = "select * from download_info where url=?";
        Cursor cursor = getWritableDatabase().rawQuery(sql, new String[]{url});
        if(null == cursor) {
            return null;
        }
        cursor.moveToFirst();
        DownloadItem downloadItem =  null;
        if(cursor.getCount()>0) {
            downloadItem = new DownloadItem();
            downloadItem.name =  cursor.getString(cursor.getColumnIndex("name"));
            downloadItem.url =   cursor.getString(cursor.getColumnIndex("url"));
            downloadItem.totalSize = cursor.getString(cursor.getColumnIndex("totalSize"));
            downloadItem.state = cursor.getInt(cursor.getColumnIndex("downstate"));
        }
        closeDB();
        return downloadItem;
    }

    public void delItem(String url){
        String sql = "delete from download_info where url=?";
        Cursor cursor = getWritableDatabase().rawQuery(sql, new String[]{url});
        closeDB();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void closeDB() {
            if(null!=getWritableDatabase()&&getWritableDatabase().isOpen()) {
                getWritableDatabase().close();
            }

    }

}
