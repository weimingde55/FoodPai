package com.mobilephone.foodpai.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 网络图片加载器,采用的是异步解析网络图片，单例模式利用getInstance()获取NativeImageLoader实例
 * 调用loadNativeImage()方法加载网络图片，此类可作为一个加载网络图片的工具类
 */
public class NetImageLoaderUtil {
    private static final String TAG = "NativeImageLoader-test";
    private LruCache<String, Bitmap> mMemoryCache;
    private static NetImageLoaderUtil mInstance = new NetImageLoaderUtil();
    private ExecutorService mImageThreadPool = Executors.newFixedThreadPool(1);

    private NetImageLoaderUtil() {
        //获取应用程序的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        //用最大内存的1/4来存储图片
        final int cacheSize = maxMemory / 4;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {

            //获取每张图片的大小
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
            }
        };

    }

    /**
     * 通过此方法来获取类的实例
     *
     * @return
     */
    public static NetImageLoaderUtil getInstance() {
        return mInstance;
    }

    /**
     * 加载图片，对图片不进行裁剪,不建议使用
     *
     * @param url
     * @param mCallBack
     * @return
     */
    public Bitmap loadNetImage(Context context, final String url, final NativeImageCallBack mCallBack) {

        return this.loadNetImage(context, url, null, mCallBack);
    }

    /**
     * 此方法来加载网络图片，这里的mPoint是用来封装ImageView的宽和高，我们会根据ImageView控件的大小来裁剪Bitmap
     * 如果你不想裁剪图片，调用loadNativeImage(final String url, final NativeImageCallBack mCallBack)来加载
     *
     * @param url
     * @param mPoint
     * @param mCallBack
     * @return
     */
    public Bitmap loadNetImage(Context context, final String url, final Point mPoint, final NativeImageCallBack mCallBack) {
        //先获取内存中的Bitmap
        final String key = url.substring(url.indexOf("/") + 1, url.length());

        Bitmap bitmap = getBitmapFromMemCache(key);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (mCallBack != null)
                    mCallBack.onImageLoader((Bitmap) msg.obj, url);
            }

        };

        //若该Bitmap不在内存缓存中，则读取本地私有目录的图片，则
        if (bitmap != null) {
            return bitmap;
        } else {

            final String dirPath = context.getExternalCacheDir().getAbsolutePath();
//            Log.e(TAG, "loadNetImage: dirPath " + dirPath);
            Bitmap fileBitmap = FileUtil.readBitmap(dirPath, key);

            if (fileBitmap != null) {
//                Log.e(TAG, "loadNetImage: fileBitmap : width / height " + fileBitmap.getWidth() + " / " + fileBitmap.getHeight());
//                if (mCallBack != null) {
//                    mCallBack.onImageLoader(fileBitmap, url);
//                    Log.e(TAG, "loadNetImage: " + fileBitmap);
//                }
                addBitmapToMemoryCache(key, fileBitmap);

                return fileBitmap;

            } else {//若本地也没有,启用线程去加载网络图片，并将Bitmap加入到mMemoryCache中
                mImageThreadPool.execute(new Runnable() {

                    @Override
                    public void run() {
                        //先获取图片的缩略图
                        Bitmap mBitmap = decodeBitmapForNet(url, mPoint == null ? 0 : mPoint.x, mPoint == null ? 0 : mPoint.y);
                        Message msg = handler.obtainMessage();
                        msg.obj = mBitmap;
                        handler.sendMessage(msg);
                        //将图片加入到内存缓存
                        addBitmapToMemoryCache(key, mBitmap);
                        //将图片加入到私有目录
//                        Log.e(TAG, "run: mBitmap" + mBitmap.getByteCount());
                        FileUtil.writeBitmap(mBitmap, dirPath, key, Bitmap.CompressFormat.JPEG);
//////////

                    }
                });
            }
        }

        return null;
    }

    /**
     * 往内存缓存中添加Bitmap
     *
     * @param key
     * @param bitmap
     */
    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null && bitmap != null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * 根据key来获取内存中的图片
     *
     * @param key
     * @return
     */
    private Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 根据View(主要是ImageView)的宽和高来获取图片的缩略图,
     *
     * @param url
     * @param viewWidth
     * @param viewHeight
     * @return
     */
    private Bitmap decodeBitmapForNet(String url, int viewWidth, int viewHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        byte[] bitmapByte = getBitmapByte(url);
        if (bitmapByte != null) {
            //设置为true,表示解析Bitmap对象，该对象不占内存
            options.inJustDecodeBounds = true;

            BitmapFactory.decodeByteArray(bitmapByte, 0, bitmapByte.length, options);
            //设置缩放比例
            options.inSampleSize = computeScale(options, viewWidth, viewHeight);

            //设置为false,解析Bitmap对象加入到内存中
            options.inJustDecodeBounds = false;
//            Log.e(TAG, "decodeBitmapForNet: options.inSampleSize =  " + options.inSampleSize);

            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapByte, 0, bitmapByte.length, options);
//            Log.e(TAG, "decodeBitmapForNet: bitmap.getByteCount() kb= " + (bitmap.getByteCount() / 1024) + " width / height" + bitmap.getWidth() + " / " + bitmap.getHeight());
            return bitmap;
        }
        return null;
//        return BitmapFactory.decodeStream(bitmapInputStream2, null, options);
    }

    /**
     * 访问网络
     *
     * @param url 图片路径
     * @return
     */
    private byte[] getBitmapByte(String url) {
        InputStream inputStream = null;
        ByteArrayOutputStream baos = null;
        try {
            URL imgUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) imgUrl.openConnection();
            connection.setReadTimeout(10000);
            connection.connect();
            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                inputStream = connection.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = -1;
                while ((len = inputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                byte[] bytes = baos.toByteArray();
//                Log.e(TAG, "getBitmapByte: " + bytes.length);
                return bytes;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (baos != null)
                    baos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 根据View(主要是ImageView)的宽和高来计算Bitmap缩放比例。默认不缩放
     *
     * @param options
     * @param viewWidth
     * @param viewHeight
     * @return
     */
    private int computeScale(BitmapFactory.Options options, int viewWidth, int viewHeight) {

        int bitmapWidth = options.outWidth;
        int bitmapHeight = options.outHeight;
//        Log.e(TAG, "decodeBitmapForNet: viewWidth / viewHeight" + viewWidth + " / " + viewHeight + "::: bitmapWidth  / bitmapHeight " + bitmapWidth + " / " + bitmapHeight);

        int inSampleSize = 1;
        if (viewWidth == 0 || viewHeight == 0) {
            return inSampleSize;
        }
        //假如Bitmap的宽度或高度大于我们设定图片的View的宽高，则计算缩放比例,因为图片可能很大，所以要++

        if (bitmapWidth > viewWidth || bitmapHeight > viewHeight) {
            int widthScale = Math.round((float) bitmapWidth / (float) viewWidth);
            int heightScale = Math.round((float) bitmapHeight / (float) viewHeight);
            //为了保证图片不缩放变形，我们取宽高比例最小的那个
            inSampleSize = (widthScale < heightScale ? widthScale : heightScale);
//            Log.e(TAG, "computeScale: inSampleSize = " + inSampleSize);
        }
//        while (bitmapWidth / inSampleSize > viewWidth || bitmapHeight / inSampleSize > viewHeight) {
//            inSampleSize *= 2;
//        }

//        Log.e(TAG, "computeScale: " + inSampleSize);
        return inSampleSize;
    }


    /**
     * 加载网络图片的回调接口
     */
    public interface NativeImageCallBack {
        /**
         * 当子线程加载完了网络的图片，将Bitmap和图片路径回调在此方法中
         *
         * @param bitmap
         * @param path
         */
        void onImageLoader(Bitmap bitmap, String path);
    }
}
