package com.answer.library.color;

import android.view.View;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.animation.DecelerateInterpolator;

public class AnimationLibrary {
    public static ObjectAnimator AnimationOriginaux(View view, String direction, int time, float... parameter) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, direction, parameter);
        objectAnimator.setDuration(time);

        objectAnimator.start();
        return objectAnimator;
    }

    /*水平平移(左右)*/
    public static ObjectAnimator Transparentanimation(View view, int time, float... parameter) {
        return AnimationOriginaux(view, "translationX", time, parameter);
    }

    /*垂直平移(上下)*/
    public static ObjectAnimator Displacementanimation(View view, int time, float... parameter) {
        return AnimationOriginaux(view, "translationY", time, parameter);
    }

    /*缩放*/
    public static AnimatorSet Zoomanimation(View view, int time, float... parameter) {
        AnimatorSet animatorSet = new AnimatorSet(); // 组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", parameter);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", parameter);

        animatorSet.setDuration(time);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(scaleX).with(scaleY); // 两个动画同时开始
        animatorSet.start();

        return animatorSet;
    }

    /*缩放x轴*/
    public static ObjectAnimator ZoomXanimation(View view, int time, float... parameter) {
        return AnimationOriginaux(view, "scaleX", time, parameter);
    }

    /*缩放Y轴*/
    public static ObjectAnimator ZoomYanimation(View view, int time, float... parameter) {
        return AnimationOriginaux(view, "scaleY", time, parameter);
    }

    /*旋转*/
    public static ObjectAnimator rotationanimation(View view, int time, float... parameter) {
        return AnimationOriginaux(view, "rotation", time, parameter);
    }

    /*旋转*/
    public static ObjectAnimator rotationanimationX(View view, int time, float... parameter) {
        return AnimationOriginaux(view, "rotationX", time, parameter);
    }

    /*旋转*/
    public static void rotationanimationY(View view, int time, float... parameter) {
        AnimationOriginaux(view, "rotationY", time, parameter);
    }

    /*淡入*/
    public static void Alphaanimation(View view, int time, float... parameter) {
        AnimationOriginaux(view, "alpha", time, parameter);
    }

    /*渐渐改变背景颜色*/
    public static void Colorchangeanimation(View view, int time, int... color) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(view, "backgroundColor", color);
        objectAnimator.setDuration(time);
        objectAnimator.setEvaluator(new android.animation.ArgbEvaluator());
        objectAnimator.start();
    }
}
