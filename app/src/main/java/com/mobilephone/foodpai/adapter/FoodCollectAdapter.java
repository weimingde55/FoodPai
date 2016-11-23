package com.mobilephone.foodpai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.bean.bmobbean.CollectBean;
import com.mobilephone.foodpai.util.DownLoadImageUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-11-04.
 */
public class FoodCollectAdapter extends BaseAdapter {
    private static final String TAG = "test";
    private final LayoutInflater inflater;
    Context context;
    List<CollectBean> list;

    public FoodCollectAdapter(Context context, List<CollectBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (list != null) {
            int size = list.size();
            return size;
        }
        return 0;
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
            convertView = inflater.inflate(R.layout.food_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = ((ViewHolder) convertView.getTag());
        }
        CollectBean collectBean = list.get(position);
        String name = collectBean.getTitle();
        String imgUrl = collectBean.getImgUrl();
        String calory = collectBean.getCalory();
        holder.tvFoodName.setText(name);
        holder.tvFoodInfo.setText(calory);
        DownLoadImageUtil.noCacheLoad(context,imgUrl,R.mipmap.fail_img,holder.ivFoodImg);
        notifyDataSetChanged();

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.ivFoodImg)
        ImageView ivFoodImg;
        @Bind(R.id.tvFoodName)
        TextView tvFoodName;
        @Bind(R.id.tvFoodInfo)
        TextView tvFoodInfo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
