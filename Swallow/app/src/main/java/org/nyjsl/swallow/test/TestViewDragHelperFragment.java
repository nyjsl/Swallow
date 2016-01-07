package org.nyjsl.swallow.test;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;

import org.nyjsl.swallow.R;
import org.nyjsl.swallow.ui.fragment.BaseFragement;
import org.nyjsl.swallow.widgets.MDragFrame;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by weix01 on 2015-12-31.
 */
public class TestViewDragHelperFragment extends BaseFragement {

    @Bind(R.id.drag_frame)  MDragFrame dragFrame;

    @Bind(R.id.circle) View circle;

    @Bind(R.id.raise_bt) Button raise_bt;
    @Bind(R.id.lower_bt) Button lower_bt;

    public static TestViewDragHelperFragment newInstance() {
        TestViewDragHelperFragment fragment = new TestViewDragHelperFragment();
        return fragment;
    }

    public TestViewDragHelperFragment() {
        // Required empty public constructor
    }


    @Override
    protected void setListeners() {

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_test_view_drag_helper;
    }

    @Override
    protected void init() {
        dragFrame.setUp();
    }

    @OnClick(R.id.raise_bt)
    void raise(){
        float elevation = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            elevation = circle.getElevation();
            elevation =  elevation+8;
            circle.setElevation(elevation);
            Snackbar.make(raise_bt,elevation+"",Snackbar.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.lower_bt)
    void lower(){
        float elevation = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            elevation = circle.getElevation();
            elevation =  elevation-8 <0 ? 0:  elevation-8;
            circle.setElevation(elevation);
            Snackbar.make(raise_bt,elevation+"",Snackbar.LENGTH_SHORT).show();
        }
    }

}
