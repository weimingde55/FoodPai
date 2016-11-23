package com.mobilephone.foodpai.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.activity.SearchComparisonActivity;
import com.mobilephone.foodpai.bean.SearchFoodBean;
import com.mobilephone.foodpai.util.DownLoadImageUtil;
import com.mobilephone.foodpai.widget.MyCircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qf on 2016/11/3.
 */
public class SearchResultListAdapter extends BaseAdapter {

    final LayoutInflater inflater;
    Context context;
    List<SearchFoodBean.ItemsBean> data;
    String compelet;
    boolean aBoolean;


    public SearchResultListAdapter(Context context, List<SearchFoodBean.ItemsBean> data, String compelet, boolean aBoolean) {
        this.context = context;
        this.data = data;
        this.compelet = compelet;
        this.aBoolean =aBoolean;
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
        final SearchFoodBean.ItemsBean itemsBean = data.get(position);


        String calory = itemsBean.getCalory();
        holder.tvCalory.setText(calory + "千卡/100克");//复用问题不能直接用holder.tvCalory.getText()，因为他总后一个会将第一个的值设置到position去
        holder.tvfoodName.setText(itemsBean.getName());
        DownLoadImageUtil.noCacheLoad(context, itemsBean.getThumb_image_url(), R.mipmap.fail_img, holder.lvHeader);
        if (compelet != null) {
            holder.rbAdd.setVisibility(View.VISIBLE);
            holder.lvSuggest.setVisibility(View.GONE);
        }
        holder.rbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = itemsBean.getCode();
                Toast.makeText(context, "成功加入对比", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, SearchComparisonActivity.class);
                intent.putExtra("code", code);
                intent.putExtra("RL",aBoolean);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });

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
        @Bind(R.id.rbAdd)
        RadioButton rbAdd;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
