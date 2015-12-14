package org.nyjsl.swallow;

import android.app.Activity;
import android.app.Application;

import org.nyjsl.swallow.receivers.NetworkStateReceiver;
import org.nyjsl.swallow.ui.BaseActivity;
import org.nyjsl.swallow.utils.NetWorkUtil;
import org.nyjsl.swallow.utils.Remember;


/**
 * Created by weix01 on 2015-07-20.
 */
public class Swallow extends Application implements NetworkStateReceiver.NetChangeObserver{

    private static final String APP_TAG = "SWALLOW";

    public Activity mCurrentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        Remember.init(getApplicationContext(), APP_TAG);
        NetworkStateReceiver.registerObserver(this);
    }

    @Override
    public void onConnect(NetWorkUtil.NetType type) {
        mCurrentActivity = ActivityStack.getActivityStack().currentActivity();
        if (mCurrentActivity != null) {
            if (mCurrentActivity instanceof BaseActivity) {
                ((BaseActivity) mCurrentActivity).onConnect(type);
            }
        }
    }

    @Override
    public void onDisConnect() {
        mCurrentActivity = ActivityStack.getActivityStack().currentActivity();
        if (mCurrentActivity != null) {
            if (mCurrentActivity instanceof BaseActivity) {
                ((BaseActivity) mCurrentActivity).onDisConnect();
            }
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        NetworkStateReceiver.removeRegisterObserver(this);
    }

    public static class  Configuration{
       public static boolean MATERIAL_DIALOG = true;
    }
}
