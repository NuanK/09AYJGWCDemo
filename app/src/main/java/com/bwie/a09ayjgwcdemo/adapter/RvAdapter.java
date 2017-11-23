package com.bwie.a09ayjgwcdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.a09ayjgwcdemo.MyView;
import com.bwie.a09ayjgwcdemo.R;
import com.bwie.a09ayjgwcdemo.bean.GoodsBean;
import com.bwie.a09ayjgwcdemo.eventbus.MessageEvent;
import com.bwie.a09ayjgwcdemo.eventbus.PriceAndCountEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<GoodsBean.DataBean.DatasBean> list;

    public RvAdapter(Context context, List<GoodsBean.DataBean.DatasBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_child,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final GoodsBean.DataBean.DatasBean datasBean = list.get(position);
        final MyViewHolder myViewHolder=(MyViewHolder)holder;
        myViewHolder.cbChild.setChecked(datasBean.isChecked());
        myViewHolder.tv_tel.setText(datasBean.getType_name());
        myViewHolder.tv_content.setText(datasBean.getMsg());
        myViewHolder.tv_time.setText(datasBean.getAdd_time());
        myViewHolder.tv_price.setText(datasBean.getPrice()+"");
        myViewHolder.myView.setNum(datasBean.getNum()+"");
        myViewHolder.cbChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datasBean.setChecked(myViewHolder.cbChild.isChecked());
                PriceAndCountEvent comute=compute();
                EventBus.getDefault().post(comute);
                if (myViewHolder.cbChild.isChecked()){
                    if (isAllCbSelectd()){
                        //改变"全选"状态
                        changeAllCbState(true);
                    }
                }else {
                    changeAllCbState(false);
                }
                notifyDataSetChanged();
            }
        });

        myViewHolder.myView.setAddClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = myViewHolder.myView.getNum();
                num++;
                datasBean.setNum(num);
                if (myViewHolder.cbChild.isChecked()){
                    EventBus.getDefault().post(compute());
                }
                notifyDataSetChanged();
            }
        });

        myViewHolder.myView.setDelClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = myViewHolder.myView.getNum();
                if (num==1){
                    return;
                }
                num--;
                datasBean.setNum(num);
                if (myViewHolder.cbChild.isChecked()){
                    EventBus.getDefault().post(compute());
                }
                notifyDataSetChanged();
            }
        });

        myViewHolder.tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                EventBus.getDefault().post(compute());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox cbChild;
        private final TextView tv_tel;
        private final TextView tv_content;
        private final TextView tv_time;
        private final TextView tv_price;
        private MyView myView;
        private final TextView tv_del;

        public MyViewHolder(View itemView) {
            super(itemView);

            cbChild = itemView.findViewById(R.id.check_child);
            tv_tel = itemView.findViewById(R.id.tv_tel);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_price = itemView.findViewById(R.id.tv_pri);
            tv_del = itemView.findViewById(R.id.tv_del);
            myView = itemView.findViewById(R.id.view);
        }
    }


    /**
     * 改变全选的状态
     *
     * @param flag
     */
    private void changeAllCbState(boolean flag) {
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setChecked(flag);
        EventBus.getDefault().post(messageEvent);
    }


    private boolean isAllCbSelectd(){
        for (int i = 0; i <list.size() ; i++) {
            GoodsBean.DataBean.DatasBean datasBean = list.get(i);
            if (!datasBean.isChecked()){
                return false;
            }
        }
        return true;
    }

    /**
     * 计算钱和数量
     * @return
     */

    private PriceAndCountEvent compute(){
        int price=0;
        int count=0;
        for (int i = 0; i <list.size() ; i++) {
            GoodsBean.DataBean.DatasBean datasBean = list.get(i);
            if (datasBean.isChecked()){
                price+=datasBean.getPrice()*datasBean.getNum();
                count+=datasBean.getNum();
            }
        }

        PriceAndCountEvent priceAndCountEvent=new PriceAndCountEvent();
        priceAndCountEvent.setPrice(price);
        priceAndCountEvent.setCount(count);
        return priceAndCountEvent;
    }

    public void allSelect(boolean flag){
        for (int i = 0; i <list.size() ; i++) {
            GoodsBean.DataBean.DatasBean datasBean = list.get(i);
            datasBean.setChecked(flag);
        }

        EventBus.getDefault().post(compute());
        notifyDataSetChanged();
    }
}
