package com.micropole.sxwine.module.personal.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.darsh.multipleimageselect.utils.ImageLoader;

import java.util.List;

import uk.co.senab.photoview.PhotoView;


/**
 * 日期：2017.01.06
 * <p>
 * 作者：xudiwei
 * <p>
 * 描述：：图片预览/删除页面的适配器
 */
public class ImageViewerAdapter extends PagerAdapter {

    private static final String TAG = "ImageViewerAdapter";

    private Context mContext;
    private List<String> mList;
    private boolean mIsNetworkImg;
    private OnImageLongClickListener mImageLongClickListener;

    public ImageViewerAdapter(Context context, List<String> list, boolean isNetworkImg) {
        mContext = context;
        mList = list;
        this.mIsNetworkImg = isNetworkImg;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(mContext).inflate(com.darsh.multipleimageselect.R.layout.item_preview, container, false);
        PhotoView photoView = (PhotoView) view.findViewById(com.darsh.multipleimageselect.R.id.photoView);
        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        //点击事件
        if (null != mImageLongClickListener) {
            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mImageLongClickListener.onImageLongClick(position, v);
                    return false;
                }
            });
        }

        String url = mList.get(position);
        Log.d(TAG,"url: "+url);
        if (mIsNetworkImg) {
//            ImageLoader.loadToUrl(mContext, photoView, MyApi.IMAGE_PREFIX + mList.get(position));
            ImageLoader.loadToUrl(mContext, photoView, url);
        } else {
            ImageLoader.loadToUrl(mContext, photoView, url);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
//        return super.getItemPosition(object);
    }

    public void setOnImageLongClickListener(OnImageLongClickListener listener) {
        this.mImageLongClickListener = listener;
    }

    public interface OnImageLongClickListener {
        void onImageLongClick(int position, View view);
    }

}
