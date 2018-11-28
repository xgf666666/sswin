package com.micropole.sxwine.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.micropole.sxwine.BuildConfig;
import com.micropole.sxwine.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * 图片选择工具
 * 作者: chigankaiqiang on 2016/9/30 11:26
 */

public class ImageChooseHelper implements View.OnClickListener {

    /**
     * 相机获取图片
     */
    private final int REQUEST_CODE_PHOTO = 1 ;
    /**
     * 图库获取图片
     */
    private final int REQUEST_CODE_ALBUM = 2;
    /**
     * 图片裁剪
     */
    private final int REQUEST_CODE_CROP = 3 ;
    /**
     * 取消选图
     */
    private final int REQUEST_CODE_CANCEL = 0;
    /**
     * 头像尺寸
     * 默认150
     */
    private int headSize = 150;

    /**
     * 头像文件
     */
    private File file;

    /**
     * 是否开启裁剪
     * 默认开启
     */
    private boolean isCrop = true;

    /**
     * 使用弱引用保存Activity实例
     */
    private WeakReference<Activity> activityWeakReference;
    private WeakReference<FragmentActivity> fragmentActivityWeakReference;
    private WeakReference<Fragment> fragmentWeakReference;

    private OnFinishChooseImageListener listener;
    private AlertDialog alertDialog;
    private AlertDialog defaultDialog;
    private OnFinishChooseAndCropImageListener mOnFinishChooseAndCropImageListener;

    public ImageChooseHelper(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }

    public ImageChooseHelper(Fragment fragment) {
        fragmentWeakReference = new WeakReference<>(fragment);
        fragmentActivityWeakReference = new WeakReference<>(fragment.getActivity());
    }

    /**
     * Android N下获取文件Uri的正确姿势
     *
     * @param file 文件
     * @return
     */
    private Uri getUri(File file) {
        if (file == null)
            throw new NullPointerException("文件不存在");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileprovider", file);
        } else {
            return Uri.fromFile(file);
        }
    }

    /**
     * 获取弱引用中的Activity实例
     *
     * @return activity
     */
    private Activity getActivity() {
        if (fragmentActivityWeakReference != null) {
            return fragmentActivityWeakReference.get();
        }
        return activityWeakReference.get();
    }

    /**
     * 初始化文件名
     */
    private File initFile(String fileName) {
        String dirPath = Environment.getExternalStorageDirectory() + "/" + getActivity().getPackageName();
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();
        file = new File(dir, fileName);
        return file;
    }

    /**
     * 设置头像裁剪尺寸
     *
     * @param headSize 头像矩形裁剪尺寸
     */
    public void setHeadSize(int headSize) {
        this.headSize = headSize;
    }

    /**
     * 设置是否开启裁剪
     *
     * @param isCrop
     */
    public void setCrop(boolean isCrop) {
        this.isCrop = isCrop;
    }

    /**
     * 进入相机
     */
    public void startCamearPicCut() {
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra("camerasensortype", 2);// 调用前置摄像头
        intent.putExtra("autofocus", true);// 自动对焦
        intent.putExtra("fullScreen", false);// 全屏
        intent.putExtra("showActionIcons", false);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        // 指定调用相机拍照后照片的储存路径
        initFile(System.currentTimeMillis() + ".jpg");
       intent.putExtra(MediaStore.EXTRA_OUTPUT, getUri(file));

        if (fragmentWeakReference != null) {
            fragmentWeakReference.get().startActivityForResult(intent, REQUEST_CODE_PHOTO);
        } else {
            if (getActivity() != null) {
                getActivity().startActivityForResult(intent, REQUEST_CODE_PHOTO);
            }
        }

    }

    /**
     * 进入图库选图
     */
    public void startImageChoose() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");

        //进入选图
        if (fragmentWeakReference != null) {
            fragmentWeakReference.get().startActivityForResult(intent, REQUEST_CODE_ALBUM);
        } else {
            getActivity().startActivityForResult(intent, REQUEST_CODE_ALBUM);
        }


    }

    /**
     * 获取图片的ContextUri
     *
     * @param imageFile 图片文件
     * @return ContextUri
     */
    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getActivity().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getActivity().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * 裁剪照片
     *
     * @param uri  裁剪uri
     * @param size 裁剪尺寸
     */
    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);

        if (fragmentWeakReference != null) {
            fragmentWeakReference.get().startActivityForResult(intent, REQUEST_CODE_CROP);
        } else {
            getActivity().startActivityForResult(intent, REQUEST_CODE_CROP);
        }
    }

    /**
     * 设置图片选择对话框
     *
     * @param alertDialog 图片选择对话框
     */
    public void setDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    /**
     * 显示选择图片的对话框
     */
    public void showChooseImage() {
        if (alertDialog != null) {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            } else {
                alertDialog.show();
            }
        } else {
            if (defaultDialog == null) {
                initDefaultDialog();
            }
            if (defaultDialog.isShowing()) {
                defaultDialog.dismiss();
            } else {
                defaultDialog.show();
            }
        }

    }

    /**
     * 隐藏对话框
     */
    public void dismissChooseImage() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        } else if (defaultDialog != null && defaultDialog.isShowing()) {
            defaultDialog.dismiss();
        }

    }

    /**
     * 结果处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_CODE_PHOTO:
                if (isCrop) {
                    startPhotoZoom(getImageContentUri(file), headSize);
                } else {
                    if (listener != null) {
                        //相机，不裁剪,直接返回Uri和照片文件
                        listener.onFinish(getImageContentUri(file), file);
                    }
                }
                break;
            case REQUEST_CODE_ALBUM:

                if (data == null) {
                    return;
                }
                if (isCrop) {
                    startPhotoZoom(data.getData(), headSize);
                } else {
                    if (listener != null) {
                        //不裁剪,直接返回Uri
                        listener.onFinish(data.getData(), null);
                    }
                }
                break;
            case REQUEST_CODE_CROP:
                if (data == null) {
                    return;
                }
                if (data.getExtras() != null) {
                    Bundle bundle = data.getExtras();
                    Bitmap photo = bundle.getParcelable("data");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    if (photo != null) {
                        photo.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    }

                    FileOutputStream fos = null;
                    if (mOnFinishChooseAndCropImageListener != null) {

                        try {
                            if (file != null) {
                                file.getParentFile().delete();//删除照片
                            }
                            //将裁剪出来的Bitmap转换成本地文件
                            File file = initFile("image.png");
                            fos = new FileOutputStream(file);
                            fos.write(baos.toByteArray());
                            fos.flush();
                            //通知图库有更新
                            getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));

                            //裁剪过后返回Bitmap,处理生成文件用来上传
                            mOnFinishChooseAndCropImageListener.onFinish(photo, file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (fos != null)
                                try {
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            if (baos != null)
                                try {
                                    baos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                        }
                    }

                }
                break;
        }
    }

    /**
     * 初始化默认图片选择对话框
     */
    private void initDefaultDialog() {
        if (getActivity() == null) {
            return;
        }
        if (defaultDialog == null) {
            View contentView = LayoutInflater.from(getActivity()).inflate(
                    R.layout.popup_picture, null);
            defaultDialog = new AlertDialog.Builder(getActivity(), R.style.Theme_Design_BottomSheetDialog)
                    .setView(contentView)
                    .create();
            contentView.findViewById(R.id.tv_camera).setOnClickListener(this);
            contentView.findViewById(R.id.tv_picture).setOnClickListener(this);
            contentView.findViewById(R.id.tv_cancel).setOnClickListener(this);
            Window window = defaultDialog.getWindow();
            if (window != null) {
                window.setGravity(Gravity.BOTTOM);//设置窗口位于底部
                window.setWindowAnimations(android.R.style.Animation_InputMethod);//键盘弹出动画
                window.getDecorView().setPadding(0, 0, 0, 0);
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(lp);
            }
        }
    }

    /**
     * 图片裁剪返回监听
     *
     * @param listener
     */
    public void setOnFinishChooseImageListener(OnFinishChooseImageListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_camera:
                startCamearPicCut();
                break;
            case R.id.tv_picture:
                startImageChoose();
                break;
            case R.id.tv_cancel:
                break;
        }
        dismissChooseImage();
    }

    /**
     * 设置选图裁剪回调监听
     *
     * @param onFinishChooseAndCropImageListener 选图裁剪回调监听
     */
    public void setOnFinishChooseAndCropImageListener(OnFinishChooseAndCropImageListener onFinishChooseAndCropImageListener) {
        mOnFinishChooseAndCropImageListener = onFinishChooseAndCropImageListener;
    }

    /**
     * 裁剪图片完成回调监听
     */
    public interface OnFinishChooseAndCropImageListener {
        void onFinish(Bitmap bitmap, File file);
    }

    /**
     * 仅选图完成回调监听
     */
    public interface OnFinishChooseImageListener {
        void onFinish(Uri uri, File file);
    }
}
