package test2.dyc.com.testdownload;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/7/3.
 */
public class DownloadItem implements Serializable{

    public String url;
    public String name;
    public long currentSize;
    public long totalSize;
    public long currentTime;

}
