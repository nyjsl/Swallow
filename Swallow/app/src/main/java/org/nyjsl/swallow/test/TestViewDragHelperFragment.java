package org.nyjsl.swallow.test;

import org.nyjsl.swallow.R;
import org.nyjsl.swallow.ui.fragment.BaseFragement;
import org.nyjsl.swallow.widgets.MDragFrame;

import butterknife.Bind;

/**
 * Created by weix01 on 2015-12-31.
 */
public class TestViewDragHelperFragment extends BaseFragement {

    @Bind(R.id.drag_frame)  MDragFrame dragFrame;

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
        return R.layout.activity_test_view_drag_helper;
    }

    @Override
    protected void init() {
        dragFrame.setUp();
    }

}
