package com.example.administrator.testa.animatorUse;

import android.animation.TimeInterpolator;

/**
 * Created Jansen on 2016/1/21.
 */
public class DeccelerateAceelerateInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        if (input < 0.5) {
            return (float) (Math.sin(input * Math.PI) / 2);
        } else {
            return (float) (2 - Math.sin(input * Math.PI) )/ 2;
        }
    }
}
