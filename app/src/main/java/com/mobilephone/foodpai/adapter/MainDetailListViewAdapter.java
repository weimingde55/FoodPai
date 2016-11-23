package com.mobilephone.foodpai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.myinterface.UnitOfHeat;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qf on 2016/11/4.
 */
public class MainDetailListViewAdapter extends BaseAdapter {

    private Context context;
    private List<UnitOfHeat> heats;
    private LayoutInflater inflater;
    private ViewHolder viewHolder;

    public MainDetailListViewAdapter(Context context, List<UnitOfHeat> heats) {
        this.context = context;
        this.heats = heats;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return heats == null ? 0 : heats.size();
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_main_detail_list_view, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        UnitOfHeat heat = heats.get(position);
        viewHolder.tvUnit.setText(heat.getUnit());
        viewHolder.tvWeight.setText(heat.getWeight()+"克");
        viewHolder.tvCalory.setText(heat.getCalory()+"千卡");
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.tvunit)
        TextView tvUnit;
        @Bind(R.id.tvWeight)
        TextView tvWeight;
        @Bind(R.id.tvCalory)
        TextView tvCalory;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
