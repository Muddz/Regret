package com.muddzdev.regret.demo;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

class Utils {
    static float toSP(Context context, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, context.getResources().getDisplayMetrics());
    }

}
