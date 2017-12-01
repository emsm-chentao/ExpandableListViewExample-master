package com.hq.expandablelistviewexample.bean;

import java.math.BigDecimal;
import java.util.List;

public class CollocationBean {
    private BigDecimal totalPrice;
    private String name;
    private List<CollocationChildBean> collocationSkuDoList;
    private BigDecimal discountFee;
    private boolean isCheckBox;

    public boolean isCheckBox() {
        return isCheckBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.isCheckBox = checkBox;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CollocationChildBean> getCollocationSkuDoList() {
        return collocationSkuDoList;
    }

    public void setCollocationSkuDoList(List<CollocationChildBean> collocationSkuDoList) {
        this.collocationSkuDoList = collocationSkuDoList;
    }

    public BigDecimal getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(BigDecimal discountFee) {
        this.discountFee = discountFee;
    }
}
