package org.nyjsl.swallow.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.TextUtils;

import com.afollestad.materialdialogs.MaterialDialog;

import org.nyjsl.swallow.R;


/**
 * Created by weix01 on 2015-11-10.
 */
public class MaterialDialogUtil {


    public static  MaterialDialog buildIndeterminateProgressDialog(Context mContext){
        return buildIndeterminateProgressDialog(mContext,"");
    }
    public static  MaterialDialog buildIndeterminateProgressDialog(Context mContext,String context){
        return buildIndeterminateProgressDialog(mContext,false,context);
    }
    public static MaterialDialog buildIndeterminateProgressDialog(Context mContext,boolean horizontal,String content){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext);
        if(!TextUtils.isEmpty(content)){
            builder = builder.content(content);
        }else{
            builder = builder.content(mContext.getString(R.string.loading_str));
        }
        return builder.progress(true, 0).progressIndeterminateStyle(horizontal).build();
    }

    public static void showMaterialInputDialog(Context mContext,String posStr,String hintStr, MaterialDialog.InputCallback callback){
        showMaterialInputDialog(mContext, "", posStr, hintStr, callback);
    }

    public static void showMaterialInputDialog(Context mContext,String title,String posStr,String hintStr, MaterialDialog.InputCallback callback){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext);
        if(!TextUtils.isEmpty(title)) builder = builder.title(title);
        builder.inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(2, 16)
                .positiveText(posStr)
                .input(hintStr, hintStr, false, callback).show();
    }

    public static void showCancelCallbacks(Context mContext,String content,MaterialDialog.SingleButtonCallback callback){
        showCancelCallbacks(mContext, "", content, "", callback, null);
    }
    public static void showCancelCallbacks(Context mContext,String content,String confrimStr,MaterialDialog.SingleButtonCallback callback){
        showCancelCallbacks(mContext, "", content, confrimStr, "", callback, null);
    }


    public static void showCancelCallbacks(Context mContext,String content,String confrimStr,String cancelStr,MaterialDialog.SingleButtonCallback callback){
        showCancelCallbacks(mContext, "", content, confrimStr, cancelStr, callback, null);
    }

    public static void showCancelCallbacks(Context mContext,String content,String confrimStr,String cancelStr,MaterialDialog.SingleButtonCallback callback,DialogInterface.OnDismissListener dismissListener){
        showCancelCallbacks(mContext, "", content, confrimStr, cancelStr, callback, dismissListener);
    }

    public static void showCancelCallbacks(Context mContext,String title,String content,String confrimStr,String cancelStr,MaterialDialog.SingleButtonCallback callback,DialogInterface.OnDismissListener dismissListener){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext);
        if(null != title) builder = builder.title(title);
        builder = builder.content(content);
        if(!TextUtils.isEmpty(confrimStr)){
            builder = builder.positiveText(confrimStr);
        }else{
            builder = builder.positiveText(R.string.md_choose_label);
        }
        if(!TextUtils.isEmpty(cancelStr)){
            builder = builder.negativeText(cancelStr);
        }else{
            builder = builder.negativeText(R.string.md_cancel_label);
        }
        builder.onAny(callback);
        if(null != dismissListener) builder = builder.dismissListener(dismissListener);
        builder.show();
    }


    public static void showList(Context mContext,MaterialDialog.ListCallback listCallback,String ...items){
        showList(mContext, "", listCallback, items);
    }

    public static void showList(Context mContext,MaterialDialog.ListCallback listCallback,int resId){
       showList(mContext,"", listCallback, resId);
    }
    public static void showList(Context mContext,String title,MaterialDialog.ListCallback listCallback,int resId){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext);
        if(!TextUtils.isEmpty(title)) builder = builder.title(title);
        builder.items(resId)
                .itemsCallback(listCallback)
                .show();
    }

    public static void showList(Context mContext,String title,MaterialDialog.ListCallback listCallback,String ...items){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext);
        if(!TextUtils.isEmpty(title)) builder = builder.title(title);
        builder.items(items)
                .itemsCallback(listCallback)
                .show();
    }



    public static void showListSinglChoice(Context mContext,MaterialDialog.ListCallbackSingleChoice singleChoice,String ...items){
       showListSinglChoice(mContext, "", singleChoice, items);
    }

    public static void showListSinglChoice(Context mContext,String title,MaterialDialog.ListCallbackSingleChoice singleChoice,String ...items){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext);
        if(!TextUtils.isEmpty(title)) builder = builder.title(title);

        builder.items(items)
                .itemsCallbackSingleChoice(0, singleChoice)
                .positiveText(R.string.md_choose_label)
                .show();
    }

    public static void showMutilChoice(Context mContext,MaterialDialog.ListCallbackMultiChoice callback,String ...items) {
       showMutilChoice(mContext, "",callback, items);
    }

    public static void showMutilChoice(Context mContext,String title,MaterialDialog.ListCallbackMultiChoice callback,String ...items) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext);
        if(!TextUtils.isEmpty(title)) builder = builder.title(title);
        builder.items(items)
                .itemsCallbackMultiChoice(new Integer[]{0}, callback)
                .positiveText(R.string.md_choose_label)
                .show();
    }

}
