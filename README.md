___
##一、简介
高仿京东购物车　ExpandableListView 来实现  并且ExpandableListView支持刷新　和自动刷新

##二、使用
> 引用, AndroidStudio使用Gradle构建添加依赖（推荐）
* compile 'com.facebook.fresco:fresco:0.9.0'
* compile 'com.zhy:autolayout:1.4.5'

> 使用到的第三方框架
* [Fresco](https://github.com/facebook/fresco)  图片加载缓存框架
* [AndroidAutoLayout](https://github.com/hongyangAndroid/AndroidAutoLayout)   屏幕适配框架

Demo来自某个线上电商app的页面, 使用前先看看效果图
![demo演示](https://github.com/hexianqiao3755/ExpandableListViewExample/blob/master/iamge/WeChat_20171201143242.mp4)
![demo演示](https://github.com/chentao753951/ExpandableListViewExample-master/blob/master/iamge01/001.jpg)
![demo演示](https://github.com/chentao753951/ExpandableListViewExample-master/blob/master/iamge01/002.jpg)
![demo演示](https://github.com/chentao753951/ExpandableListViewExample-master/blob/master/iamge01/003.jpg)
![demo演示](https://github.com/chentao753951/ExpandableListViewExample-master/blob/master/iamge01/004.jpg)

####1. 如果子条目需要响应click事件,必需返回true
```
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
```

####2.  去除掉默认的关闭和打开状态的图片(系统默认的不好看)
```
android:groupIndicator="@null"
```

####3.  解决无法正常展开问题
```
//HorizontalScrollView
hsv_goods_list.setFocusable(false)
```
