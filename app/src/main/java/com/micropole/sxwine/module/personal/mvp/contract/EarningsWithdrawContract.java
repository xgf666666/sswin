package com.micropole.sxwine.module.personal.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.module.personal.Bean.BankRecordEntity;
import com.micropole.sxwine.module.personal.Bean.CheckPayPwdEntity;
import com.micropole.sxwine.module.personal.Bean.EarningsWithdrawEntity;
import com.micropole.sxwine.utils.network.HttpObserver;

/**
 * Created by JohnnyH on 2018/6/14.
 */

public class EarningsWithdrawContract {

    public interface View extends BaseMvpView{
        void onCheckPayPwdSuccess(CheckPayPwdEntity data);
        void onCheckPayPwdFailure(String err);

        void onEarningsWithdrawSuccess(EarningsWithdrawEntity data);
        void onEarningsWithdrawFailure(String err);

        void onGetBankRecordSuccess(BankRecordEntity data);
    }

    public interface Presenter {
        void checkPayPwd();

        void earningsWithdraw(String account,String type,String withdraw_amount,String deal_password);

        void earningsWithdraw2(String account,String card_holder,String withdraw_amount,String deal_password,String bank_name,String swift_code,String bank_address);

        void getBankRecord();
    }

    public interface Model{
        void checkPayPwd(HttpObserver<CheckPayPwdEntity> httpObserver);

        void earningsWithdraw(String account,String type,String withdraw_amount,String deal_password,
                              HttpObserver<EarningsWithdrawEntity> httpObserver);

        void earningsWithdraw2(String account,String card_holder,String withdraw_amount,String deal_password
                ,String bank_name,String swift_code,String bank_address,HttpObserver<EarningsWithdrawEntity> httpObserver);

        void getBankRecord(HttpObserver<BankRecordEntity> httpObserver);
    }
}
