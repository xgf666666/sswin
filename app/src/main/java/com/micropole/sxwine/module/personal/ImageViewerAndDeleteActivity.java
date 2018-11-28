package com.micropole.sxwine.module.personal;

import android.Manifest;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvpframe.BaseMvpActivity;
import com.micropole.sxwine.R;
import com.micropole.sxwine.module.personal.adapter.ImageViewerAdapter;
import com.micropole.sxwine.module.personal.mvp.contract.ImageViewerContract;
import com.micropole.sxwine.module.personal.mvp.presenter.ImageViewerPresenterImpl;

import java.util.ArrayList;

/**
 * 日期：2017.01.03
 * <p>
 * 作者：xudiwei
 * <p>
 * 描述：图片预览/删除页面
 * <p>
 * sample:
 * <p>
 * Intent intent = new Intent(context, ImageViewerAndDeleteActivity.class);
 * intent.putStringArrayListExtra(ImageViewerAndDeleteActivity.IMGS_DATA_KEY, new List<>String</>);
 * intent.putExtra(ImageViewerAndDeleteActivity.INDEX_KEY, 1);
 * startActivityForResult(intent, 100);
 */
public class ImageViewerAndDeleteActivity extends BaseMvpActivity<ImageViewerContract.Model,ImageViewerContract.View, ImageViewerPresenterImpl>
        implements ImageViewerContract.View, View.OnClickListener {

    private static final String TAG = "ImageViewerAndDeleteActivity";

    /*打开当前Activity时所需要带过来的数据所对应的key*/
    public static final String IMGS_DATA_KEY = "images";
    /*打开当前Activity时所需要带过来的当前要显示的pager*/
    public static final String INDEX_KEY = "index";
    /*打开当前Activity时所需要带过来表明是本地图片还是网络图片*/
    public static final String IS_NETWORD_IMAGE_KEY = "netword";
    /*打开当前Activity时所需要带过来的值，告诉当前页面是否需要显示删除按钮*/
    public static final String SHOW_DELETE_BTN = "delete_btn";

    public static final String SHARE_ELEMENT_NAME = "view_animator_tag";

    private ArrayList<String> mList;
    private ViewPager mVpPreview;
    private ImageViewerAdapter mAdapter;
    //private TitleBar mTitleBar;
    private int mIndex;
    private boolean mShowDeleteBtn;
    private boolean mIsNetworkImg;


    //读写权限
    private String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private ImageButton mIb_back;
    private TextView mTv_title;
    private ImageButton mIb_delete;


    @Override
    public int getLayoutId() {return R.layout.activity_image_preview_and_delete;}

    /**
     * 初始化上个页面带过来的数据
     */
    @Override
    public void initData() {
        initView();
        //获取上个页面带过来的数据
        Log.e("Tag","11111111111mList="+mList.size());

        //当前显示面默认是0页
        mIndex = getIntent().getIntExtra(INDEX_KEY, 0);

        //是否显示删除按钮
        mShowDeleteBtn = getIntent().getBooleanExtra(SHOW_DELETE_BTN, true);

        //是否为网络图片
        mIsNetworkImg = getIntent().getBooleanExtra(IS_NETWORD_IMAGE_KEY, false);
        initListener();
    }


    @Override
    public void initListener() {
        mList = getIntent().getStringArrayListExtra(IMGS_DATA_KEY);
        mAdapter = new ImageViewerAdapter(this, mList, mIsNetworkImg);
        mAdapter.setOnImageLongClickListener(new MyOnImageLongClickListener());
        mVpPreview.setAdapter(mAdapter);
        mVpPreview.addOnPageChangeListener(new MyOnPageChangeListener());
        mVpPreview.setCurrentItem(mIndex);
        //转场效果
        //        ViewCompat.setTransitionName(findViewById(R.id.title_bar), SHARE_ELEMENT_NAME);

        //标题带显示查看当前图片的位置索引
        mTv_title.setText(getString(R.string.text_preview_index, mIndex + 1, mList.size()));
    }

    /**
     * 初始化数据
     */

    public void initView() {
        mIb_back = (ImageButton) findViewById(R.id.ib_back_2);
        mIb_back.setOnClickListener(this);
        mTv_title = (TextView) findViewById(R.id.tv_title);
        mIb_delete = (ImageButton) findViewById(R.id.ib_delete);
        mIb_delete.setOnClickListener(this);
       /* mTitleBar = setTitleBar(R.id.title_bar,
                getString(R.string.text_preview),
                true,
                true,
                new MyOnTitleBarClickListener());*/
        mVpPreview = (ViewPager) findViewById(R.id.vp_preview);
        //mTitleBar.setTvRightText("123");
        //mTitleBar.setIvRightBtnDrawable(R.mipmap.icon_del_black_h);
        //mTitleBar.visibleIvRightBtn(mShowDeleteBtn);
        FrameLayout fl = (FrameLayout) findViewById(R.id.fl_parent);


    }

    @Override
    protected ImageViewerPresenterImpl createPresenter() {
        return new ImageViewerPresenterImpl();
    }

    /**
     * 由presenter调和此方法实现更新UI
     *
     * @param isSuccess 删除是否成功，true表示成功，false表示失败
     * @param error     当isSuccess为false时有值可取
     * @
     */
    @Override
    public void deleteResult(boolean isSuccess, String error) {
        if (isSuccess) {
            mAdapter.notifyDataSetChanged();
            Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "error", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 返回,把改变后的数据（即有部分图片可能被用户删除掉的数据），设置到
     * intent里然后返回上个页页
     */
    @Override
    public void onBackPressed() {

        //返回操作后的数据
        Intent intent = new Intent();
        intent.putStringArrayListExtra(IMGS_DATA_KEY, mList);
        setResult(0, intent);
        super.onBackPressed();
    }







    /**
     * 显示保存图片对话框
     *
     * @param position
     */
    private void showSaveImageDialog(final int position) {
        /*SaveImageDialog saveImageDialog = new SaveImageDialog(this, new SaveImageDialog.OnConfirmClickListener() {
            @Override
            public void onConfirmClick() {
                showProgressDialog("保存中...");
                saveImage(position);
            }
        });
        saveImageDialog.show();*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_back_2:
                //返回操作后的数据
                onBackPressed();
                /*Intent intent = new Intent();
                intent.putStringArrayListExtra(IMGS_DATA_KEY, mList);
                setResult(0, intent);
                finish();*/
                break;
            case R.id.ib_delete:
                //删除图片
                getMPresenter().deleteImg(ImageViewerAndDeleteActivity.this, mList, mIndex);
                break;
        }
    }







    /**
     * 标题栏点击事件
     */
   /* private class MyOnTitleBarClickListener implements TitleBar.OnTitleBarClickListener {
        @Override
        public void onLeftButtonClick(View view) {
            //返回操作后的数据
            onBackPressed();
        }

        @Override
        public void onRightButtonClick(View view) {
            //删除图片
            mPresenter.deleteImg(ImageViewerAndDeleteActivity.this, mList, mIndex);
        }
    }*/

    /**
     * 页面滑动监听
     */
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            //标题带显示查看当前图片的位置索引
            mTv_title.setText(getString(R.string.text_preview_index, position + 1, mList.size()));
            mIndex = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    /**
     * 图片长按事件
     */
    private class MyOnImageLongClickListener implements ImageViewerAdapter.OnImageLongClickListener {
        @Override
        public void onImageLongClick(int position, View view) {
            showSaveImageDialog(position);
        }
    }


}
