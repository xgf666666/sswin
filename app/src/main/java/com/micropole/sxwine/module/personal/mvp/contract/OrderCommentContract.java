package com.micropole.sxwine.module.personal.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.utils.network.HttpObserver;

import java.util.List;

/**
 * Created by JohnnyH on 2018/9/7.
 */

public class OrderCommentContract {

    public interface View extends BaseMvpView {

        void setImageResult(List<String> pictures);

        void onCommentSuccess(String msg);
        void onCommentFailure(String err);
    }

    public interface Presetner {

        /**
         * 从图片选择器或是拍照后返回
         *
         * @param newImageLists
         */
        void imageSelect(List<String> newImageLists);

        /**
         * 从图片预览页面后返回
         *
         * @param newImageLists
         */
        void imageDelete(List<String> newImageLists);

        void submitComment(String order_id,String goods_id,String score,String content,List<String> photos);
    }

    public interface Model {
        void submitComment(String order_id, String goods_id, String score, String content, List<String> photos, HttpObserver<Object> httpObserver);
    }
}
