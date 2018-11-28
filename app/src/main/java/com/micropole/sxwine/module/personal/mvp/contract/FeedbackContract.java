package com.micropole.sxwine.module.personal.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.utils.network.HttpObserver;

/**
 * Created by JohnnyH on 2018/6/15.
 */

public class FeedbackContract {

    public interface View extends BaseMvpView {
        void onFeedbackSuccess(String msg);
        void onFeedbackFailure(String err);
    }

    public interface Presenter {
        void feedback(String content);
    }

    public interface Model {
        void feedback(String content, HttpObserver<Object> httpObserver);
    }
}
