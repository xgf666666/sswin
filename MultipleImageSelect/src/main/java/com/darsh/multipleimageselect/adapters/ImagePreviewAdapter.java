package com.darsh.multipleimageselect.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.darsh.multipleimageselect.R;
import com.darsh.multipleimageselect.models.Image;
import com.darsh.multipleimageselect.utils.ImageLoader;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by: UFO on: 2017/1/6.
 * <p>
 * 描述：
 */

public class ImagePreviewAdapter extends PagerAdapter {

    private static final String TAG = "BannerViewAdapter";

    private Context mContext;
    private List<Image> mList;

    public ImagePreviewAdapter(Context context, List<Image> list) {
        mContext = context;
        mList = list;
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
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_preview, container, false);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.photoView);
        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageLoader.loadToUrl(mContext, photoView, mList.get(position).path);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
