// Idownload.aidl
package test2.dyc.com.testdownload;

// Declare any non-default types here with import statements
import test2.dyc.com.testdownload.DownloadItem;
interface Idownload {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void start(in DownloadItem item) ;


     void pause(in DownloadItem item) ;


     void stop(in DownloadItem item) ;


     void resume() ;


     void stopAll() ;


}
