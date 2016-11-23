package com.mobilephone.foodpai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobilephone.foodpai.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qf on 2016/11/2.
 */
public class HomeSearchAdapter extends BaseAdapter {

    final LayoutInflater inflater;
    Context context;
    List<String> data;

    public HomeSearchAdapter(Context context, List<String> data) {
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
            convertView = inflater.inflate(R.layout.home_search_item, parent, false);
            holder =new ViewHolder(convertView);
            convertView.setTag(holder);

        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        String s = data.get(position);
        holder.tvSearch.setText(s);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tvSearch)
        TextView tvSearch;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
