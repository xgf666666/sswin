package com.darsh.multipleimageselect.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by: UFO on: 2016/11/2.
 * <p>
 * 描述：文件工具类
 */

public class FileUtils {
    private static final String TAG = "FileUtils";

    /**
     * 根据路径创建一个在sd卡目录下的一个文件夹
     *
     * @param dirPath 文件夹名
     * @return 返回创建成功的File对象或是null.
     */
    public static File createDirToSDCard(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            throw new IllegalArgumentException("dir path can not empty");
        }

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir; /*= Environment.getExternalStoragePublicDirectory(dirPath);*/
            dir = new File(Environment.getExternalStorageDirectory(), dirPath);
            boolean mkdirs = dir.mkdirs() || dir.isDirectory();
            return dir;
        }
        return null;
    }

}
