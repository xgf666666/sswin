package com.micropole.sxwine.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.micropole.sxwine.R;

/**
 * Created by JohnnyH on 2018/8/14.
 */

public class SelectDocumentDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private ImageView mIv_my;
    private ImageView mIv_cn;

    public SelectDocumentDialog(@NonNull Context context) {
        super(context);
        mContext=context;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.view_select_doucment_dialog, null);
        view.findViewById(R.id.ll_my).setOnClickListener(this);
        view.findViewById(R.id.ll_cn).setOnClickListener(this);
        mIv_my = view.findViewById(R.id.iv_my);
        mIv_cn = view.findViewById(R.id.iv_cn);
        setContentView(view);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.ll_my){
            mIv_my.setSelected(true);
            mIv_cn.setSelected(false);
            if (mOnSelectStateListener!=null){
                mOnSelectStateListener.selectState("2");
                dismiss();
            }
        }else {
            mIv_my.setSelected(false);
            mIv_cn.setSelected(true);
            if (mOnSelectStateListener!=null){
                mOnSelectStateListener.selectState("1");
                dismiss();
            }
        }
    }

    public void setSelectState(String type){
        if (type=="2"){
            mIv_my.setSelected(true);
            mIv_cn.setSelected(false);
        }else {
            mIv_my.setSelected(false);
            mIv_cn.setSelected(true);
        }
    }

    @Override
    public void show() {
        super.show();
    }

    private OnSelectStateListener mOnSelectStateListener;

    public interface OnSelectStateListener {
        void selectState(String type);
    }

    public void setOnSelectStateListener(OnSelectStateListener listener){
        mOnSelectStateListener=listener;
    }

}
