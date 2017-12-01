package com.hq.expandablelistviewexample.adapter;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.hq.expandablelistviewexample.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/**
 * @author ct
 * @date 2013-10-08
 * 功能介绍： 支持自动下拉刷新  自动加载更多
 */
public class XListViewNew extends ExpandableListView implements OnScrollListener {
    private ImageView header_progressbar;
    private final static int SCROLL_BACK_HEADER = 0;
    private final static int SCROLL_BACK_FOOTER = 1;

    private final static int SCROLL_DURATION = 400;

    // when pull up >= 50px
    private final static int PULL_LOAD_MORE_DELTA = 50;

    // support iOS like pull
    private final static float OFFSET_RADIO = 1.8f;

    private float mLastY = -1;

    // used for scroll back
    private Scroller mScroller;
    // user's scroll listener
    private OnScrollListener mScrollListener;
    // for mScroller, scroll back from header or footer.
    private int mScrollBack;

    // the interface to trigger refresh and load more.
    private IXListViewListener mListener;

    private XHeaderViewNew mHeader;
    // header view content, use it to calculate the Header's height. And hide it
    // when disable pull refresh.
    private RelativeLayout mHeaderContent;
    private TextView mHeaderTime;
    private int mHeaderHeight;

    private LinearLayout mFooterLayout;
    private XFooterViewNew mFooterView;
    private boolean mIsFooterReady = false;

    private boolean mEnablePullRefresh = true;//设置下拉刷新是否可用
    private boolean mPullRefreshing = false;

    private boolean mEnablePullLoad = true;//设置上拉加载是否可用
    private boolean mEnableAutoLoad = true;//设置是否 自动  上拉加载
    private boolean mPullLoading = false;

    // total list items, used to detect is at the bottom of ListView
    private int mTotalItemCount;

    public XListViewNew(Context context) {
        super(context);
        initWithContext(context);
    }

    public XListViewNew(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }

    public XListViewNew(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context);
    }

    private void initWithContext(Context context) {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        super.setOnScrollListener(this);

        // init header view
        mHeader = new XHeaderViewNew(context);
        mHeaderContent = (RelativeLayout) mHeader.findViewById(R.id.header_content);
        header_progressbar = (ImageView) mHeader.findViewById(R.id.header_progressbar);
        mHeaderTime = (TextView) mHeader.findViewById(R.id.header_hint_time);
        addHeaderView(mHeader);

        // init footer view
        mFooterView = new XFooterViewNew(context);
        mFooterView.setTextIsHide(mEnableAutoLoad);
        mFooterLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        mFooterLayout.addView(mFooterView, params);

        // init header height
        ViewTreeObserver observer = mHeader.getViewTreeObserver();
        if (null != observer) {
            observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation")
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onGlobalLayout() {
                    mHeaderHeight = mHeaderContent.getHeight();
                    ViewTreeObserver observer = getViewTreeObserver();

                    if (null != observer) {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            observer.removeGlobalOnLayoutListener(this);
                        } else {
                            observer.removeOnGlobalLayoutListener(this);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        // make sure XFooterView is the last footer view, and only add once.
        if (!mIsFooterReady) {
            mIsFooterReady = true;
            addFooterView(mFooterLayout);
        }

        super.setAdapter(adapter);
    }

    /**
     * 获取下拉 是否正在 刷新
     *
     * @return
     */
    public boolean getPullRefreshing() {
        return mPullRefreshing;
    }

    /**
     * Enable or disable pull down refresh feature.
     *
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;

        // disable, hide the content
        mHeaderContent.setVisibility(enable ? View.VISIBLE : View.GONE);
    }


    /**
     * Enable or disable pull up load more feature.
     *
     * @param enable
     */
    public void setPullLoadEnable(boolean enable) {
        mEnablePullLoad = enable;
        if (!mEnablePullLoad) {
            mFooterView.setBottomMargin(0);
            mFooterView.hide();
            mFooterView.setPadding(0, 0, 0, 0);
            mFooterView.setOnClickListener(null);

        } else {
            mPullLoading = false;
            mFooterView.setPadding(0, 0, 0, 0);
            mFooterView.show();
            mFooterView.setState(XFooterViewNew.STATE_NORMAL);
            // both "pull up" and "click" will invoke load more.
            mFooterView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mPullLoading) {
//                        Log.e("CCCCTTTT", "onClick---------------mPullLoading");
                        startLoadMore();
                    }
                }
            });
        }
    }

    /**
     * Enable or disable auto load more feature when scroll to bottom.
     *
     * @param enable
     */
    public void setAutoLoadEnable(boolean enable) {
        mEnableAutoLoad = enable;
        if (!mEnableAutoLoad) {
            mFooterView.setmHintView(true);
        }
    }

    /**
     * Stop refresh, reset header view.
     */
    public void stopRefresh() {
        if (mPullRefreshing) {
            mPullRefreshing = false;
            resetHeaderHeight();
        }
    }

    /**
     * Stop load more, reset footer view.
     */
    public void stopLoadMore() {
        if (mPullLoading) {
            mPullLoading = false;
            mFooterView.setState(XFooterViewNew.STATE_NORMAL);
        }
    }

    /**
     * Set last refresh time
     */
    public void setRefreshTime() {
        mHeaderTime.setText(new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date()));
    }

    /**
     * Set listener.
     *
     * @param listener
     */
    public void setXListViewListener(IXListViewListener listener) {
        mListener = listener;
    }

    /**
     * 自动刷新
     */
    /*public void autoRefresh() {
        mHeader.setVisibleHeight(mHeaderHeight);
		setAutoRefresh();
	}*/

    /**
     * 自定义自动刷新高度
     */
    public void autoRefresh(int HeaderViewHeigth) {
        this.setSelection(0);//自动刷新 自动指定到top
        progressbarScale(1.0f);
        mHeader.setVisibleHeight(HeaderViewHeigth);
        setAutoRefresh();
    }

    private void setAutoRefresh() {
        if (mEnablePullRefresh && !mPullRefreshing) {
            // update the arrow image not refreshing
            if (mHeader.getVisibleHeight() > mHeaderHeight) {
                mHeader.setState(XHeaderViewNew.STATE_READY);
            } else {
                mHeader.setState(XHeaderViewNew.STATE_NORMAL);
            }
        }

        mPullRefreshing = true;
        mHeader.setState(XHeaderViewNew.STATE_REFRESHING);
        refresh();
    }

    private void invokeOnScrolling() {
        if (mScrollListener instanceof OnXScrollListener) {
            OnXScrollListener listener = (OnXScrollListener) mScrollListener;
            listener.onXScrolling(this);
        }
    }

    private void updateHeaderHeight(float delta) {
        mHeader.setVisibleHeight((int) delta + mHeader.getVisibleHeight());
        if (mEnablePullRefresh && !mPullRefreshing) {
            // update the arrow image unrefreshing
            if (mHeader.getVisibleHeight() > mHeaderHeight) {
                mHeader.setState(XHeaderViewNew.STATE_READY);
            } else {
                mHeader.setState(XHeaderViewNew.STATE_NORMAL);
            }
        }

        // scroll to top each time
        setSelection(0);
    }

    private void resetHeaderHeight() {
        int height = mHeader.getVisibleHeight();
        //LG.e("resetHeaderHeight() ===height===="+height+""+"");LG.e("resetHeaderHeight() ====mHeaderHeight=="+mHeaderHeight+""+"");
//		if (height == 0)
//			return;

        // refreshing and header isn't shown fully. do nothing.
        if (mPullRefreshing && height <= mHeaderHeight)
            return;

        // default: scroll back to dismiss header.
        int finalHeight = 0;
        // is refreshing, just scroll back to show all the header.
        if (mPullRefreshing && height > mHeaderHeight) {
            finalHeight = mHeaderHeight;
        }

        mScrollBack = SCROLL_BACK_HEADER;
        mScroller.startScroll(0, height, 0, finalHeight - height,
                SCROLL_DURATION);

        // trigger computeScroll
        invalidate();
    }

    private void updateFooterHeight(float delta) {
        int height = mFooterView.getBottomMargin() + (int) delta;

        if (mEnablePullLoad && !mPullLoading) {
            if (height > PULL_LOAD_MORE_DELTA) {
                // height enough to invoke load more.
                mFooterView.setState(XFooterViewNew.STATE_READY);
            } else {
                mFooterView.setState(XFooterViewNew.STATE_NORMAL);
            }
        }

        mFooterView.setBottomMargin(height);

        // scroll to bottom
        // setSelection(mTotalItemCount - 1);
    }

    private void resetFooterHeight() {
        int bottomMargin = mFooterView.getBottomMargin();

        if (bottomMargin > 0) {
            mScrollBack = SCROLL_BACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
                    SCROLL_DURATION);
            invalidate();
        }
    }

    private void startLoadMore() {
        mPullLoading = true;
        mFooterView.setTextIsHide(mEnableAutoLoad);
        mFooterView.setState(XFooterViewNew.STATE_LOADING);
        loadMore();
    }

    //放大缩小控件
    private void progressbarScale(float scale) {
        scale = 0.1f + 0.9f * scale;
        if (scale > 1.0f) {
            scale = 1.0f;
        }
        ViewCompat.setScaleX(header_progressbar, scale);
//        ViewCompat.setPivotY(header_progressbar, header_progressbar.getHeight());//左到右
        ViewCompat.setScaleY(header_progressbar, scale);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (getFirstVisiblePosition() == 0 && (mHeader.getVisibleHeight() > 0 || deltaY > 0)) {
                    // the first item is showing, header has shown or pull down.
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                    progressbarScale(mHeader.getVisibleHeight() / mLastY * 5.0f);
                    invokeOnScrolling();
                } else if (getLastVisiblePosition() == mTotalItemCount - 1 && (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
                    // last item, already pulled up or want to pull up.
                    updateFooterHeight(-deltaY / OFFSET_RADIO);
                }
                break;
            case MotionEvent.ACTION_UP:
                // reset
                mLastY = -1;
                if (getFirstVisiblePosition() == 0) {
                    //	LG.e("getFirstVisiblePosition() == 0==========="+getFirstVisiblePosition());
                    // invoke refresh

                    if (mEnablePullRefresh && mHeader.getVisibleHeight() > mHeaderHeight && !mPullRefreshing) {
                        mPullRefreshing = true;
//                        Log.e("CCCCTTTT", "---------------mEnablePullRefresh");
                        mHeader.setState(XHeaderViewNew.STATE_REFRESHING);
                        refresh();
                        progressbarScale(1.0f);
                    }
                    resetHeaderHeight();
                } else if (getLastVisiblePosition() == mTotalItemCount - 1) {
                    // invoke load more.
                    if (mEnablePullLoad && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA && !mPullLoading) {
//                        Log.e("CCCCTTTT", "onTouchEvent---------------mPullLoading");
                        startLoadMore();
                    }
                    resetFooterHeight();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /* 实现动画效果  一直computeScroll方法
     */
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {//判断mScroller滚动是否完成
            if (mScrollBack == SCROLL_BACK_HEADER) {
                mHeader.setVisibleHeight(mScroller.getCurrY());
            } else {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }
            postInvalidate();
            invokeOnScrolling();
        }
        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    /*滚动到停止
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            if (mEnableAutoLoad && getLastVisiblePosition() == getCount() - 1 && !mPullLoading) {
//                Log.e("CCCCTTTT", "onScrollStateChanged---------------mPullLoading");
                startLoadMore();
            }
        }
    }

    /*滚动  一直触发
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // send to user's listener
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }



    private void refresh() {
        if (mEnablePullRefresh && null != mListener) {
            mListener.onRefresh();
        }
    }

    private void loadMore() {
        if (mEnablePullLoad && null != mListener) {
            mListener.onLoadMore();
        }
    }

    /**
     * You can listen ListView.OnScrollListener or this one. it will invoke
     * onXScrolling when header/footer scroll back.
     */
    public interface OnXScrollListener extends OnScrollListener {
        void onXScrolling(View view);
    }

    /**
     * Implements this interface to get refresh/load more event.
     *
     * @author markmjw
     */
    public interface IXListViewListener {
        void onRefresh();

        void onLoadMore();
    }
}
