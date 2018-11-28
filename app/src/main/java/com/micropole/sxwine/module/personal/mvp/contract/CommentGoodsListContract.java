package com.micropole.sxwine.module.personal.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.module.personal.Bean.CommentGoodsListEntity;
import com.micropole.sxwine.utils.network.HttpObserver;

import java.util.List;

/**
 * Created by JohnnyH on 2018/9/7.
 */

public class CommentGoodsListContract {

    public interface View extends BaseMvpView{

        void setData(List<CommentGoodsListEntity> data);

        void onDataFailure(String err);
    }

    public interface Presenter {

        void loadData(String order_id);
    }

    public interface Model{

        void loadData(String order_id, HttpObserver<List<CommentGoodsListEntity>> httpObserver);
    }

}
