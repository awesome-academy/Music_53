package com.trantan.music53.utils;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class ImageUtil {
    private static final long DURATION = 20000;

    public static void rotateImage(ImageView imageView, boolean isRotate) {
        RotateAnimation rotateAnimation = new RotateAnimation(0f, 359f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(DURATION);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        if (isRotate && imageView.getAnimation() == null) {
            imageView.startAnimation(rotateAnimation);
        } else imageView.setAnimation(null);
    }
}
