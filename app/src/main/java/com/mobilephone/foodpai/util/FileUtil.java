package com.mobilephone.foodpai.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/10/25.
 */
public class FileUtil {
    private static final String TAG = "FileUtil-test";

    /**
     * 将Bitmap对象存储到指定的路径下
     *
     * @param bitmap
     * @param dirPath
     * @param fileName
     * @param format
     * @return
     */
    public static boolean writeBitmap(Bitmap bitmap, String dirPath, String fileName, Bitmap.CompressFormat format) {
        FileOutputStream fos = null;
        File file = null;
        try {
            File dirFile = createDir(dirPath);
            if (dirFile == null) {
                return false;
            }
            file = createFile(dirFile, fileName);
            if (file == null) {
                return false;
            }
            fos = new FileOutputStream(file, false);
            bitmap.compress(format, 100, fos);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "writeBitmap: " + e.getMessage() + "  /  " + dirPath);
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 在给定目录中创建目标文件
     *
     * @param fileName
     * @param dirFile
     * @return
     */
    public static File createFile(File dirFile, String fileName) {
        try {
            File file = new File(dirFile.getAbsolutePath() + File.separator + fileName);
            if (!file.exists() || file.isDirectory()) {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    return null;
                }
            }
            return file;
        } catch (Exception e) {
            Log.e(TAG, "createFile: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap readBitmap(String dirPath, String fileName) {
        FileInputStream fis = null;

        try {
            boolean exists = new File(dirPath, fileName).exists();
            if (!exists) {
                return null;
            }
            File file = new File(dirPath + File.separator + fileName);
            //读入内容并转化为图片
            fis = new FileInputStream(file);
            return BitmapFactory.decodeStream(fis);


        } catch (Exception e) {
            Log.e(TAG, "readBitmap: " + e.getMessage());
            e.printStackTrace();

        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


        return null;
    }

    /**
     * 根据给定的路径创建文件夹
     *
     * @param dirPath
     * @return
     */
    public static File createDir(String dirPath) {
        File dirFile = new File(dirPath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            boolean success = dirFile.mkdirs();
            if (!success) {
                return null;
            }
        }

        return dirFile;
    }
}
