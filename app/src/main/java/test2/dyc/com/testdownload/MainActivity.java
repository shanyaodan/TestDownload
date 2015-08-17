package test2.dyc.com.testdownload;

import android.app.DownloadManager;
import android.content.ComponentName;
import android.net.Uri;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


public class MainActivity extends ActionBarActivity {
String downo ="http://apk.hiapk.com/appdown/com.gamevil.fishing.global?planid=1235201&seid=c6ba2047-94b0-0001-98ed-147010d01500";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  final   DownLoadManager downLoadManager = new DownLoadManager();
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoadManager.start(downo,"test");
            }
        });
        findViewById(R.id.pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoadManager.stop(downo);
            }
        });


        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoadManager.removeData(downo);
//              downLoadManager.start("http://count.liqucn.com/d.php?id=441834&ArticleOS=android&content_url=http://www.liqucn.com/rj/441834.shtml&down_url=kpa.1_gnipousiriem/iacil/4102/daolpu/moc.ncuqil.elif//:ptth&is_cp=1&market_place_id=11","test");
            }
        });
//    MyDownload md= new MyDownload(this,"http://count.liqucn.com/d.php?id=441834&ArticleOS=android&content_url=http://www.liqucn.com/rj/441834.shtml&down_url=kpa.1_gnipousiriem/iacil/4102/daolpu/moc.ncuqil.elif//:ptth&is_cp=1&market_place_id=11");
//        md.startDownload();
    }

}
