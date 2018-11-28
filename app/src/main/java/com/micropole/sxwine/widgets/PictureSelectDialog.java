package com.micropole.sxwine.widgets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import com.micropole.sxwine.R;


/**
 * Created by: xudiwei
 * <p>
 * on: 2017/4/13.
 * <p>
 * 描述：动态创建页面的图片获取方式选择对话框
 */

public class PictureSelectDialog extends BaseMyDialog {

    private OnPictureSelectDialogClickListener mListener;

    public PictureSelectDialog(Context context) {
        super(context, View.inflate(context, R.layout.view_picture_select_dialog, null), R.style.Animation_Bottom_Rising);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        findViewById(R.id.tv_camera).setOnClickListener(this);
        findViewById(R.id.tv_photo).setOnClickListener(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_camera:
                if (null != mListener) {
                    mListener.onCamera();
                }
                break;
            case R.id.tv_photo:
                if (null != mListener) {
                    mListener.onPhoto();
                }
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    public void setOnPictureSelectDialogClickListener(OnPictureSelectDialogClickListener listener) {
        this.mListener = listener;

    }

    public interface OnPictureSelectDialogClickListener {

        /**
         * 拍照
         */
        void onCamera();

        /**
         * 从手机相册选择
         */
        void onPhoto();
    }
}
