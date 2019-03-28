package com.cosmo.common.refresh;

import android.content.Context;
import android.util.AttributeSet;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class CommonRefreshView extends VerticalSwipeRefreshLayout {

    public CommonRefreshView(Context context) {
        super(context);
        init();
    }

    public CommonRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 默认刷新样式初始化
     */
    private void init() {

    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    OnRefreshListener onRefreshListener;

    /**
     * 下拉刷新监听
     *
     * @param onRefreshListener {@link OnRefreshListener}
     */
    public void addOnRefreshListener(final OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
        super.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshListener.onRefresh();
            }
        });
    }

    /**
     * 自动刷新（值做动画）
     */
    public void autoRefresh() {
        super.setRefreshing(true);
    }

    /**
     * 自动刷新(动画数据一起加载)
     */
    public void autoRefreshData() {
        super.setRefreshing(true);
        if (onRefreshListener != null) {
            onRefreshListener.onRefresh();
        }
    }

    /**
     * 完成刷新
     */
    public void refreshComplete() {
        super.setRefreshing(false);
    }
}
