package org.nyjsl.swallow.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

import org.nyjsl.swallow.R;

/**
 * Created by weix01 on 2015-11-13.
 */
public class DialogUtil {


    public static ProgressDialog buildProgressDialog(Context mContext){
        return buildProgressDialog(mContext,"");
    }

    public static ProgressDialog buildProgressDialog(Context mContext,String context){
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        if(!TextUtils.isEmpty(context)){
            progressDialog.setMessage(context);
        }else{
            progressDialog.setMessage(mContext.getString(R.string.loading_str));
        }
        progressDialog.setCanceledOnTouchOutside(true);
        return progressDialog;
    }
}
