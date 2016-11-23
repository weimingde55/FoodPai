package com.mobilephone.foodpai.myinterface;

/**
 * Created by qf on 2016/11/4.
 */
public class UnitOfHeat {
    private String unit;
    private String weight;
    private String calory;

    public UnitOfHeat() {
    }

    public UnitOfHeat(String unit, String weight, String calory) {
        this.unit = unit;
        this.weight = weight;
        this.calory = calory;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCalory() {
        return calory;
    }

    public void setCalory(String calory) {
        this.calory = calory;
    }

    @Override
    public String toString() {
        return "UnitOfHeat{" +
                "unit='" + unit + '\'' +
                ", weight='" + weight + '\'' +
                ", calory='" + calory + '\'' +
                '}';
    }
}
