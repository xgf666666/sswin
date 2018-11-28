package com.micropole.sxwine.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


public class BaseMyDialog extends Dialog implements View.OnClickListener {

    protected Context mContext;
    private View view;
    private boolean isDismiss = true;
    private TextView mTv_loading;

    public BaseMyDialog(Context context) {
        super(context);
    }

    public BaseMyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public BaseMyDialog(Context context, View view, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        this.view = view;
        this.view.setOnClickListener(this);
        setContentView(view);
    }

    @Override
    public void show() {
        super.show();

        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth(); //设置宽度
        getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        if (isDismiss) {
            dismiss();
        }
    }

    /**
     * 当点击对话框外面时是否关闭对话框,默认是要关闭对话框
     *
     * @param b
     */
    public void clickOutsideDismiss(boolean b) {
        this.isDismiss = b;
    }


}
