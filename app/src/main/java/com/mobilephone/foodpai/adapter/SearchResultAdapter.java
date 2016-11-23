package com.mobilephone.foodpai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobilephone.foodpai.R;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qf on 2016/11/2.
 */
public class SearchResultAdapter extends BaseAdapter {


    private static final String TAG = "test";
    final LayoutInflater inflater;
    Context context;
    List<Map<Object, String>> mapList;

    public SearchResultAdapter(Context context,List<Map<Object, String>> mapList) {
        this.context = context;
        this.mapList = mapList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mapList.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.search_result_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        Map<Object, String> map = mapList.get(position);

        for (int i = 0; i < map.size(); i++) {
            String s = map.get("s");
            holder.tvFoodName.setText(s);
            String foodName = map.get("foodName");
            holder.tvFoodName.setText(foodName);
        }


        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.tvFoodName)
        TextView tvFoodName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
