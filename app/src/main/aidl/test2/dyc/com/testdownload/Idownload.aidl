// Idownload.aidl
package test2.dyc.com.testdownload;

// Declare any non-default types here with import statements
interface Idownload {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void start(String url,String name) ;

     void stop(String url) ;

     void stopAll() ;


}
