package com.mobilephone.foodpai.util;

/**
 * 热量单位换算 -- 千卡转换成千焦
 * Created by qf on 2016/11/4.
 */
public class UnitUtil {

    public static String conversionKilo(String calory) {
        String kilo = "";
        float caloryFloat = Float.parseFloat(calory);
        float kiloFloat = caloryFloat * 4.182f;
        int kiloInt = (int) ((kiloFloat * 10 + 5) / 10);
        kilo = Integer.toString(kiloInt);
        return kilo;
    }

    /**
     * 根据提供的“每100克”的营养元素的数据，换算出其他单位的营养元素数据
     *
     * @param calory       “每100克”的千卡数
     * @param switchCalory 其他单位的千卡数
     * @param ingredient   “每100克”的其他营养元素
     * @return float   其他单位的其他营养元素
     */
    public static String conversionUnit(String calory, String switchCalory, String ingredient) {
        String switchIngredient = "";
        float switchFloat = 0.0f;
        if (calory.length() != 0 && switchCalory.length() != 0 && ingredient.length() != 0) {
            float caloryFloat = Float.parseFloat(calory);
            float sCaloryFloat = Float.parseFloat(switchCalory);
            float ingredientFloat = Float.parseFloat(ingredient);
            switchFloat = sCaloryFloat / caloryFloat * ingredientFloat;
            switchFloat = ((float) (Math.round(switchFloat * 1000) / 1000));    //保留两位有效数字
            switchIngredient = Float.toString(switchFloat);
        }
        return switchIngredient;
    }
}
