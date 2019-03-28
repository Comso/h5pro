package com.cosmo.common.refresh;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class VerticalSwipeRefreshLayout extends SwipeRefreshLayout {

    float lastX = 0;
    float lastY = 0;
    boolean isHorizontalMove = false;//是否在横向滚动

    public VerticalSwipeRefreshLayout(Context context) {
        super(context);
    }

    public VerticalSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            lastX = ev.getX();
            lastY = ev.getY();
            isHorizontalMove = false;
            return super.onInterceptTouchEvent(ev);
        }
        int x2 = (int) Math.abs(ev.getX() - lastX);
        int y2 = (int) Math.abs(ev.getY() - lastY);

        //滑动图片最小距离检查
        if (x2>y2){
            if (x2>=100){
                isHorizontalMove = true;
            }
            return false;
        }

        //是否移动图片(下拉刷新不处理)
        if (isHorizontalMove){
            return false;
        }
        boolean isok = super.onInterceptTouchEvent(ev);

        return isok;
    }
}
