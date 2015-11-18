package org.nyjsl.swallow.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.afollestad.materialdialogs.MaterialDialog;

import org.nyjsl.swallow.Swallow;
import org.nyjsl.swallow.presenter.Presenter;
import org.nyjsl.swallow.utils.DialogUtil;
import org.nyjsl.swallow.utils.MaterialDialogUtil;
import org.nyjsl.swallow.utils.ToastUtils;
import org.nyjsl.swallow.views.BaseView;

import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity implements BaseView{

    protected Context mContext = null;

    private MaterialDialog materialDialog;

    private ProgressDialog dialog;

    private FragmentManager supportFragmentManager;

    protected Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getContentLayout());
        ButterKnife.bind(this);
        supportFragmentManager = getSupportFragmentManager();
        setListeners();
        init();
    }

    /**
     * sub class should call this method andd
     * pass a sub Presenter
     * @param presenter
     */
    protected void bindPresenter(Presenter presenter){
        this.presenter = presenter;
        presenter.attachView(this);
    }

    @Override
    public void showD() {
        showProgressDialog();
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showD(int id) {
        showProgressDialog(id);
    }

    @Override
    public void disD() {
        dismissProgressDialog();
    }

    @Override
    public void showT(int id) {
        showToast(id);
    }

    /**
     * get ContentView layout res id
     * @return
     */
    protected abstract int getContentLayout();

    protected abstract void setListeners();

    protected  abstract void init();

    protected void replaceFragment(int containerId,Fragment fr) {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(containerId, fr);
        fragmentTransaction.commitAllowingStateLoss();
    }


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
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
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
