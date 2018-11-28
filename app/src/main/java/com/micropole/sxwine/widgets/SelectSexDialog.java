package com.micropole.sxwine.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

import com.micropole.sxwine.R;

/**
 * Created by JohnnyH on 2018/6/14.
 */

public class SelectSexDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    public SelectSexDialog(@NonNull Context context) {
        super(context, R.style.Animation_Bottom_Rising);
        this.mContext=context;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setCanceledOnTouchOutside(false);
        initUI();
    }

    private void initUI() {
        View view = View.inflate(mContext, R.layout.view_select_sex_dialog, null);
        view.findViewById(R.id.tv_man).setOnClickListener(this);
        view.findViewById(R.id.tv_woman).setOnClickListener(this);
        view.findViewById(R.id.tv_secrecy).setOnClickListener(this);
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        setContentView(view);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.tv_man ://男
                if (mListener!=null){
                mListener.OnSelectSex("1");
                }
                break;
            case  R.id.tv_woman ://女
                if (mListener!=null){
                    mListener.OnSelectSex("2");
                }
                break;
            case  R.id.tv_secrecy ://保密
                if (mListener!=null){
                    mListener.OnSelectSex("3");
                }
                break;
            case  R.id.tv_cancel ://取消
                dismiss();
                break;
        }

    }

    private OnSelectSexListener mListener;

    public interface OnSelectSexListener{
        void OnSelectSex(String type);
    }

    public void setOnSelectSexListener(OnSelectSexListener onSelectSexListener){
        mListener=onSelectSexListener;
    }
}
