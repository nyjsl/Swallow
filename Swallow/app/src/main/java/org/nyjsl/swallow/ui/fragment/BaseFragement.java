package org.nyjsl.swallow.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;

import org.nyjsl.swallow.Swallow;
import org.nyjsl.swallow.utils.DialogUtil;
import org.nyjsl.swallow.utils.MaterialDialogUtil;
import org.nyjsl.swallow.utils.ToastUtils;

import butterknife.ButterKnife;


public abstract class BaseFragement extends Fragment  {




    protected Context mContext;

    private MaterialDialog materialDialog;

    private ProgressDialog dialog;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment BaseFragement.
     */
//    public static BaseFragement newInstance() {
//        BaseFragement fragment = new BaseFragement();
//        return fragment;
//    }


    public BaseFragement() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        ButterKnife.bind(this, view);

        setListeners();
        init();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayout(),null);
    }

    protected  abstract  int getFragmentLayout();

    protected abstract void init();

    protected abstract void setListeners();

    /**
     * show system toast duration long
     * @param msg
     */
    protected void showToast(String msg){
        ToastUtils.show(getActivity(), msg);
    }
    /**
     * show system toast duration long
     * @param resId
     */
    protected void showToast(int resId){
        ToastUtils.show(getActivity(), resId);
    }

    /**
     * show custom toast duration long
     * @param resId
     * @param viewId
     */
    protected void showToast(int resId,int viewId){
        ToastUtils.show(getActivity(), resId, viewId);
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

    /**
     *  show progress dialog
     * @param msg
     */
    private void showProgressDialogNormal(String msg){
        if(null == dialog )  dialog = DialogUtil.buildProgressDialog(mContext);
        if(dialog.isShowing()) return;

        if(!TextUtils.isEmpty(msg)) dialog.setMessage(msg);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });


    }

    private void showMaterialIndeterminateProgressDialog(String msg){
        if(null == materialDialog) materialDialog = MaterialDialogUtil.buildIndeterminateProgressDialog(mContext);
        if(materialDialog.isShowing()) return;
        if(!TextUtils.isEmpty(msg)) materialDialog.setContent(msg);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                materialDialog.show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
        ButterKnife.unbind(this);
    }

    protected void dismissProgressDialog() {
        if(Swallow.Configuration.MATERIAL_DIALOG){
            dismissMaterialProgressDialog();
        }else {
            dismissProgressDialogNormal();
        }
    }

    /**
     * dismiss progressdialog
     */
    private void dismissProgressDialogNormal(){
        if(null == dialog ) return;
        if(dialog.isShowing()) getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        });

    }

    private void dismissMaterialProgressDialog() {
        if(null == materialDialog ) return;
        if(materialDialog.isShowing()) getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                materialDialog.dismiss();
            }
        });
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
