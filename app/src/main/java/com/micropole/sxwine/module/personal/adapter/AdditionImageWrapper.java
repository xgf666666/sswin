package com.micropole.sxwine.module.personal.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.darsh.multipleimageselect.utils.ImageLoader;
import com.micropole.sxwine.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: xudiwei
 * <p>
 * on: 2017/4/13.
 * <p>
 * 描述：用于处理带有可添加item的列表布局，可用于动态创建时的图片内容的显示操作布局
 * <p>
 * －－－－－－
 * ｜       ｜
 * ｜   +   ｜
 * ｜       ｜
 * －－－－－－
 * <p>
 * 用例 :
 * <p>
 * AdditionImageWrapper additionImageWrapper = new AdditionImageWrapper(Context, RecyclerView);
 * additionImageWrapper.wrap(new AdditionImageWrapper.OnAdditionLayoutItemClick() {
 *
 * @Override public void onAdditionItemClick() {
 * //添加item被点击
 * }
 * @Override public void onImageItemClick(int position) {
 * //普通图片被点击
 * }
 * });
 * <p>
 * //设置图片的数据
 * additionImageWrapper.setData(List<String>);
 * <p>
 * //设置最多显示的图片数量（默认为9张）
 * additionImageWrapper.setLimit(int);
 * <p>
 * //设置RecyclerView的列（默认为4列）
 * addition.setRow(int);
 */

public class AdditionImageWrapper {
    private static final String TAG = "AdditionImageWrapper";
    /*默认图片数量为9张*/
    public static final int DEFAULT_COUNT = 9;
    /*默认图片的列数为4格*/
    public static final int DEFAULT_ROW = 4;
    /*图片数据有存在包含添加item的数据的，（当图片的数量小于mImageCountLimit时）*/
    private List<String> mList = new ArrayList<>();
    /*直实通过setData设置进来的图片*/
    private List<String> mRealList = new ArrayList<>();
    //剩下几张 mImageCountLimit - list
    private int mRemainCount = DEFAULT_COUNT;

    private Context mContext;
    private RecyclerView mRecyclerView;
    private int mImageCountLimit = DEFAULT_COUNT;
    private int mRecyclerViewRow = DEFAULT_ROW;
    private OnAdditionLayoutItemClick mItemClick;
    public CreateDynamicRVAdapter mAdapter;


    public AdditionImageWrapper(Context context, RecyclerView recyclerView) {
        this.mContext = context;
        this.mRecyclerView = recyclerView;
    }

    public void setLimit(int imageCountLimit) {
        this.mImageCountLimit = imageCountLimit;
        this.mRemainCount = imageCountLimit;
    }

    public void setRow(int recyclerViewRow) {
        this.mRecyclerViewRow = recyclerViewRow;
    }

    public int getRemainCount() {
        return mRemainCount;
    }

    public List<String> getImageList() {
        return mRealList;
    }


    public void setData(List<String> list) {
        if (list.size() > mImageCountLimit) {
            throw new IllegalArgumentException(" list size is 大于limit, limit是：" + mImageCountLimit + " 你给的是： " + list.size());
        }
        mRealList.clear();

        mRealList.addAll(list);
        mRemainCount = mImageCountLimit - list.size();

        this.mList.clear();
        this.mList.addAll(list);
//        log.e("list:" + list.size() + ",path:" + list.get(0));
        if (this.mList.size() < mImageCountLimit) {
            addDefaultItem();
        }
        if (null != mAdapter) {
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 包裹
     *
     * @param itemClick
     */
    public void wrap(OnAdditionLayoutItemClick itemClick) {
        this.mItemClick = itemClick;
        initRecyclerView();
    }

    /**
     * 添加珍上空的Item数据
     */
    private void addDefaultItem() {
        mList.add("");
    }

    private void initRecyclerView() {
        addDefaultItem();
        GridLayoutManager manager = new GridLayoutManager(mContext, mRecyclerViewRow);
        mAdapter = new CreateDynamicRVAdapter(mContext, mList);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }


    public interface OnAdditionLayoutItemClick {
        /**
         * 添加图片的item被点击
         */
        void onAdditionItemClick();

        /**
         * 图片item被点击
         *
         * @param position
         */
        void onImageItemClick(int position);

        /**
         * 图片删除按钮被点击
         *
         * @param position
         */
        void onImageDeleteClick(int position);
    }


    /**
     * RecyclerView适配器
     */
    private class CreateDynamicRVAdapter extends RecyclerView.Adapter<CreateDynamicRVAdapter.ViewHolder> {
        private Context mContext;
        private List<String> mList;

        CreateDynamicRVAdapter(Context context, List<String> list) {
            this.mContext = context;
            this.mList = list;
        }

        @Override
        public CreateDynamicRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_issue_dynamic, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CreateDynamicRVAdapter.ViewHolder holder, final int position) {
            String path = mList.get(position);

            if (position == mList.size() - 1 && TextUtils.isEmpty(path)) {
                holder.ivImage.setImageResource(R.mipmap.ic_camera);
                holder.ivDelete.setVisibility(View.INVISIBLE);
            } else {
                Log.e("Tag","加载图片");
                //holder.ivImage.setImageResource(R.drawable.print_load_);
                ImageLoader.loadToUrl(mContext, holder.ivImage, path);
                holder.ivDelete.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        /**
         * ViewHolder
         */
        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivImage;
            ImageView ivDelete;

            public ViewHolder(View itemView) {
                super(itemView);
                ivImage = (ImageView) itemView.findViewById(R.id.riv_img);
                ivDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
                if (null == mItemClick) {
                    return;
                }

                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = ViewHolder.this.getLayoutPosition();
                        String path = mList.get(position);
                        if (TextUtils.isEmpty(path) && position == mList.size() - 1) {
//                            mItemClick.onAdditionItemClick();
                            notifyDataSetChanged();
                        } else {
                            mItemClick.onImageDeleteClick(position);
//                            mList.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                });

                //设置点击事件
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = ViewHolder.this.getLayoutPosition();
                        String path = mList.get(position);
                        if (TextUtils.isEmpty(path) && position == mList.size() - 1) {
                            mItemClick.onAdditionItemClick();
                        } else {
                            mItemClick.onImageItemClick(position);
                        }
                    }
                });
            }
        }
    }


}
