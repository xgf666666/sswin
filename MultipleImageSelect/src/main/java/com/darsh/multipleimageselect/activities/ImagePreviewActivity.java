package com.darsh.multipleimageselect.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.darsh.multipleimageselect.R;
import com.darsh.multipleimageselect.adapters.ImagePreviewAdapter;
import com.darsh.multipleimageselect.models.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片预览
 */
public class ImagePreviewActivity extends HelperActivity {

    private static final String TAG = "ImagePreviewActivity";
    /**
     * 打开当前Activity时所需要带过来的数据所对应的key
     */
    public static final String IMGS_DATA_KEY = "images";
    /**
     * 打开当前Activity时所需要带过来的数据
     */
    public List<Image> mList = new ArrayList<>();
    private ViewPager mVpPreview;
    private ImagePreviewAdapter mAdapter;
    private TextView mTvIndex;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        initView();

        initData();
    }

    /**
     * init view
     */
    private void initView() {
        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new MyOnClickListener());

        mTvIndex = (TextView) findViewById(R.id.tv_index);

        mVpPreview = (ViewPager) findViewById(R.id.vp_preview);

    }

    /**
     * init data
     */
    private void initData() {

        //data
        ArrayList<Image> images = getIntent().getParcelableArrayListExtra(IMGS_DATA_KEY);
        mList.addAll(images);
        count = mList.size();

        //viewpager
        mAdapter = new ImagePreviewAdapter(this, mList);
        mVpPreview.setAdapter(mAdapter);
        mVpPreview.addOnPageChangeListener(new MyOnPageChangeListener());

        //init index
        mTvIndex.setText(getString(R.string.text_select_picture_count, 1, count));
    }

    /**
     * item click event
     */
    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.iv_back) {
                finish();
            }
        }
    }

    /**
     * 滑动监听
     */
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mTvIndex.setText(getString(R.string.text_select_picture_count, position + 1, count));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
