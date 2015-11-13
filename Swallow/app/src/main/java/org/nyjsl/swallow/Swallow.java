package org.nyjsl.swallow;

import android.app.Application;

import org.nyjsl.swallow.utils.Remember;


/**
 * Created by weix01 on 2015-07-20.
 */
public class Swallow extends Application {

    private static final String APP_TAG = "SWALLOW";



    @Override
    public void onCreate() {
        super.onCreate();
        Remember.init(getApplicationContext(), APP_TAG);
    }


    public static class  Configuration{
       public static boolean MATERIAL_DIALOG = true;
    }
}
