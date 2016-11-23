package com.mobilephone.foodpai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.bean.CompeletFoodBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qf on 2016/11/4.
 */
public class SearchCompeletFoodAdapter extends BaseAdapter {

    final LayoutInflater inflater;
    Context context;
    boolean aBoolean;
    List<CompeletFoodBean.NutritionBean> nutritionLeft;
    List<CompeletFoodBean.NutritionBean> nutritionRight;
//    List<CompeletFoodBean.NutritionBean> nutrition;


    public SearchCompeletFoodAdapter(Context context, List<CompeletFoodBean.NutritionBean> nutrition, boolean aBoolean) {
        this.context = context;
        if (aBoolean){
            this.nutritionRight = nutrition;
        }else {
            this.nutritionLeft = nutrition;
        }
//        this.nutrition = nutrition;
        this.aBoolean = aBoolean;
        inflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        if (aBoolean) {
            return nutritionRight.size();
        }
        return nutritionLeft.size();

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
            convertView = inflater.inflate(R.layout.complete_itme_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (aBoolean) {
            CompeletFoodBean.NutritionBean nutritionBeanRight = nutritionRight.get(position);
            holder.tvCenter.setText(nutritionBeanRight.getName());
            String unit = nutritionBeanRight.getUnit();
            if (unit == null) {
                unit = "";
            }
            holder.tvRightInfo.setText(nutritionBeanRight.getValue() + unit);

        } else {
            CompeletFoodBean.NutritionBean nutritionBeanLeft = nutritionLeft.get(position);
            holder.tvCenter.setText(nutritionBeanLeft.getName());
            String unit = nutritionBeanLeft.getUnit();
            if (unit == null) {
                unit = "";
            }
            holder.tvLeftInfo.setText(nutritionBeanLeft.getValue() + unit);
        }


        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tvLeftInfo)
        TextView tvLeftInfo;
        @Bind(R.id.tvCenter)
        TextView tvCenter;
        @Bind(R.id.tvRightInfo)
        TextView tvRightInfo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
