package org.nyjsl.swallow.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import org.nyjsl.swallow.R;

import java.util.ArrayList;

/**
 * Created by weix01 on 2016-01-07.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<CardViewItem> items;

    public CardViewAdapter(Context mContext,ArrayList<CardViewItem> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_rv_cardview,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(null != items) {
            CardViewItem item = items.get(position);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.cardView.setElevation(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,item.elevation,mContext.getResources().getDisplayMetrics()));
            }
            holder.cardView.setRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, item.radius, mContext.getResources().getDisplayMetrics()));
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0:items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public View itemView;

        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.cardView = (CardView) itemView.findViewById(R.id.cardview);
        }
    }

    public static class CardViewItem {
        public int radius;
        public int elevation;
        public CardViewItem() {

        }

        public CardViewItem(int radius, int elevation) {
            this.radius = radius;
            this.elevation = elevation;
        }
    }

}
