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
 * Created by JohnnyH on 2018/10/11.
 */

public class TextDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    public TextDialog(@NonNull Context context) {
        super(context);
        this.mContext=context;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setCancelable(false);
        initView();
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.view_text_dialog, null);
        view.findViewById(R.id.tv_confirm).setOnClickListener(this);
        setContentView(view);


    }

    @Override
    public void onClick(View view) {
        if (mOnConfirmClickListener!=null){
            mOnConfirmClickListener.onConfirmClick();
            dismiss();
        }
    }

    @Override
    public void show() {
        super.show();
    }

    private OnConfirmClickListener mOnConfirmClickListener;

    public interface OnConfirmClickListener {
        void onConfirmClick();
    }

    public void setOnConfirmClickListener(OnConfirmClickListener listener){
        mOnConfirmClickListener=listener;
    }
}
