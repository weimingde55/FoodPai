package com.mobilephone.foodpai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.bean.FoodDetailsBean;
import com.mobilephone.foodpai.util.DownLoadImageUtil;
import com.mobilephone.foodpai.widget.MyCircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qf on 2016/11/2.
 */
public class FoodClassDetailsAdapter extends BaseAdapter {

    final LayoutInflater inflater;
    Context context;
    List<FoodDetailsBean.FoodsBean> data;
    int currentPosition = -1;

    public FoodClassDetailsAdapter(Context context, List<FoodDetailsBean.FoodsBean> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.food_detail_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FoodDetailsBean.FoodsBean foodsBean = data.get(position);
        currentPosition = position;


        String calory = foodsBean.getCalory();
        holder.tvCalory.setText(calory +"千卡/100克");//复用问题不能直接用holder.tvCalory.getText()，因为他总后一个会将第一个的值设置到position去
        holder.tvfoodName.setText(foodsBean.getName());
        DownLoadImageUtil.noCacheLoad(context, foodsBean.getThumb_image_url(), R.mipmap.fail_img, holder.lvHeader);


        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.lvHeader)
        MyCircleImageView lvHeader;
        @Bind(R.id.tvfoodName)
        TextView tvfoodName;
        @Bind(R.id.tvCalory)
        TextView tvCalory;
        @Bind(R.id.lvSuggest)
        ImageView lvSuggest;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
