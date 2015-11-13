package org.nyjsl.swallow.test;

import android.widget.Button;
import android.widget.TextView;

import org.nyjsl.swallow.R;
import org.nyjsl.swallow.Swallow;
import org.nyjsl.swallow.ui.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;


public class TestMainActivity extends BaseActivity {


    @Bind(R.id.test_tv) TextView  test_tv;
    @Bind(R.id.tv_test) TextView  tv_test;
    @Bind(R.id.show)  Button show;
    @Bind(R.id.change) Button  change;
    @Bind(R.id.duang) Button  duang;


    @Override
    protected int getContentLayout() {
        return R.layout.activity_test_main;
    }


    @Override
    protected void setListeners() {

    }

    @Override
    protected void init() {
        tv_test.setText("对呀,就是这么屌");
    }

    @OnClick(R.id.show)
    void show(){
        showProgressDialog();
    }
    @OnClick(R.id.change)
    void change(){
        Swallow.Configuration.MATERIAL_DIALOG = !Swallow.Configuration.MATERIAL_DIALOG;
    }
    @OnClick(R.id.duang)
    void duang(){
        replaceFragment(R.id.container,TestFragment.newInstance());
    }
}
