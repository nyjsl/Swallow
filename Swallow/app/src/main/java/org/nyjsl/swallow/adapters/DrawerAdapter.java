package org.nyjsl.swallow.adapters;

import android.content.Context;
import android.graphics.Outline;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import org.nyjsl.swallow.R;

import java.util.ArrayList;

/**
 * Created by weix01 on 2016-01-05.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder>{


    private ArrayList<DrawerItem> items;
    private Context mContext;

    public DrawerAdapter(Context mContext,ArrayList<DrawerItem> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.drawer_rv_item,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(null != items){
            DrawerItem drawerItem = items.get(position);
            if(null != drawerItem){
                holder.img.setImageResource(drawerItem.imgId);
                holder.text.setText(drawerItem.text);
            }
        }
    }

    @Override
    public int getItemCount() {
        return null == items? 0:items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img;
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                img.setOutlineProvider(new ViewOutlineProvider() {
                    @Override
                    public void getOutline(View view, Outline outline) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            outline.setOval(0, 0, view.getWidth(), view.getHeight());
                        }
                    }
                });
                img.setClipToOutline(true);
            }
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public static class DrawerItem {
        public String text;
        public int imgId;
        public String fragmentClassName;

        public DrawerItem() {

        }

        public DrawerItem(String text, int imgId, String fragmentClassName) {
            this.text = text;
            this.imgId = imgId;
            this.fragmentClassName = fragmentClassName;
        }
    }
}
