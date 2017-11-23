package com.bwie.a09ayjgwcdemo.presenter;

import com.bwie.a09ayjgwcdemo.bean.GoodsBean;
import com.bwie.a09ayjgwcdemo.model.IMainModel;
import com.bwie.a09ayjgwcdemo.model.MainModel;
import com.bwie.a09ayjgwcdemo.net.OnNetListener;
import com.bwie.a09ayjgwcdemo.view.IMainActivity;

import java.util.ArrayList;
import java.util.List;


public class MainPresenter {
    private IMainModel iMainModel;
    private IMainActivity iMainActivity;

    public MainPresenter(IMainActivity iMainActivity){
        this.iMainActivity=iMainActivity;
        iMainModel=new MainModel();
    }

    public void getGoods(){
        iMainModel.getGoods(new OnNetListener<GoodsBean>() {
            @Override
            public void OnSuccess(GoodsBean goodsBean) {
                ArrayList<GoodsBean.DataBean.DatasBean> list = new ArrayList<>();
                List<GoodsBean.DataBean> data = goodsBean.getData();
                for (int i = 0; i <data.size() ; i++) {
                    List<GoodsBean.DataBean.DatasBean> datas = data.get(i).getDatas();
                    list.addAll(datas);
                }
                iMainActivity.showRv(list);
            }

            @Override
            public void OnFailure(Exception e) {

            }
        });
    }
}
