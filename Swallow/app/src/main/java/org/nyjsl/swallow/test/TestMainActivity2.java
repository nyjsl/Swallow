package org.nyjsl.swallow.test;

import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.nyjsl.swallow.R;
import org.nyjsl.swallow.adapters.DrawerAdapter;
import org.nyjsl.swallow.ui.BaseActivity;
import org.nyjsl.swallow.utils.NetWorkUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class TestMainActivity2 extends BaseActivity {


    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.drawerlayout) DrawerLayout drawerLayout;

    @Bind(R.id.left_drawer_rc) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        initDrawer();
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DrawerAdapter.DividerItemDecoration(mContext,LinearLayoutManager.VERTICAL));
        ArrayList< DrawerAdapter.DrawerItem> items;
        items = new ArrayList<>();
        items.add(new DrawerAdapter.DrawerItem("awesome",android.R.drawable.ic_menu_my_calendar,""));
        items.add(new DrawerAdapter.DrawerItem("test",android.R.drawable.ic_menu_myplaces,""));
        items.add(new DrawerAdapter.DrawerItem("test",android.R.drawable.ic_menu_upload,""));
        recyclerView.setAdapter(new DrawerAdapter(mContext,items));
    }


    private void initDrawer() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,0,0){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
                syncState();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                syncState();
            }
        };
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

    }

    @OnClick(R.id.fab)
    void fabClicked() {
        Snackbar.make(fab, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_test_main2;
    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void onConnect(NetWorkUtil.NetType type) {

    }

    @Override
    public void onDisConnect() {

    }

}
