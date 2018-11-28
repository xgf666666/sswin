package com.micropole.sxwine.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.micropole.sxwine.R;

/**
 * Created by JohnnyH on 2018/6/14.
 */

public class WithdrawDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private ImageView mIv_pay_1;
    private ImageView mIv_pay_2;
    private ImageView mIv_pay_3;

    public WithdrawDialog(@NonNull Context context) {
        super(context, R.style.Animation_Bottom_Rising);
        this.mContext=context;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setCanceledOnTouchOutside(false);
        initUI();
    }

    private void initUI() {
        View view = View.inflate(mContext, R.layout.view_withdraw_dialog, null);
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        view.findViewById(R.id.ll_pay_1).setOnClickListener(this);
        view.findViewById(R.id.ll_pay_2).setOnClickListener(this);
        view.findViewById(R.id.ll_pay_3).setOnClickListener(this);
        mIv_pay_1 = view.findViewById(R.id.iv_pay_1);
        mIv_pay_2 = view.findViewById(R.id.iv_pay_2);
        mIv_pay_3 = view.findViewById(R.id.iv_pay_3);
        setContentView(view);
    }

    @Override
    public void show() {
        super.show();
    }

    private String mType="PAYPAL";
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_pay_1:
                mType="PAYPAL";
                changeUI(mType);
                break;
            case R.id.ll_pay_2:
                mType="IPAY88";
                changeUI(mType);
                break;
            case R.id.ll_pay_3:
                mType="MOL";
                changeUI(mType);
                break;
        }
        if (mOnSelectPayModelListener!=null){
            mOnSelectPayModelListener.selectPayModel(mType);
            dismiss();
        }
    }

    public void setPayModel(String payModel){
        if (TextUtils.isEmpty(payModel)){
            return;
        }
        mType=payModel;
        changeUI(mType);
    }

    private void changeUI(String type){
        setDefUI();
        switch (type){
            case "PAYPAL":
                mIv_pay_1.setImageResource(R.mipmap.home_btn_confirm_red);
                break;
            case "IPAY88" :
                mIv_pay_2.setImageResource(R.mipmap.home_btn_confirm_red);
                break;
            case "MOL" :
                mIv_pay_3.setImageResource(R.mipmap.home_btn_confirm_red);
                break;
        }
    }

    private void  setDefUI(){
        mIv_pay_1.setImageResource(R.mipmap.home_btn_input);
        mIv_pay_2.setImageResource(R.mipmap.home_btn_input);
        mIv_pay_3.setImageResource(R.mipmap.home_btn_input);
    }

    private OnSelectPayModelListener mOnSelectPayModelListener;

    public interface OnSelectPayModelListener{
        void selectPayModel(String type);
    }

    public void setOnSelectPayModelListener(OnSelectPayModelListener listener){
        mOnSelectPayModelListener=listener;
    }
}
