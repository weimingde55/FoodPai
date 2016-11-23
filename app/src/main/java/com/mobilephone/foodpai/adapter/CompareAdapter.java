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
 * Created by lenovo on 2016/11/14.
 */
public class CompareAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    Context context;
    List<CompeletFoodBean.NutritionBean> left;
    List<CompeletFoodBean.NutritionBean> right;

    public CompareAdapter(Context context, List<CompeletFoodBean.NutritionBean> left, List<CompeletFoodBean.NutritionBean> right) {

        this.context = context;
        this.left = left;
        this.right = right;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        if ((left.size() != 0 || right.size() != 0) && left.size() >= right.size()) {
            return left.size();
        } else if (left.size() == 0 && right.size() == 0) {
            return 0;
        } else if ((left.size() != 0 || right.size() != 0) && left.size() < right.size()) {
            return right.size();
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
            convertView = inflater.inflate(R.layout.complete_itme_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CompeletFoodBean.NutritionBean leftBean = null;
        CompeletFoodBean.NutritionBean rightBean = null;
        try {
            leftBean = left.get(position);
            rightBean = right.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (leftBean != null) {
            holder.tvCenter.setText(leftBean.getName());
            String unit = null;
            String value = null;
            try {
                unit = leftBean.getUnit();
                if (unit == null) {
                    unit = "";
                }
                value = leftBean.getValue();
                if (value.equals("")) {
                    value = "--";
                }
                holder.tvLeftInfo.setText(value + unit);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        if (rightBean != null) {
            String unit = null;
            String value = null;

            try {
                unit = rightBean.getUnit();
                if (unit == null) {
                    unit = "";
                }
                value = rightBean.getValue();
                if (value.equals("")) {
                    value = "--";
                }
                holder.tvRightInfo.setText(value + unit);
            } catch (Exception e) {
                e.printStackTrace();
            }

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
