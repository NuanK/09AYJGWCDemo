package com.bwie.a09ayjgwcdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ASUS on 2017/11/21.
 */

public class MyView extends LinearLayout{

    private ImageView iv_add;
    private ImageView iv_del;
    private TextView tv_num;


    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view= LayoutInflater.from(context).inflate(R.layout.myview,this);
        iv_add=findViewById(R.id.iv_add);
        iv_del=findViewById(R.id.iv_del);
        tv_num=findViewById(R.id.tv_num);
    }

    public void setAddClickListener(OnClickListener onClickListener) {
        iv_add.setOnClickListener(onClickListener);
    }

    public void setDelClickListener(OnClickListener onClickListener) {
        iv_del.setOnClickListener(onClickListener);
    }

    public void setNum(String num) {
        tv_num.setText(num);
    }

    public int getNum() {
        String num = tv_num.getText().toString();
        return Integer.parseInt(num);
    }

}
