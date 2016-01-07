package org.nyjsl.swallow.test;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.nyjsl.swallow.R;
import org.nyjsl.swallow.adapters.CardViewAdapter;
import org.nyjsl.swallow.ui.fragment.BaseFragement;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by weix01 on 2016-01-07.
 */
public class TestCardViewFragment extends BaseFragement{

    @Bind(R.id.test_card_view_rc) RecyclerView rc;

    public static TestCardViewFragment newInstance() {
        TestCardViewFragment fragment = new TestCardViewFragment();
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_test_cardview;
    }

    @Override
    protected void init() {
        initRecyclerView();
    }


    private void initRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(mContext,3);
        rc.setLayoutManager(manager);
        ArrayList<CardViewAdapter.CardViewItem> items = new ArrayList<>();
        for(int i=0;i<20;i++){
            CardViewAdapter.CardViewItem  item  = new CardViewAdapter.CardViewItem(i,i*2);
            items.add(item);
        }
        CardViewAdapter adapter = new CardViewAdapter(mContext,items);
        rc.setAdapter(adapter);
    }
    @Override
    protected void setListeners() {

    }
}
