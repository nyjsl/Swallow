package org.nyjsl.swallow.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import org.nyjsl.swallow.utils.NetWorkUtil;

import java.util.ArrayList;

/**
 * Created by weix01 on 2015-12-14.
 */
public class NetworkStateReceiver extends BroadcastReceiver {

    private final static String ANDROID_NET_CHANGE_ACTION = ConnectivityManager.CONNECTIVITY_ACTION;//"android.net.conn.CONNECTIVITY_CHANGE";
    private static Boolean networkAvailable = false;
    private static NetWorkUtil.NetType netType;
    private static ArrayList<NetChangeObserver> netChangeObserverArrayList;

    public static void register(Context mContext){
        IntentFilter filter = new IntentFilter();
        filter.addAction(ANDROID_NET_CHANGE_ACTION);
        mContext.getApplicationContext().registerReceiver(getReceiver(), filter);
    }

    /**
     * 注销网络状态广播
     */
    public static void unRegister(Context mContext) {
        if (receiver != null) {
            try {
                mContext.getApplicationContext().unregisterReceiver(receiver);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

    }

    private static BroadcastReceiver receiver;

    private static BroadcastReceiver getReceiver() {
        if (receiver == null) {
            receiver = new NetworkStateReceiver();
        }
        return receiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION)) {
            if (!NetWorkUtil.isNetworkAvailable(context)) {
                networkAvailable = false;
            } else {
                netType = NetWorkUtil.getAPNType(context);
                networkAvailable = true;
            }
            notifyObserver();
        }
    }

    /**
     * 注册网络连接观察者
     */
    public static void registerObserver(NetChangeObserver observer) {
        if (netChangeObserverArrayList == null) {
            netChangeObserverArrayList = new ArrayList<NetChangeObserver>();
        }
        netChangeObserverArrayList.add(observer);
    }

    /**
     * 注销网络连接观察者
     */
    public static void removeRegisterObserver(NetChangeObserver observer) {
        if (netChangeObserverArrayList != null) {
            netChangeObserverArrayList.remove(observer);
        }
    }

    private void notifyObserver() {

        for (int i = 0; i < netChangeObserverArrayList.size(); i++) {
            NetChangeObserver observer = netChangeObserverArrayList.get(i);
            if (observer != null) {
                if (isNetworkAvailable()) {
                    observer.onConnect(netType);
                } else {
                    observer.onDisConnect();
                }
            }
        }

    }

    /**
     * 获取当前网络状态，true为网络连接成功，否则网络连接失败
     */
    public static Boolean isNetworkAvailable() {
        return networkAvailable;
    }

    public static NetWorkUtil.NetType getAPNType() {
        return netType;
    }

    public interface NetChangeObserver{
        /**
         * 网络连接连接时调用
         */
        public void onConnect(NetWorkUtil.NetType type);

        /**
         * 当前没有网络连接
         */
        public void onDisConnect();
    }
}
