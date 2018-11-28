package com.micropole.sxwine.module.personal.mvp.presenter;

import android.content.Context;

import com.example.mvpframe.BaseMvpPresenter;
import com.micropole.sxwine.module.personal.mvp.contract.ImageViewerContract;

import java.util.List;



/**
 * Created by JacobHHH on 2017/9/29.
 */

public class ImageViewerPresenterImpl extends BaseMvpPresenter<ImageViewerContract.Model,ImageViewerContract.View> implements ImageViewerContract.Presenter {


    @Override
    public void deleteImg(Context context, List<String> list, int deletePosition) {
        try {
            list.remove(deletePosition);
            if (null != getMView()) {
                getMView().deleteResult(true, "");
            }
        } catch (Exception e) {
            if (null != getMView()) {
                getMView().deleteResult(false, "删除失败");
            }
        }
    }


    @Override
    public ImageViewerContract.Model createModel() {
        return null;
    }
}
