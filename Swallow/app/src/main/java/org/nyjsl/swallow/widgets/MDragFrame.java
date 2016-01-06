package org.nyjsl.swallow.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Outline;
import android.os.Build;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;

import org.nyjsl.swallow.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weix01 on 2015-12-31.
 */
public class MDragFrame extends FrameLayout {


    private ViewDragHelper viewDragHelper;

    private View circle;

    private List<View> dragViews;

    public MDragFrame(Context context) {
        this(context,null);
    }

    public MDragFrame(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MDragFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MDragFrame(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setUp(){
        circle = findViewById(R.id.circle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circle.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        outline.setOval(0, 0, view.getWidth(), view.getHeight());
                    }
                }
            });
            circle.setClipToOutline(true);
        }
        if(null == dragViews){
            dragViews = new ArrayList<>();
        }
        dragViews.add(circle);
        viewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return dragViews.contains(child);
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }
        });

    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            viewDragHelper.cancel();
            return false;
        }
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        viewDragHelper.processTouchEvent(ev);
        return true;
    }
}
