package com.bwie.a09ayjgwcdemo.model;

import com.bwie.a09ayjgwcdemo.bean.GoodsBean;
import com.bwie.a09ayjgwcdemo.net.OnNetListener;


public interface IMainModel {
    public void getGoods(OnNetListener<GoodsBean> onNetListener);
}
