package com.hq.expandablelistviewexample.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hq.expandablelistviewexample.R;

/**
 * Created by Administrator on 2017/11/20.
 */

public class CustomView extends LinearLayout {
    private ImageView revserse;    //减号
    private ImageView add;         //加号
    private EditText editText;
    private int number = 1;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置布局
        View view = LayoutInflater.from(context).inflate(R.layout.customview, null, false);
        revserse = (ImageView) view.findViewById(R.id.id_image_revserse);
        add = (ImageView) view.findViewById(R.id.id_image_add);
        editText = (EditText) view.findViewById(R.id.id_ed_num);

        //点击减号时的情况变化
        revserse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    String content = editText.getText().toString().trim();
//                    //商品数量大于0时才可以变化，等于0的情况不能出现
//                    int count = Integer.valueOf(content);
//                    if (count > 1) {
//                        number = count - 1;
//                        editText.setText(number + "");
//
//                        //当减号点击后，数值变化
//                        if (listener != null) {
//                            listener.click(number);
//                        }
//                    }
//                } catch (NumberFormatException e) {
//                    e.printStackTrace();
//                }
                listener.click(0);
            }
        });

        editText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.click(1);
            }
        });

        //点击加号时的情况变化
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    String content = editText.getText().toString().trim();
//                    int count = Integer.valueOf(content) + 1;
//                    number = count;
//                    editText.setText(count + "");
//                    //当加号点击后，数值变化
//                    if (listener != null) {
//                        listener.click(count);
//                    }
//                } catch (NumberFormatException e) {
//                    e.printStackTrace();
//                }
                listener.click(2);
            }
        });
        addView(view);
    }

    //设置方法获取EditText输入框的值
    public void setEditText(int count) {
        editText.setText(count + "");
    }

    //声明number变量
    public int getCurrentCount() {

        return number;
    }

    public int getEditTextNum() {
        try {
            return Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //定义回调接口,用于判断是否点击情况
    public ClickListener listener;

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

    public interface ClickListener {
        /**
         * count  ==0   减号
         * count  ==1   数字
         * count  ==2   加号
         *
         * @param count
         */
        void click(int count);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}