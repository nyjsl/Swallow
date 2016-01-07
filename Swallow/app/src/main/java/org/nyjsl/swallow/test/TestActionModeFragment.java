package org.nyjsl.swallow.test;

import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import org.nyjsl.swallow.R;
import org.nyjsl.swallow.ui.BaseActivity;
import org.nyjsl.swallow.ui.fragment.BaseFragement;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by weix01 on 2016-01-07.
 */
public class TestActionModeFragment extends BaseFragement {


    @Bind(R.id.show_action_mode)Button show_action_mode;

    private ActionMode actionMode;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_test_action_mode;
    }

    @Override
    protected void init() {
    }

    @OnClick(R.id.show_action_mode)
    void showActionMode(){
        BaseActivity baseActivity = (BaseActivity) getActivity();
        actionMode = baseActivity.startSupportActionMode(amCallback);
    }
    @Override
    protected void setListeners() {

    }

    private ActionMode.Callback amCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.action_mode_menu,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.menu_add:
                    showToast("add");
                    mode.finish();
                    return true;
                case R.id.menu_delete:
                    showToast("delete");
                    mode.finish();
                    return true;
                case R.id.menu_save:
                    showToast("save");
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };
}
