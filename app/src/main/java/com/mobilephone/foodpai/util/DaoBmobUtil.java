package com.mobilephone.foodpai.util;

import android.util.Log;

import com.mobilephone.foodpai.bean.UserBean;
import com.mobilephone.foodpai.bean.bmobbean.CollectBean;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2016/11/4.
 * 单例模式工具类，getInstance()拿到此类实例
 */
public class DaoBmobUtil {

    private static final String TAG = "DaoBmobUtil-test";
    private static DaoBmobUtil daoBmobUtil = new DaoBmobUtil();

    public static DaoBmobUtil getInstance() {
        return daoBmobUtil;
    }

    private DaoBmobUtil() {
    }

    //修改用户信息
    public void onUpdate(UserBean userBean, final OnUpdate onUpdate) {
        UserBean currentUser = BmobUser.getCurrentUser(UserBean.class);
        userBean.update(currentUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                onUpdate.done(e);
            }
        });
    }

    //用户登录
    public void onLogin(UserBean userBean, final OnDaoLogin onDaoLogin) {
        userBean.login(new SaveListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                onDaoLogin.done(userBean, e);
            }
        });

    }

    //查询用户数据
    public void onQuery(final OnDaoQuery onDaoQuery) {
        UserBean user = UserBean.getCurrentUser(UserBean.class);
        if (user!=null) {
            BmobQuery<CollectBean> Query = new BmobQuery<>();
            Query.addWhereEqualTo("Collect", user);
            Query.findObjects(new FindListener<CollectBean>() {
                @Override
                public void done(List<CollectBean> list, BmobException e) {

                    onDaoQuery.onQuery(list, e);
                }
            });
        }
    }

    //增加用户收藏数据
    public void onAdd(String title, String link,String imgurl,String calory,String code, final OnDaoAdd onDaoAdd) {
        UserBean user = UserBean.getCurrentUser(UserBean.class);
        Log.e(TAG, "onAdduser: "+user);
        if (user!=null) {
            String objectId = user.getObjectId();
            Log.e(TAG, "onAddobjectId: "+objectId);
            CollectBean collectBean = new CollectBean();
            collectBean.setTitle(title);
            collectBean.setLink(link);
            collectBean.setImgUrl(imgurl);
            collectBean.setCalory(calory);
            collectBean.setCode(code);
            collectBean.setCollect(user);
            collectBean.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    onDaoAdd.onAdd(s, e);
                }
            });
        }
    }



    //删除用户收藏数据
    public void onDelete(Map<String, String> map, String title, final OnDelete onDelete) {
        String objectId = map.get(title);
        Log.e(TAG, "onDelete: ");
        CollectBean collectBean = new CollectBean();
        collectBean.setObjectId(objectId);
        collectBean.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                onDelete.onDelete(e);
            }
        });
    }


    //单文件上传

    /**
     * @param bmobFile        BmobFile bmobFile = new BmobFile(new File(picPath));
     * @param onDaoUpLoadfile 状态回调
     */
    public void upLoadFile(BmobFile bmobFile, final OnDaoUpLoadfile onDaoUpLoadfile) {

        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                onDaoUpLoadfile.done(e);
            }

            @Override
            public void onProgress(Integer value) {
                onDaoUpLoadfile.onProgress(value);
            }
        });
    }

    //批量上传
    public void upLoadeFiles(String[] filePaths, final OnDaoUpLoadFiles onDaoUpLoadFiles) {
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
            /**
             *1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
             *2、urls-上传文件的完整url地址
             *if (urls.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成
             *do something
             *}
             * @param files
             * @param urls
             */
            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                onDaoUpLoadFiles.onSuccess(files, urls);

            }

            /**
             *
             * @param curIndex--表示当前第几个文件正在上传
             * @param curPercent--表示当前上传文件的进度值（百分比）
             * @param total--表示总的上传文件数
             * @param totalPercent--表示总的上传进度（百分比）
             */
            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                onDaoUpLoadFiles.onProgress(curIndex, curPercent, total, totalPercent);
            }

            /**
             * Log.e(TAG, "onError: " + "错误码" + statuscode + ",错误描述：" + errormsg);
             * @param statuscode
             * @param errormsg
             */
            @Override
            public void onError(int statuscode, String errormsg) {
                onDaoUpLoadFiles.onError(statuscode, errormsg);

            }
        });

    }

    //修改用户信息接口回调
    public interface OnUpdate {
        void done(BmobException e);
    }

    //用户登录接口
    public interface OnDaoLogin {
        void done(UserBean userBean, BmobException e);
    }

    //单文件上传回调
    public interface OnDaoUpLoadfile {
        void done(BmobException e);

        void onProgress(Integer value);
    }

    //批量上传的回调
    public interface OnDaoUpLoadFiles {
        void onSuccess(List<BmobFile> files, List<String> urls);

        void onProgress(int curIndex, int curPercent, int total, int totalPercent);

        void onError(int statuscode, String errormsg);
    }

    //查询的接口回调
    public interface OnDaoQuery {

        void onQuery(List<CollectBean> list, BmobException e);
    }

    //增加数据的接口
    public interface OnDaoAdd {
        void onAdd(String s, BmobException e);
    }

    //删除数据的接口
    public interface OnDelete {
        void onDelete(BmobException e);
    }

}
