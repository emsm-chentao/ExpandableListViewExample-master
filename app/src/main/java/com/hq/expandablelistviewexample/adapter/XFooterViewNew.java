package com.hq.expandablelistviewexample.adapter;


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hq.expandablelistviewexample.R;


/**
 * @author ct
 * @date 2013-10-08
 */
public class XFooterViewNew extends LinearLayout {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_LOADING = 2;

    private final int ROTATE_ANIM_DURATION = 180;

    private View mLayout;

    private ImageView mProgressBar;

    private TextView mHintView;

    // private ImageView mHintImage;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;
    private AnimationDrawable animationDrawable = null;
    private int mState = STATE_NORMAL;

    public XFooterViewNew(Context context) {
        super(context);
        initView(context);
    }

    public XFooterViewNew(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mLayout = LayoutInflater.from(context).inflate(R.layout.vw_footer, null);
        mLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(mLayout);

        mProgressBar = (ImageView) mLayout.findViewById(R.id.footer_progressbar);
        mHintView = (TextView) mLayout.findViewById(R.id.footer_hint_text);
        mHintView.setVisibility(this.IsHide ? View.GONE : View.VISIBLE);
//        mHintImage = (ImageView) mLayout.findViewById(R.id.footer_arrow);

        mRotateUpAnim = new RotateAnimation(0.0f, 180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);

        mRotateDownAnim = new RotateAnimation(180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
        mProgressBar.setImageResource(R.drawable.loading_animation);
        animationDrawable = (AnimationDrawable) mProgressBar.getDrawable();
        if (null != animationDrawable) {
            animationDrawable.setOneShot(false);
        }
    }

    Boolean IsHide = true;

    /**
     * 自动加载更多  是否显示提示文字
     *
     * @param IsHide
     */
    public void setTextIsHide(Boolean IsHide) {
        this.IsHide = IsHide;
    }

    public void setmHintView(Boolean IsShow) {
        mHintView.setVisibility(IsShow ? View.VISIBLE : View.GONE);
    }

    /**
     * Set footer view state
     *
     * @param state
     * @see #STATE_LOADING
     * @see #STATE_NORMAL
     * @see #STATE_READY
     */
    public void setState(int state) {
        if (state == mState) return;

        if (state == STATE_LOADING) {
//            mHintImage.clearAnimation();
//            mHintImage.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
            mHintView.setVisibility(View.GONE);
            if (animationDrawable != null) {
                animationDrawable.start();
            }
        } else {
            mHintView.setVisibility(this.IsHide ? View.GONE : View.VISIBLE);
//            mHintImage.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
            if (animationDrawable != null) {
                animationDrawable.stop();
            }
        }

        switch (state) {
            case STATE_NORMAL:
//                if (mState == STATE_READY) {
//                    mHintImage.startAnimation(mRotateDownAnim);
//                }
//                if (mState == STATE_LOADING) {
//                    mHintImage.clearAnimation();
//                }
                mHintView.setText(R.string.xlistview_footer_hint_normal);
                break;

            case STATE_READY:
                if (mState != STATE_READY) {
//                    mHintImage.clearAnimation();
//                    mHintImage.startAnimation(mRotateUpAnim);
                    mHintView.setText(R.string.xlistview_footer_hint_ready);
                }
                break;

            case STATE_LOADING:
                break;
        }
        mState = state;
    }

    /**
     * Set footer view bottom margin.
     *
     * @param margin
     */
    public void setBottomMargin(int margin) {
        if (margin < 0) return;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mLayout.getLayoutParams();
        lp.bottomMargin = margin;
        mLayout.setLayoutParams(lp);
    }

    /**
     * Get footer view bottom margin.
     *
     * @return
     */
    public int getBottomMargin() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mLayout.getLayoutParams();
        return lp.bottomMargin;
    }

    /**
     * hide footer when disable pull load more
     */
    public void hide() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mLayout.getLayoutParams();
        lp.height = 0;
        mLayout.setLayoutParams(lp);
    }

    /**
     * show footer
     */
    public void show() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mLayout.getLayoutParams();
        lp.height = LayoutParams.WRAP_CONTENT;
        mLayout.setLayoutParams(lp);
    }

}
