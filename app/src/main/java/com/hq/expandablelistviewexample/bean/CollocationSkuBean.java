package com.hq.expandablelistviewexample.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 */

public class CollocationSkuBean {
    private String skuTitle;
    private String imageMd5;
    private boolean isShow;
    private boolean isCheckBox;
    private List<CollocationSkuChildBean> collocationSkuChildBeans;

    public boolean isCheckBox() {
        return isCheckBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.isCheckBox = checkBox;
    }


    public List<CollocationSkuChildBean> getCollocationSkuChildBeans() {
        return collocationSkuChildBeans;
    }

    public void setCollocationSkuChildBeans(List<CollocationSkuChildBean> collocationSkuChildBeans) {
        this.collocationSkuChildBeans = collocationSkuChildBeans;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public CollocationSkuBean(String skuTitle, String imageMd5, boolean isShow, List<CollocationSkuChildBean> collocationSkuChildBeans, boolean isCheckBox) {
        this.skuTitle = skuTitle;
        this.imageMd5 = imageMd5;
        this.isShow = isShow;
        this.collocationSkuChildBeans = collocationSkuChildBeans;
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
