package com.micropole.sxwine.module.personal.mvp.contract;

import android.content.Context;

import com.example.mvpframe.BaseMvpView;

import java.util.List;

/**
 * Created by JacobHHH on 2017/9/29.
 * 图片预览/删除页面的view接口
 */

public class ImageViewerContract {

    public interface View extends BaseMvpView {
        /**
         * 删除除图片结果回调
         *
         * @param isSuccess 删除是否成功，true表示成功，false表示失败
         * @param error     当isSuccess为false时有值可取
         */
        void deleteResult(boolean isSuccess, String error);
    }

    public interface Presenter{
        /**
         * 删除一张图片
         *
         * @param context
         * @param list           图片集合
         * @param deletePosition 需要删除的图片的位置
         */
        void deleteImg(Context context, List<String> list, int deletePosition);
    }

    public interface Model{

    }

}
