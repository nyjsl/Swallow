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

    /**
     *计算文件夹下文件及子文件夹所有文件的大小
     * @param dirPath
     * @return
     */
    public static long calDirSize(String dirPath){
        long sum = 0;
        File f = new File(dirPath);
        if(null != f && f.isDirectory()){
            File[] fs = f.listFiles();
            final int len = fs.length;
            for( int i=0;i<len;i++){
                File l = fs[i];
                if(null != l)
                    if(l.isDirectory()){
                        sum += calDirSize(l.getPath());
                    }else{
                        sum += l.length();
                    }
            }
        }
        return sum;
    }

    /**
     * 清除文件目录所有的文件及子目录
     */
    public static void clearDirFiles(String dirPath){
        File f = new File(dirPath);
        if(null != f && f.isDirectory()){
            File[] fs = f.listFiles();
            final int len = fs.length;
            for( int i=0;i<len;i++){
                File l = fs[i];
                if(null != l){
                    if(l.isDirectory()){
                        clearDirFiles(l.getPath());
                    }else{
                        l.delete();
                    }

                }
            }
        }
    }
}
