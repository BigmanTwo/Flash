package com.example.administrator.flash.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 动画的工具类
 *
 * Created by KingHua on 2017/2/23.
 */
public class AnimationUtils {
    /***
     *创建动画层
     * @param activity
     * @return
     */
        public static ViewGroup createAnimLayout(Activity activity){
            ViewGroup   rootView= (ViewGroup) activity.getWindow().getDecorView();
            LinearLayout animLayout=new LinearLayout(activity);
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            animLayout.setLayoutParams(lp);
            animLayout.setBackgroundResource(android.R.color.transparent);
            rootView.addView(animLayout);
            return rootView;
        }

    /***
     * 增添动画
     * @param activity
     * @param startView 起始view
     * @param targetView  目标view
     * @param addTaskListener
     */
    public static void setAddTaskAnimation(Activity activity, View startView, View targetView, final AddTaskAnimotionListener addTaskListener){
//        创建遮罩动画层
        ViewGroup animMasklayout=createAnimLayout(activity);
        final ImageView imageView=new ImageView(activity);
        animMasklayout.addView(imageView);
//        创建animation
        int[] startLocArray=new int[2];
        int[] endLocArray=new int[2];
        startView.getLocationInWindow(startLocArray);
        targetView.getLocationInWindow(endLocArray);
        ViewGroup.LayoutParams startParams=startView.getLayoutParams();
        ViewGroup.LayoutParams targetParams=targetView.getLayoutParams();
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(
                startParams.width,startParams.height  );
        lp.leftMargin=startLocArray[0];
        lp.topMargin=startLocArray[1];
        imageView.setLayoutParams(lp);
//        设置遮罩层背景
        if (startView!=null&&startView instanceof ImageView){
            ImageView iv= (ImageView) startView;
            iv.setImageDrawable(iv.getDrawable()==null?null:iv.getDrawable());
        }
//计算位移
        int xOffset=endLocArray[0]-startLocArray[0]+targetParams.width/2;//动画位移的x坐标
        int yOffset=startLocArray[1]-startLocArray[1]+targetParams.height/2;//动画唯一的y坐标
        TranslateAnimation translateAnimationX=new TranslateAnimation(0,xOffset,0,0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);
        translateAnimationX.setFillAfter(true);
        TranslateAnimation translateAnimationY=new TranslateAnimation(0,yOffset,0,0);
        translateAnimationY.setInterpolator(new LinearInterpolator());
        translateAnimationY.setRepeatCount(0);
        translateAnimationY.setFillAfter(true);
        ScaleAnimation scaleAnimation=new ScaleAnimation(1.0f,0.2f,1.0f,0.2f);
        scaleAnimation.setInterpolator(new LinearInterpolator());
        scaleAnimation.setFillAfter(true);
        AnimationSet animationSet=new AnimationSet(false);
        animationSet.setFillAfter(false);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimationX);
        animationSet.addAnimation(translateAnimationY);
        animationSet.setDuration(800);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (addTaskListener!=null){
                    addTaskListener.onAnimationStart(animation);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.setVisibility(View.GONE);
                if (addTaskListener!=null){
                    addTaskListener.onAnimationEnd(animation);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /***
     * 购物车动画的监听
     */
    public interface AddTaskAnimotionListener{
        void onAnimationStart(Animation animation);
        void onAnimationEnd(Animation animation);
    }

}
