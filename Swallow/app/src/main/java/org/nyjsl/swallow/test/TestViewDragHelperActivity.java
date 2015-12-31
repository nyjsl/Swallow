package org.nyjsl.swallow.test;

import android.os.Message;

import org.nyjsl.swallow.R;
import org.nyjsl.swallow.ui.BaseActivity;
import org.nyjsl.swallow.utils.NetWorkUtil;
import org.nyjsl.swallow.widgets.MDragFrame;

import butterknife.Bind;

/**
 * Created by weix01 on 2015-12-31.
 */
public class TestViewDragHelperActivity extends BaseActivity {

    @Bind(R.id.drag_frame)  MDragFrame dragFrame;

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_test_view_drag_helper;
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void init() {
        dragFrame.setUp();
    }

    @Override
    public void onConnect(NetWorkUtil.NetType type) {

    }

    @Override
    public void onDisConnect() {

    }
}
