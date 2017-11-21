package com.bwie.a09ayjgwcdemo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.a09ayjgwcdemo.R;
import com.bwie.a09ayjgwcdemo.adapter.RvAdapter;
import com.bwie.a09ayjgwcdemo.bean.GoodsBean;
import com.bwie.a09ayjgwcdemo.eventbus.MessageEvent;
import com.bwie.a09ayjgwcdemo.eventbus.PriceAndCountEvent;
import com.bwie.a09ayjgwcdemo.presenter.MainPresenter;
import com.bwie.a09ayjgwcdemo.view.IMainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainActivity{

    private RecyclerView mRecy;
    private CheckBox mCheckQuan;
    /**
     * 0
     */
    private TextView mTvPrice;
    /**
     * 结算(0)
     */
    private TextView mTvNum;
    private LinearLayout mActivityMain;
    MainPresenter mainPresenter;
    RvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        mainPresenter=new MainPresenter(this);
        initView();
        mRecy.setLayoutManager(new LinearLayoutManager(this));
        mainPresenter.getGoods();
        mCheckQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.allSelect(mCheckQuan.isChecked());
            }
        });
    }

    private void initView() {
        mRecy = (RecyclerView) findViewById(R.id.recy);
        mCheckQuan = (CheckBox) findViewById(R.id.check_quan);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvNum = (TextView) findViewById(R.id.tv_num);
        mActivityMain = (LinearLayout) findViewById(R.id.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        mCheckQuan.setChecked(event.isChecked());
    }

    @Subscribe
    public void onMessageEvent(PriceAndCountEvent event) {
        mTvNum.setText("结算(" + event.getCount() + ")");
        mTvPrice.setText(event.getPrice() + "");
    }

    @Override
    public void showRv(List<GoodsBean.DataBean.DatasBean> list) {
        adapter=new RvAdapter(this,list);
        mRecy.setAdapter(adapter);
    }
}
