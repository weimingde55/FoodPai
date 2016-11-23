package com.mobilephone.foodpai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.activity.EditUserInfoActivity;
import com.mobilephone.foodpai.activity.LoginActivity;
import com.mobilephone.foodpai.activity.MyCollectActivity;
import com.mobilephone.foodpai.activity.SetingActivity;
import com.mobilephone.foodpai.activity.UpdataFoodActivity;
import com.mobilephone.foodpai.base.BaseFragment;
import com.mobilephone.foodpai.bean.UserBean;
import com.mobilephone.foodpai.widget.MyCircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/10/31.
 */
public class MineFragment extends BaseFragment {

    private static final String TAG = "MineFragment-test";
    @Bind(R.id.ivSeting)
    ImageView ivSeting;
    @Bind(R.id.ivPhoto)
    ImageView ivPhoto;
    @Bind(R.id.rlPhoto)
    RelativeLayout rlPhoto;
    @Bind(R.id.ivMyCollect)
    ImageView ivMyCollect;
    @Bind(R.id.rlMyCollect)
    RelativeLayout rlMyCollect;
    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.rlUpload)
    RelativeLayout rlUpload;
    @Bind(R.id.ivMyOrdr)
    ImageView ivMyOrdr;
    @Bind(R.id.rlMyCordr)
    RelativeLayout rlMyCordr;
    private View view;
    private TextView tvUserName;
    private TextView tvEditUserInfo;
    private TextView tvLand;
    private MyCircleImageView ivUserCover;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_mine, container, false);
            tvUserName = ((TextView) view.findViewById(R.id.tvUserName));
            tvEditUserInfo = ((TextView) view.findViewById(R.id.tvEditUserInfo));
            ivUserCover = ((MyCircleImageView) view.findViewById(R.id.ivUserCover));

            tvLand = ((TextView) view.findViewById(R.id.tvLand));
//            getCurrentUser();
            tvLand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });
            tvEditUserInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
                    startActivity(intent);
                }
            });
        }

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getCurrentUser();
    }

    private void getCurrentUser() {
        //获取当前用户信息
        UserBean currentUser = UserBean.getCurrentUser(UserBean.class);
        if (currentUser != null) {
            tvLand.setVisibility(View.GONE);
            tvUserName.setVisibility(View.VISIBLE);
            tvUserName.setText(currentUser.getUsername());
            tvEditUserInfo.setVisibility(View.VISIBLE);
            String userCoverUrl = currentUser.getUserCover();
            Glide.with(getActivity())
                    .load(userCoverUrl)
                    .skipMemoryCache(true)
                    .into(ivUserCover);
            Log.e(TAG, "onCreateView: " + currentUser.getUsername());
        } else {
            tvUserName.setVisibility(View.GONE);
            tvLand.setVisibility(View.VISIBLE);
            tvEditUserInfo.setVisibility(View.GONE);

        }
    }

    @OnClick(R.id.ivSeting)
    public void onIvSeting(View view) {
        Intent intent = new Intent(getContext(), SetingActivity.class);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.rlUpload)
    public void onRlUpload(View view) {
        Intent intent = new Intent(getContext(), UpdataFoodActivity.class);
        getActivity().startActivity(intent);
    }


    @OnClick(R.id.rlMyCollect)
    public void onRlMyCollect() {
        Intent intent = new Intent(getActivity(), MyCollectActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
