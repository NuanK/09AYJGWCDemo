package com.bwie.a09ayjgwcdemo.model;

import com.bwie.a09ayjgwcdemo.bean.GoodsBean;
import com.bwie.a09ayjgwcdemo.net.OnNetListener;

/**
 * Created by ASUS on 2017/11/21.
 */

public interface IMainModel {
    public void getGoods(OnNetListener<GoodsBean> onNetListener);
}
