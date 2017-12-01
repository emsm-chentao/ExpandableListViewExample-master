package com.hq.expandablelistviewexample.bean;

/**
 * Created by Administrator on 2017/11/30.
 */

public class CollocationChildChildBean {
    private String skuTitle;
    private String imageMd5;
    private int number;
    private boolean isCheckBox;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isCheckBox() {
        return isCheckBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.isCheckBox = checkBox;
    }

    public CollocationChildChildBean(String skuTitle, String imageMd5, int number, boolean isCheckBox) {
        this.skuTitle = skuTitle;
        this.imageMd5 = imageMd5;
        this.number = number;
        this.isCheckBox = isCheckBox;
    }

    public String getSkuTitle() {
        return skuTitle;
    }

    public void setSkuTitle(String skuTitle) {
        this.skuTitle = skuTitle;
    }

    public String getImageMd5() {
        return imageMd5;
    }

    public void setImageMd5(String imageMd5) {
        this.imageMd5 = imageMd5;
    }
}
