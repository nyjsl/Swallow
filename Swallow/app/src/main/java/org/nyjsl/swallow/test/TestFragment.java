package org.nyjsl.swallow.test;

import android.widget.Button;
import android.widget.TextView;

import org.nyjsl.swallow.R;
import org.nyjsl.swallow.ui.fragment.BaseFragement;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by weix01 on 2015-11-13.
 */
public class TestFragment  extends BaseFragement {

    @Bind(R.id.test_tv) TextView  test_tv;
    @Bind(R.id.tv_test) TextView  tv_test;
    @Bind(R.id.show)  Button show;
    @Bind(R.id.kill) Button  kill;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_test;
    }

    @Override
    protected void init() {
        test_tv.setText("我擦泪,真心帅");
    }

    public static TestFragment newInstance() {
        TestFragment fragment = new TestFragment();
        return fragment;
    }

    public TestFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setListeners() {

    }

    @OnClick(R.id.show)
    void show() {
        showProgressDialog();
    }

    @OnClick(R.id.kill)
    void kill() {
        showToast("我这么可怜,你忍心吗");
    }
}
