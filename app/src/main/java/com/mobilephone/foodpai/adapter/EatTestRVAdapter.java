package com.mobilephone.foodpai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.bean.EatTestBean;
import com.mobilephone.foodpai.util.DownLoadImageUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/1.
 */
public class EatTestRVAdapter extends RecyclerView.Adapter<EatTestRVAdapter.ViewHolder> {


    private Context context;
    private List<EatTestBean.FeedsBean> feeds;
    private LayoutInflater inflater;

    public EatTestRVAdapter(Context context, List<EatTestBean.FeedsBean> feeds) {
        this.context = context;
        this.feeds = feeds;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.eat_test_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        EatTestBean.FeedsBean feedsBean = feeds.get(position);

        holder.tvInfo.setText(feedsBean.getSource());
        holder.tvTitle.setText(feedsBean.getTitle());
        holder.tvReaded.setText(feedsBean.getTail());
//        Glide.with(context).load(Uri.parse(feedsBean.getBackground())).into(holder.ivBackground);
//        DownLoadImageUtil.load(context, feedsBean.getBackground(), R.mipmap.mq_ic_emoji_normal, R.mipmap.fail_img, holder.ivBackground);
        DownLoadImageUtil.noCacheLoad(context, feedsBean.getBackground(),R.mipmap.fail_img, holder.ivBackground);
        final int layoutPosition = holder.getLayoutPosition();
        holder.rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(layoutPosition);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivBackground)
        ImageView ivBackground;
        @Bind(R.id.tvInfo)
        TextView tvInfo;
        @Bind(R.id.tvTitle)
        TextView tvTitle;
        @Bind(R.id.tvReaded)
        TextView tvReaded;
        @Bind(R.id.rlRoot)
        RelativeLayout rlRoot;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
       void onItemClick(int position);
   }
}
