package test2.dyc.com.testdownload;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/7/3.
 */
public class DownloadItem implements Parcelable {

    public String url;
    public String name;
    public String currentSize;
    public String totalSize;
    public String currentTime;
    public int state;
    public int downloadPercent;

    /**
     * 数据查询使用，判断下载状况
     */
    public static final int DOWNLOAD_STATE_NOTINT = -1;
    public static final int DOWNLOAD_STATE_DOING = 1;
    public static final int DOWNLOAD_STATE_PAUSE = 2;
    public static final int DOWNLOAD_STATE_FINISH = 3;
    public static final int DOWNLOAD_STATE_ERROR = 4;


    public DownloadItem() {

    }


    protected DownloadItem(Parcel in) {
        url = in.readString();
        name = in.readString();
        currentSize = in.readString();
        totalSize = in.readString();
        currentTime = in.readString();
        state = in.readInt();
    }

    public static final Creator<DownloadItem> CREATOR = new Creator<DownloadItem>() {
        @Override
        public DownloadItem createFromParcel(Parcel in) {
            return new DownloadItem(in);
        }

        @Override
        public DownloadItem[] newArray(int size) {
            return new DownloadItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(name);
        dest.writeString(currentSize);
        dest.writeString(totalSize);
        dest.writeString(currentTime);
        dest.writeInt(state);
    }

    @Override
    public String toString() {
        return "DownloadItem{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", currentSize='" + currentSize + '\'' +
                ", totalSize='" + totalSize + '\'' +
                ", currentTime='" + currentTime + '\'' +
                ", state=" + state +
                ", downloadPercent=" + downloadPercent +
                '}';
    }
}
