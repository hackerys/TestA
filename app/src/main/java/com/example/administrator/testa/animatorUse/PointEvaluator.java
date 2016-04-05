package com.example.administrator.testa.animatorUse;

import android.animation.TypeEvaluator;

/**
 * Created Jansen on 2016/1/20.
 */
public class PointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Pointer startPointer = (Pointer) startValue;
        Pointer endPointer = (Pointer) endValue;
        float x = startPointer.getX() + fraction * (endPointer.getX() - startPointer.getX());
        float y = startPointer.getY() + fraction * (endPointer.getY() - startPointer.getY());
        return new Pointer(x, y);
    }
}
