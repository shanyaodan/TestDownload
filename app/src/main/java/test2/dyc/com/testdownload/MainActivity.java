package test2.dyc.com.testdownload;

import android.app.DownloadManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    MyDownload md= new MyDownload(this,"http://count.liqucn.com/d.php?id=441834&ArticleOS=android&content_url=http://www.liqucn.com/rj/441834.shtml&down_url=kpa.1_gnipousiriem/iacil/4102/daolpu/moc.ncuqil.elif//:ptth&is_cp=1&market_place_id=11");
        md.startDownload();
    }

}
