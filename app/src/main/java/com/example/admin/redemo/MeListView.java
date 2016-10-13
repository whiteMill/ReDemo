package com.example.admin.redemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by admin on 2016/10/12.
 */
public class MeListView extends ListView {

    private int mScreenWidth; //屏幕宽度
    private int mDownX; //按下的X坐标值
    private int mDownY; //按下的Y坐标值
    private int mDeleteBtnWidth; //删除按钮的宽度

    private Boolean deleteIsSow=false; //删除按钮是否正在显示

    private ViewGroup mPointChilden; //当前

    private LinearLayout.LayoutParams layoutParams;

    public MeListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            //按下事件
            case MotionEvent.ACTION_DOWN:
                performActionDown(ev);
                break;
            //抬起事件
            case MotionEvent.ACTION_UP:
                performActionUp();
                break;
            //滑动事件
            case MotionEvent.ACTION_MOVE:
                return performActionMove(ev);
        }
        return super.onTouchEvent(ev);
    }

    public void allDeleteShow(){
        for(int i=0;i<getChildCount();i++){
            mPointChilden = (ViewGroup) getChildAt(i);
            //获取删除按钮的宽度
            mDeleteBtnWidth = mPointChilden.getChildAt(1).getLayoutParams().width;
            //Log.d("DDD",mPointChilden.getChildCount()+"");
            layoutParams = (LinearLayout.LayoutParams)mPointChilden.getChildAt(0).getLayoutParams();
            layoutParams.width = mScreenWidth;
            mPointChilden.getChildAt(0).setLayoutParams(layoutParams);
            deleteIsSow = true;
            layoutParams.leftMargin = -mDeleteBtnWidth;
            mPointChilden.getChildAt(0).setLayoutParams(layoutParams);
        }
    }

    public void cancaelShow(){
        for(int i=0;i<getChildCount();i++){
            mPointChilden = (ViewGroup) getChildAt(i);
            layoutParams = (LinearLayout.LayoutParams)mPointChilden.getChildAt(0).getLayoutParams();
            deleteIsSow = false;
            layoutParams.leftMargin =0;
            mPointChilden.getChildAt(0).setLayoutParams(layoutParams);
        }
    }

    private void performActionUp() {
        //偏移量是否大于删除按钮的一半  大于则显示button 否则不显示
        if (-layoutParams.leftMargin >= mDeleteBtnWidth / 2) {
            layoutParams.leftMargin = -mDeleteBtnWidth;
            deleteIsSow = true;
        } else {
            turnToNormal();
        }
        mPointChilden.getChildAt(0).setLayoutParams(layoutParams);
    }

    private boolean performActionMove(MotionEvent ev) {
        int nowX = (int) ev.getX();
        int nowY = (int) ev.getY();
        if (Math.abs(nowX - mDownX) > Math.abs(nowY - mDownY)) {
            //向左滑动
            if (nowX < mDownX) {
                int scroll = (mDownX - nowX) / 2;
                if (scroll > mDeleteBtnWidth) {
                    scroll = mDeleteBtnWidth;
                }
                Log.d("DDD",scroll+">>>");
                layoutParams.leftMargin = -scroll;
                mPointChilden.getChildAt(0).setLayoutParams(layoutParams);
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    private void performActionDown(MotionEvent ev) {
        if (deleteIsSow) {
            turnToNormal();
        }
        //按下的
        mDownX = (int) ev.getX();
        mDownY = (int) ev.getY();
        mPointChilden = (ViewGroup) getChildAt(pointToPosition(mDownX, mDownY) - getFirstVisiblePosition());
        //获取删除按钮的宽度
        mDeleteBtnWidth = mPointChilden.getChildAt(1).getLayoutParams().width;
        //Log.d("DDD",mPointChilden.getChildCount()+"");
        layoutParams = (LinearLayout.LayoutParams)mPointChilden.getChildAt(0).getLayoutParams();
        layoutParams.width = mScreenWidth;
        mPointChilden.getChildAt(0).setLayoutParams(layoutParams);
    }

    private void turnToNormal() {
        layoutParams.leftMargin = 0;
        mPointChilden.getChildAt(0).setLayoutParams(layoutParams);
        deleteIsSow = false;
    }

    public boolean canClick() {
        return !deleteIsSow;
    }
}
