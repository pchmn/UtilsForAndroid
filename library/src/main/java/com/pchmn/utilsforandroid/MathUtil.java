package com.pchmn.utilsforandroid;


public class MathUtil {

    /**
     * Round a float value by a given precision (number after the decimal point)
     *
     * @param value the value to round
     * @param precision the precision : the number after the decimal point
     * @return rounded value
     */
    public static float round(float value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (float) Math.round(value * scale) / scale;
    }
}
