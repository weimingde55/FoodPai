package com.mobilephone.foodpai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.bean.FoodEncyclopediaBean;
import com.mobilephone.foodpai.util.DownLoadImageUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-10-31.
 */
public class HomeAdapter extends BaseAdapter {

    private static final String TAG = "hometest";
    private final LayoutInflater inflater;
    Context context;
    List<FoodEncyclopediaBean.GroupBean.CategoriesBean> CategoriesList;
    private FoodEncyclopediaBean.GroupBean.CategoriesBean categoriesBean;

    private int layoutManager = LAYOUT_MANAGER_BIG;


    /**
     * 通过传入的常量来判断使用的item布局
     */
    public static final int LAYOUT_MANAGER_BIG = 0;
    public static final int LAYOUT_MANAGER_SMALL = 1;

    /**
     * 有参构造，初始化layoutinflater
     * @param context
     * @param categoriesList
     * @param layoutManager
     */
    public HomeAdapter(Context context, List<FoodEncyclopediaBean.GroupBean.CategoriesBean> categoriesList,int layoutManager) {
        this.context = context;
        CategoriesList = categoriesList;
        this.layoutManager = layoutManager;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 返回list集合长度
     * @return
     */
    @Override
    public int getCount() {
        if (CategoriesList != null) {
            int size = CategoriesList.size();
            return size;
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

    /**
     * 采用复用原理
     * 根据Holer给控件赋值
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            if(layoutManager==LAYOUT_MANAGER_BIG) {
                convertView = inflater.inflate(R.layout.item_home, parent, false);
            }else {
                convertView = inflater.inflate(R.layout.item_home_small, parent, false);
            }
            holder= new Holder(convertView);
            convertView.setTag(holder);
        }else {
            holder=((Holder) convertView.getTag());
        }

        categoriesBean = CategoriesList.get(position);

        final String name = categoriesBean.getName();
        String imageUrl = categoriesBean.getImage_url();




        holder.tvSortName.setText(name);
//        loadImg(imageUrl,holder);
        //图片加载并缓存
//        DownLoadImageUtil.loadImg(context,imageUrl,R.mipmap.mq_ic_emoji_normal,R.mipmap.fail_img,holder.ivSortImg);
        DownLoadImageUtil.noCacheLoad(context,imageUrl,R.mipmap.fail_img,holder.ivSortImg);
        return convertView;
    }

    /**
     * 使用Glide图片加载框架
     * @param
     * @param
     */
//    public void loadImg(String url, Holder holder) {
//        Glide.with(context)
//                .load(Uri.parse(url))
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
//                .fitCenter()
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(holder.ivSortImg);
//    }


     class Holder {
        @Bind(R.id.ivSortImg)
        ImageView ivSortImg;
        @Bind(R.id.tvSortName)
        TextView tvSortName;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
