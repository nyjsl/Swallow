package org.nyjsl.swallow.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by weix01 on 2015-07-20.
 */
public class FileUtil {

    /**
     * 获取Cache DIR
     *
     * @param context
     * @return
     */
    public static File getCacheDirectory(Context context) {
        File appCacheDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(Environment.getExternalStorageDirectory(),"swallow");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()),"cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                // ("Unable to create external cache directory");
                return null;
            }
        }
        return appCacheDir;
    }
}
