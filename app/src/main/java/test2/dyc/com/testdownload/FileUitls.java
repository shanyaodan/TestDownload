package test2.dyc.com.testdownload;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * Created by Administrator on 2015/7/27.
 *
 */
public class FileUitls {


//    public static void saveData(String url,Serializable data,Context context) {
//        try {
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(getFile(url,context)));
//            objectOutputStream.writeObject(data);
//        } catch (IOException e) {
//
//            e.printStackTrace();
//        }
//    }




//    public static  Object getData(String url,Context context){
//
//        if(!checkFileExit(url,context)) {
//            return null;
//        }
//        File file = new File(getSavePath(context)+"/"+url.hashCode());
//        try {
//                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
//                    return    objectInputStream.readObject();
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            catch (IOException e) {
//                e.printStackTrace();
//        }
//        return null;
//    }

    public static  String getTempSavePath(Context context) {
        if(!new File(context.getFilesDir().getAbsolutePath()+"/temp").exists()) {
            new File(context.getFilesDir().getAbsolutePath()+"/temp").mkdirs();
        }
        return context.getFilesDir().getAbsolutePath()+"/temp";

    }
    public static  String getCompleteSavePath(Context context) {
        if(!new File(context.getFilesDir().getAbsolutePath()+"/data").exists()) {
            new File(context.getFilesDir().getAbsolutePath()+"/data").mkdirs();
        }
        return context.getFilesDir().getAbsolutePath()+"/data";

    }

    public static boolean checkFileExit(String url,Context context){
        if(getFile(url,context).exists()) {
            return true;
        }
    return false;
    }



    public static File getFile(String url,Context context) {
     return    new File(getTempSavePath(context)+"/"+url.hashCode());

    }

    public static File getCompleteSaveFile(String name) {
        return    new File(getTempSavePath(App.getContext())+"/"+name);
    }


    public static void delFile(String url,Context context){
        new File(getTempSavePath(context)+"/"+url.hashCode()).delete();
    }

    public static void moveFile(String url,String fileName,Context context) {

        new File(getTempSavePath(context)+"/"+url.hashCode()).renameTo(new File(getCompleteSavePath(context) + "/" + fileName));

    }


}
