package com.hq.expandablelistviewexample.adapter;


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
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
public class XHeaderViewNew extends LinearLayout {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;

    private final int ROTATE_ANIM_DURATION = 180;

    private LinearLayout mContainer;

//	private ImageView mArrowImageView;

    private ImageView mProgressBar;

    private TextView mHintTextView;

    private int mState = STATE_NORMAL;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;
    private AnimationDrawable animationDrawable = null;
    private boolean mIsFirst;

    public XHeaderViewNew(Context context) {
        super(context);
        initView(context);
    }

    public XHeaderViewNew(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        // 初始情况，设置下拉刷新view高度为0
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.vw_header, null);
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);

//		mArrowImageView = (ImageView) findViewById(R.id.header_arrow);
        mHintTextView = (TextView) findViewById(R.id.header_hint_text);
        mProgressBar = (ImageView) findViewById(R.id.header_progressbar);

        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);

        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);//(UIUtils.getIdentifier( "loading_animation_car","anim")
        mRotateDownAnim.setFillAfter(true);//;
        mProgressBar.setImageResource(R.drawable.loading_animation_car);
        animationDrawable = (AnimationDrawable) mProgressBar.getDrawable();
        if (animationDrawable != null) {
            animationDrawable.setOneShot(false);
        }
    }

    public void setState(int state) {
        if (state == mState && mIsFirst) {
            mIsFirst = true;
            return;
        }

        if (state == STATE_REFRESHING) {
            // show progress
//			mArrowImageView.clearAnimation();
//			mArrowImageView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
            if (animationDrawable != null) {
                animationDrawable.start();
            }
        } else {
            // show arrow image
//			mArrowImageView.setVisibility(View.VISIBLE);
//			mProgressBar.setVisibility(View.INVISIBLE);
            if (animationDrawable != null) {
                animationDrawable.stop();
            }
        }

        switch (state) {
            case STATE_NORMAL:
                if (mState == STATE_READY) {
//					mArrowImageView.startAnimation(mRotateDownAnim);
                }

                if (mState == STATE_REFRESHING) {
//					mArrowImageView.clearAnimation();
                }

                mHintTextView.setText(R.string.xlistview_header_hint_normal);
                break;

            case STATE_READY:
                if (mState != STATE_READY) {
//					mArrowImageView.clearAnimation();
//					mArrowImageView.startAnimation(mRotateUpAnim);
                    mHintTextView.setText(R.string.xlistview_header_hint_ready);
                }
                break;

            case STATE_REFRESHING:
                mHintTextView.setText(R.string.xlistview_header_hint_loading);
                break;

            default:
                break;
        }

        mState = state;
    }

    /**
     * Set the header view visible height.
     *
     * @param height
     */
    public void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    /**
     * Get the header view visible height.
     *
     * @return
     */
    public int getVisibleHeight() {
        return mContainer.getHeight();
    }

}
