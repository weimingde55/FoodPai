package com.mobilephone.foodpai.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.bean.EatHomeFoodBean;
import com.mobilephone.foodpai.widget.MyCircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/31.
 */
public class EatHomeRVAdapter extends RecyclerView.Adapter<EatHomeRVAdapter.ViewHolder> {

    private static final String TAG = "EatHomeRVAdapter-test";
    private List<EatHomeFoodBean.FeedsBean> feedsBeanList;
    private Context context;
    private LayoutInflater layoutInflater;
//    private Point point = new Point(0, 0);

    public EatHomeRVAdapter(List<EatHomeFoodBean.FeedsBean> feedsBeanList, Context context) {
        this.feedsBeanList = feedsBeanList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.eathome_grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        EatHomeFoodBean.FeedsBean feedsBean = feedsBeanList.get(position);
        String url = feedsBean.getCard_image();
        String urlUser = feedsBean.getPublisher_avatar();

        String title = feedsBean.getTitle();
        if ("".equals(title) || title == null) {
            holder.tvFoodTitle.setVisibility(View.GONE);
            holder.tvContent.setVisibility(View.GONE);
            holder.tvUserName.setVisibility(View.GONE);
            holder.tvGoodNumber.setVisibility(View.GONE);
            holder.ivGoodCover.setVisibility(View.GONE);

        } else {
            holder.tvFoodTitle.setVisibility(View.VISIBLE);
            holder.tvContent.setVisibility(View.VISIBLE);
            holder.tvUserName.setVisibility(View.VISIBLE);
            holder.tvGoodNumber.setVisibility(View.VISIBLE);
            holder.ivGoodCover.setVisibility(View.VISIBLE);
        }

        String description = feedsBean.getDescription();
        if ("".equals(description) || description == null) {
            holder.tvContent.setVisibility(View.GONE);
        } else {
            holder.tvContent.setVisibility(View.VISIBLE);
            holder.tvContent.setText(description);
        }
        holder.tvFoodTitle.setText(title);
        holder.tvUserName.setText(feedsBean.getPublisher());
        holder.tvGoodNumber.setText(feedsBean.getLike_ct() + "");

        Glide.with(context)
                .load(urlUser)
                .crossFade(1000)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivUserCover);
        Glide.with(context)
                .load(url)
//                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.testcover)
//                .fitCenter()
//                .override(180, 150)
                .crossFade(1000)
//                .transform(new GlideRoundTransform(context, 20))//圆角矩形
//                .transform(new GlideRoundTransform(this, 10))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivEatFoodCover);

        holder.cvRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //layoutposition，才是真正的item的position
                onItemClickListener.onItemClick(holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedsBeanList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivEatFoodCover)
        ImageView ivEatFoodCover;
        @Bind(R.id.tvFoodTitle)
        TextView tvFoodTitle;
        @Bind(R.id.tvContent)
        TextView tvContent;
        @Bind(R.id.ivUserCover)
        MyCircleImageView ivUserCover;
        @Bind(R.id.tvUserName)
        TextView tvUserName;
        @Bind(R.id.tvGoodNumber)
        TextView tvGoodNumber;
        @Bind(R.id.ivGoodCover)
        ImageView ivGoodCover;
        @Bind(R.id.cvRoot)
        CardView cvRoot;
        @Bind(R.id.rlUserContent)
        RelativeLayout rlUserContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
