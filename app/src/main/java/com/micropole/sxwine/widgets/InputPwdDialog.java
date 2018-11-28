package com.micropole.sxwine.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.micropole.sxwine.R;


/**
 * Created by: xudiwei
 * <p>
 * on: 2017/4/10.
 * <p>
 * 描述：观看直播需要密码的输入框
 */

public class InputPwdDialog extends BaseDialog implements PasswordView.InputPswConfirmLengthListener, View.OnClickListener {

    private OnPswDialogClickListener mListener;
    private final TextView mTvGetMethod;
    private final PasswordView pswView;
    private Context mContext;

    public InputPwdDialog(Context context) {
        super(context, View.inflate(context, R.layout.view_input_pay_psw_dialog, null), R.style.Animation_Bottom_Rising);
        mContext=context;
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        findViewById(R.id.tv_confirm).setOnClickListener(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.iv_cancel).setOnClickListener(this);
        findViewById(R.id.tv_forget_pwd).setOnClickListener(this);
        mTvGetMethod = (TextView) findViewById(R.id.tv_get_method);
        pswView = (PasswordView) findViewById(R.id.psw_view);
        pswView.setFocusable(true);
        //显示软键盘
      /*  InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(pswView, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);*/
        pswView.setInputPswConfirmLengthListener(this);
        setCanceledOnTouchOutside(false);

        mTvGetMethod.setVisibility(View.GONE);
//        link();
    }

    private void link() {
        SpannableString spannableString = new SpannableString("11111111111111111111111111");
        spannableString.setSpan(new MyClickableSpan(), 9, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvGetMethod.setText(spannableString);
        mTvGetMethod.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        /*if (v.getId() == R.id.tv_confirm && null != mListener) {
            mListener.onConfirm(pswView.getText().toString().trim());
        } else if (v.getId() == R.id.tv_cancel) {
            dismiss();
        }*/
        switch (v.getId()){
            case R.id.iv_cancel://X按钮
                hintKb();
                dismiss();
                break;
            case R.id.tv_forget_pwd://忘记密码
                if (mOnForgetClickListener!=null){
                    mOnForgetClickListener.forgetClick();
                }
                break;
        }
    }

    private OnForgetClickListener mOnForgetClickListener;

    public interface OnForgetClickListener{
        void forgetClick();
    }

    public void setOnForgetClickListener(OnForgetClickListener listener){
        mOnForgetClickListener=listener;
    }

    /**
     * 隐藏软键盘
     */
    public void hintKb() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        /**点击空白处隐藏键盘*/
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ((Activity) mContext).finish();
    }

    public void setOnPswDialogClickListener(OnPswDialogClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void InputPswConfirmLength() {
        if (mListener!=null){
            mListener.onConfirm(pswView.getText().toString().trim());
            hintKb();
        }
    }

    public interface OnPswDialogClickListener {
        void onConfirm(String password);

        void onLinkClick();
    }

    private class MyClickableSpan extends ClickableSpan {

        @Override
        public void onClick(View widget) {
            if (null != mListener) {
                mListener.onLinkClick();
            }
        }
    }
}
