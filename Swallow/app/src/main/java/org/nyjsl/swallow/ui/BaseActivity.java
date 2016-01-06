package org.nyjsl.swallow.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.afollestad.materialdialogs.MaterialDialog;

import org.nyjsl.swallow.ActivityStack;
import org.nyjsl.swallow.Swallow;
import org.nyjsl.swallow.presenter.Presenter;
import org.nyjsl.swallow.receivers.NetworkStateReceiver;
import org.nyjsl.swallow.ui.fragment.BaseFragement;
import org.nyjsl.swallow.utils.DialogUtil;
import org.nyjsl.swallow.utils.MaterialDialogUtil;
import org.nyjsl.swallow.utils.NetWorkUtil;
import org.nyjsl.swallow.utils.ToastUtils;
import org.nyjsl.swallow.views.IBaseView;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    protected Context mContext = null;

    private MaterialDialog materialDialog;

    private ProgressDialog dialog;

    private FragmentManager supportFragmentManager;

    protected Presenter presenter;

    public MyHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        NetworkStateReceiver.register(this);
        setContentView(getContentLayout());
        ButterKnife.bind(this);
        ActivityStack.getActivityStack().addActivity(this);
        mHandler = new MyHandler(this);
        supportFragmentManager = getSupportFragmentManager();
        setListeners();
        init();
    }


    public abstract int getFragmentContainerId();

    private static class MyHandler extends Handler {

        WeakReference<BaseActivity> mReference = null;

        MyHandler(BaseActivity activity) {
            this.mReference = new WeakReference<BaseActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseActivity outer = mReference.get();
            if (outer == null && outer.isFinishing()) {
                return;
            }

            outer.handleMessage(msg);
        }
    }
    public abstract void handleMessage(Message msg);
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

    public void replaceFragment(Fragment fr) {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(getFragmentContainerId(), fr);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void replaceFragment(Class<? extends BaseFragement> fragmentKlazz){
        if(null == fragmentKlazz){
            return ;
        }
        BaseFragement fr = null;
        try {
            fr = fragmentKlazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(getFragmentContainerId(), fr);
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

    protected  void jumpForResult(Class<? extends BaseActivity> klazz,int requestCode,Bundle bundle){
        Intent intent = new Intent(mContext,klazz);
        if(null != bundle)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    protected void jump(Class<? extends BaseActivity> klazz){
        jump(klazz, false, null);
    }
    protected void jump(Class<? extends BaseActivity> klazz,boolean finish,Bundle bundle){
        Intent intent = new Intent(mContext,klazz);
        if(null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
        if(finish)
            finish();
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
        if(null != presenter)
            presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(null != presenter)
            presenter.stop();
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null) mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
        dismissProgressDialog();
        ButterKnife.unbind(this);
        NetworkStateReceiver.unRegister(this);
        ActivityStack.getActivityStack().finishActivity(this);
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

    public abstract void onConnect(NetWorkUtil.NetType type);
    public abstract void onDisConnect();

}
