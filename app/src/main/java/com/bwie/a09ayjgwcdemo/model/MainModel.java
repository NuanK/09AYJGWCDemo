package com.bwie.a09ayjgwcdemo.model;

import android.os.Handler;
import android.os.Looper;

import com.bwie.a09ayjgwcdemo.bean.GoodsBean;
import com.bwie.a09ayjgwcdemo.net.Api;
import com.bwie.a09ayjgwcdemo.net.HttpUtils;
import com.bwie.a09ayjgwcdemo.net.OnNetListener;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by ASUS on 2017/11/21.
 */

public class MainModel implements IMainModel{

    private Handler handler=new Handler(Looper.getMainLooper());

    @Override
    public void getGoods(final OnNetListener<GoodsBean> onNetListener) {
        HttpUtils.getHttpUtils().doGet(Api.url, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.OnFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final GoodsBean goodsBean = new Gson().fromJson(string, GoodsBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.OnSuccess(goodsBean);
                    }
                });
            }
        });
    }
}
