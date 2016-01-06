package org.nyjsl.swallow.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(null != items){
            DrawerItem drawerItem = items.get(position);
            if(null != drawerItem){
                holder.img.setImageResource(drawerItem.imgId);
                holder.text.setText(drawerItem.text);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(v ,""+position,Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return null == items? 0:items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public View itemView;
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
            this.itemView = itemView;
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

    public static class DividerItemDecoration extends RecyclerView.ItemDecoration{
        /**
         * RecyclerView 的布局方向
         */
        private  int mOrientation = LinearLayoutManager.VERTICAL;
        /**
         * item 之间分割线的size 默认为1
         */
        private int mItemSize = 1;
        /**
         * 绘制item分割线的画笔
         */
        private Paint mPaint ;

        public DividerItemDecoration(Context mContext,int mOrientation) {
            if(mOrientation!=LinearLayoutManager.VERTICAL &&mOrientation!=LinearLayoutManager.HORIZONTAL)
                throw new IllegalArgumentException("orientation should be VERTICAL or HORIZONTAL");
            this.mOrientation = mOrientation;
            mItemSize = (int) TypedValue.applyDimension(mItemSize,TypedValue.COMPLEX_UNIT_DIP,mContext.getResources().getDisplayMetrics());
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(Color.GRAY);
            mPaint.setStyle(Paint.Style.FILL);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            if(mOrientation == LinearLayoutManager.VERTICAL){
                drawVertical(c,parent) ;
            }else {
                drawHorizontal(c,parent) ;
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if(mOrientation == LinearLayoutManager.VERTICAL){
                outRect.set(0,0,0,mItemSize);
            }else {
                outRect.set(0,0,mItemSize,0);
            }
        }


        private void drawVertical(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getMeasuredWidth() - parent.getPaddingRight() ;
            int childCount = parent.getChildCount();
            for(int i=0;i<childCount;i++) {
                final View childAt = parent.getChildAt(i);
                RecyclerView.LayoutParams layouParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
                final int top = childAt.getBottom()+layouParams.bottomMargin;
                final int bottom = top+mItemSize;
                c.drawRect(left,top,right,bottom,mPaint);
            }
        }

        private void drawHorizontal(Canvas c, RecyclerView parent) {
            final int top = parent.getPaddingTop() ;
            final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom() ;
            final int childSize = parent.getChildCount() ;
            for(int i = 0 ; i < childSize ; i ++){
                final View child = parent.getChildAt( i ) ;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int left = child.getRight() + layoutParams.rightMargin ;
                final int right = left + mItemSize ;
                c.drawRect(left,top,right,bottom,mPaint);
            }
        }
    }
}
