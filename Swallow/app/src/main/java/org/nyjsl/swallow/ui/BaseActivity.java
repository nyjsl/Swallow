package org.nyjsl.swallow.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.afollestad.materialdialogs.MaterialDialog;

import org.nyjsl.swallow.Swallow;
import org.nyjsl.swallow.utils.DialogUtil;
import org.nyjsl.swallow.utils.MaterialDialogUtil;
import org.nyjsl.swallow.utils.ToastUtils;

import butterknife.ButterKnife;

public abstract class BaseActivity extends Activity  {

    protected Context mContext = null;

    private MaterialDialog materialDialog;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getContentLayout());
        ButterKnife.bind(this);
        setListeners();
        init();
    }

    /**
     * get ContentView layout res id
     * @return
     */
    protected abstract int getContentLayout();

    protected abstract void setListeners();

    protected  abstract void init();


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * show system toast duration long
     * @param msg
     */
    protected void showToast(String msg){
        ToastUtils.show(this, msg);
    }
    /**
     * show system toast duration long
     * @param resId
     */
    protected void showToast(int resId){
        ToastUtils.show(this, resId);
    }

    /**
     * show custom toast duration long
     * @param resId
     * @param viewId
     */
    protected void showToast(int resId,int viewId){
        ToastUtils.show(this,resId,viewId);
    }

    protected  void showProgressDialog(){
        if(Swallow.Configuration.MATERIAL_DIALOG){
            showMaterialIndeterminateProgressDialog("");
        }else{
            showProgressDialogNormal("");
        }
    }
    protected  void showProgressDialog(int resId){
        if(Swallow.Configuration.MATERIAL_DIALOG){
            showMaterialIndeterminateProgressDialog(getString(resId));
        }else{
            showProgressDialogNormal(getString(resId));
        }
    }

    protected  void showProgressDialog(String msg){
        if(Swallow.Configuration.MATERIAL_DIALOG){
            showMaterialIndeterminateProgressDialog(msg);
        }else{
            showProgressDialogNormal(msg);
        }
    }

    /**
     * show progress dialog
     */
    private void showProgressDialogNormal(String msg){

        if(isFinishing()) return;

        if(null == dialog )  dialog = DialogUtil.buildProgressDialog(mContext);
        if(dialog.isShowing()) return;

        if(!TextUtils.isEmpty(msg))  dialog.setMessage(msg);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });


    }

    private void showMaterialIndeterminateProgressDialog(String msg){

        if(isFinishing()) return;

        if(null == materialDialog) materialDialog = MaterialDialogUtil.buildIndeterminateProgressDialog(mContext);
        if(materialDialog.isShowing()) return;

        if(!TextUtils.isEmpty(msg)) materialDialog.setContent(msg);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                materialDialog.show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
        ButterKnife.unbind(this);
    }

    protected void dismissProgressDialog(){
        if(Swallow.Configuration.MATERIAL_DIALOG){
            dismissMaterialProgressDialog();
        }else{
            dismissProgressDialogNormal();
        }
    }

    /**
     * dismiss progressdialog
     */
    private void dismissProgressDialogNormal(){
        if(null == dialog ) return;
        if(dialog.isShowing()) runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        });

    }

    private void dismissMaterialProgressDialog() {
        if(null == materialDialog ) return;
        if(materialDialog.isShowing()) runOnUiThread(new Runnable() {
            @Override
            public void run() {
                materialDialog.dismiss();
            }
        });
    }
}
