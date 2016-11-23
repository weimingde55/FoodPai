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
import com.mobilephone.foodpai.bean.EatKnowBean;
import com.mobilephone.foodpai.util.DownLoadImageUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qf on 2016/11/1.
 */
public class EatKnowRVAdapter extends RecyclerView.Adapter<EatKnowRVAdapter.ViewHolder> {


    private Context context;
    private LayoutInflater inflater;
    private List<EatKnowBean.FeedsBean> feedsBeenList;

    public EatKnowRVAdapter(Context context, List<EatKnowBean.FeedsBean> feedsBeenList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.feedsBeenList = feedsBeenList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_eat_know, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (feedsBeenList.size() == 0 || feedsBeenList == null) {
            return;
        }
        List<String> images = feedsBeenList.get(position).getImages();
        String title = feedsBeenList.get(position).getTitle();
        String source = feedsBeenList.get(position).getSource();
        String tail = feedsBeenList.get(position).getTail();

        //如果图片少于3张就使用普通布局，否则使用多图布局
        Log.e("Temy", "image : " + images.size());
        if (images.size() > 0 && images.size() < 3) {
            Log.e("Temy", "普通布局");
            holder.llSpecial.setVisibility(View.GONE);
            holder.llnormal.setVisibility(View.VISIBLE);
            String url = images.get(0);
//            DownLoadImageUtil.load(context, url, R.mipmap.mq_ic_emoji_normal, R.mipmap.fail_img, holder.ivEatKnowPic);
            DownLoadImageUtil.noCacheLoad(context, url, R.mipmap.fail_img, holder.ivEatKnowPic);
            holder.tvEatKnowTitle.setText(title);
            holder.tvEatKnowFrom.setText(source);
            holder.tvReadCount.setText(tail);
        } else if (images.size() >= 3) {
            Log.e("Temy", "多图布局");
            holder.llSpecial.setVisibility(View.VISIBLE);
            holder.llnormal.setVisibility(View.GONE);
//            DownLoadImageUtil.load(context, images.get(0), R.mipmap.mq_ic_emoji_normal, R.mipmap.fail_img, holder.ivEatKnowPics1);
           DownLoadImageUtil.noCacheLoad(context, images.get(0), R.mipmap.fail_img, holder.ivEatKnowPics1);
//            DownLoadImageUtil.load(context, images.get(1), R.mipmap.mq_ic_emoji_normal, R.mipmap.fail_img, holder.ivEatKnowPics2);
            DownLoadImageUtil.noCacheLoad(context, images.get(1), R.mipmap.fail_img, holder.ivEatKnowPics2);

//            DownLoadImageUtil.load(context, images.get(2), R.mipmap.mq_ic_emoji_normal, R.mipmap.fail_img, holder.ivEatKnowPics3);
            DownLoadImageUtil.noCacheLoad(context, images.get(2), R.mipmap.fail_img, holder.ivEatKnowPics3);

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


    @Override
    public int getItemCount() {
        return (feedsBeenList == null || feedsBeenList.equals("")) ? 0 : feedsBeenList.size();

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvEatKnowTitle)
        TextView tvEatKnowTitle;
        @Bind(R.id.tvEatKnowFrom)
        TextView tvEatKnowFrom;
        @Bind(R.id.tvEatKnowEye)
        TextView tvEatKnowEye;
        @Bind(R.id.tvReadCount)
        TextView tvReadCount;
        @Bind(R.id.ivEatKnowPic)
        ImageView ivEatKnowPic;
        @Bind(R.id.llnormal)
        LinearLayout llnormal;
        @Bind(R.id.tvSpecialTitle)
        TextView tvSpecialTitle;
        @Bind(R.id.ivEatKnowPics1)
        ImageView ivEatKnowPics1;
        @Bind(R.id.ivEatKnowPics2)
        ImageView ivEatKnowPics2;
        @Bind(R.id.ivEatKnowPics3)
        ImageView ivEatKnowPics3;
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
