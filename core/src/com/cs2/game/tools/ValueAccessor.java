package com.cs2.game.tools;

import aurelienribon.tweenengine.TweenAccessor;

public class ValueAccessor implements TweenAccessor<Value> {


    @Override
    public int getValues(Value target, int tweenType, float[] returnValues) {
        returnValues[0]=target.getVal();
        return 1;
    }

    @Override
    public void setValues(Value target, int tweenType, float[] newValues) {
    target.setVal(newValues[0]);
    }
}
