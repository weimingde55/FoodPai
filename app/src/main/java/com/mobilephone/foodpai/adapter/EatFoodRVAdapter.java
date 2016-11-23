package com.mobilephone.foodpai.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.bean.EatFoodBean;
import com.mobilephone.foodpai.util.DownLoadImageUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qf on 2016/11/2.
 */
public class EatFoodRVAdapter extends RecyclerView.Adapter<EatFoodRVAdapter.ViewHolder> {

    @Bind(R.id.rlRoot)
    RelativeLayout rlRoot;
    private Context context;
    private List<EatFoodBean.FeedsBean> feedsBeanList;
    private LayoutInflater inflater;

    public EatFoodRVAdapter(Context context, List<EatFoodBean.FeedsBean> feedsBeanList) {
        this.context = context;
        this.feedsBeanList = feedsBeanList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return (feedsBeanList == null || feedsBeanList.equals("") ? 0 : feedsBeanList.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_eat_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (feedsBeanList.size() == 0 || feedsBeanList == null) {
            return;
        }
        List<String> images = feedsBeanList.get(position).getImages();
        String title = feedsBeanList.get(position).getTitle();
        String source = feedsBeanList.get(position).getSource();
        String tail = feedsBeanList.get(position).getTail();

        //如果图片少于3张就使用普通布局，否则使用多图布局
        Log.e("Temy", "image : " + images.size());
        if (images.size() > 0 && images.size() < 3) {
            Log.e("Temy", "普通布局");
            holder.llSpecial.setVisibility(View.GONE);
            holder.llnormal.setVisibility(View.VISIBLE);
            String url = images.get(0);
//            DownLoadImageUtil.load(context, url, R.mipmap.mq_ic_emoji_normal, R.mipmap.fail_img, holder.ivEatFoodPic);
            DownLoadImageUtil.noCacheLoad(context,url,R.mipmap.fail_img,holder.ivEatFoodPic);
            holder.tvEatFoodTitle.setText(title);
            holder.tvEatFoodFrom.setText(source);
            holder.tvReadCount.setText(tail);
        } else if (images.size() >= 3) {
            Log.e("Temy", "多图布局");
            holder.llSpecial.setVisibility(View.VISIBLE);
            holder.llnormal.setVisibility(View.GONE);
//            DownLoadImageUtil.load(context, images.get(0), R.mipmap.mq_ic_emoji_normal, R.mipmap.fail_img, holder.ivEatFoodPics1);
            DownLoadImageUtil.noCacheLoad(context, images.get(0), R.mipmap.fail_img, holder.ivEatFoodPics1);
//            DownLoadImageUtil.load(context, images.get(1), R.mipmap.mq_ic_emoji_normal, R.mipmap.fail_img, holder.ivEatFoodPics2);
            DownLoadImageUtil.noCacheLoad(context, images.get(1), R.mipmap.fail_img, holder.ivEatFoodPics2);
//            DownLoadImageUtil.load(context, images.get(2), R.mipmap.mq_ic_emoji_normal, R.mipmap.fail_img, holder.ivEatFoodPics3);
            DownLoadImageUtil.noCacheLoad(context, images.get(2), R.mipmap.fail_img, holder.ivEatFoodPics3);
            holder.tvSpecialTitle.setText(title);
            holder.tvSpecialFrom.setText(source);
            holder.tvSpecialReadCount.setText(tail);
        }

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


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvEatFoodTitle)
        TextView tvEatFoodTitle;
        @Bind(R.id.tvEatFoodFrom)
        TextView tvEatFoodFrom;
        @Bind(R.id.tvReadCount)
        TextView tvReadCount;
        @Bind(R.id.tvEatFoodEye)
        TextView tvEatFoodEye;
        @Bind(R.id.ivEatFoodPic)
        ImageView ivEatFoodPic;
        @Bind(R.id.llnormal)
        LinearLayout llnormal;
        @Bind(R.id.tvSpecialTitle)
        TextView tvSpecialTitle;
        @Bind(R.id.ivEatFoodPics1)
        ImageView ivEatFoodPics1;
        @Bind(R.id.ivEatFoodPics2)
        ImageView ivEatFoodPics2;
        @Bind(R.id.ivEatFoodPics3)
        ImageView ivEatFoodPics3;
        @Bind(R.id.tvSpecialFrom)
        TextView tvSpecialFrom;
        @Bind(R.id.tvSpecialEye)
        TextView tvSpecialEye;
        @Bind(R.id.tvSpecialReadCount)
        TextView tvSpecialReadCount;
        @Bind(R.id.llSpecial)
        LinearLayout llSpecial;
        @Bind(R.id.cvRoot)
        CardView cvRoot;
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
