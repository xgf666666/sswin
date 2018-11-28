package com.micropole.sxwine.module.personal

import android.content.Intent
import android.util.Log
import com.darsh.multipleimageselect.activities.AlbumSelectActivity
import com.darsh.multipleimageselect.helpers.Constants
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.loadImg
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.personal.adapter.AdditionImageWrapper
import com.micropole.sxwine.module.personal.mvp.contract.OrderCommentContract
import com.micropole.sxwine.module.personal.mvp.presenter.OrderCommentPresenter
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.widgets.PictureSelectDialog
import kotlinx.android.synthetic.main.activity_order_comment.*
import java.util.*

/**
 * Created by JohnnyH on 2018/9/7.
 * 订单商品评论
 */
class OrderCommentActivity : BaseMvpActivity<OrderCommentContract.Model,OrderCommentContract.View,OrderCommentPresenter>(),OrderCommentContract.View {

    private lateinit var mOrder_id : String
    private lateinit var mGoods_id : String
    private lateinit var mGoods_img : String
    private var mAdditionImageWrapper: AdditionImageWrapper? = null
    private var mPictureSelectDialog: PictureSelectDialog? = null
    /*预览图片请求码，因为在预览时可以对图片进行删除操作。所以要接收返回回来的新数据*/
    private val REQUEST_VIEWER_IMG = 757
    private var images = ArrayList<String>()
    private var resultImages: MutableList<String> = ArrayList()

    override fun createPresenter(): OrderCommentPresenter = OrderCommentPresenter()

    override fun getLayoutId(): Int = R.layout.activity_order_comment

    override fun initView() {
        iv_back.setOnClickListener { finish() }
        mOrder_id=intent.getStringExtra("order_id")
        mGoods_id=intent.getStringExtra("goods_id")
        mGoods_img=intent.getStringExtra("goods_img")
        iv_goods.loadImg(mContext, API.HOST+mGoods_img)
    }

    override fun initData() {

        mAdditionImageWrapper = AdditionImageWrapper(this, recyclerView)
        mAdditionImageWrapper!!.wrap(MyOnAdditionLayoutItemClick())

    }

    private var starCount : String = "5"
    override fun initListener() {
        //星星
        rb.setOnRatingBarChangeListener { ratingBar, fl, b ->
            starCount=fl.toInt().toString()
        }
        btn_submit.setOnClickListener {
            showLoadingDialog()
            mPresenter.submitComment(mOrder_id,mGoods_id,starCount,starCount,resultImages)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST_CODE && null != data) {
            //图片选择返回
            images = data.getStringArrayListExtra(Constants.INTENT_EXTRA_IMAGES)
            Log.e("Tag", "新选择的图片数量:" + images.size)
            //Log.d(TAG, "新选择的图片路径:" + images.get(0));
            mPresenter.imageSelect(images)

        } else if (requestCode == REQUEST_VIEWER_IMG && null != data) {
            val imageList = data.getStringArrayListExtra(ImageViewerAndDeleteActivity.IMGS_DATA_KEY)
            Log.e("Tag","删除的图片="+imageList)
            mPresenter.imageDelete(imageList)
        }
    }

    /**
     * RecyclerView item点击
     */
    private inner class MyOnAdditionLayoutItemClick : AdditionImageWrapper.OnAdditionLayoutItemClick {
        override fun onAdditionItemClick() {
            //添加的item被点击
            showMultiImageDialog()
        }

        override fun onImageItemClick(position: Int) {
            //普通图片被点击
            launcherToImageViewer(position)
        }

        override fun onImageDeleteClick(position: Int) {
            resultImages.removeAt(position)
            mAdditionImageWrapper!!.setData(resultImages)
        }
    }

    /**
     * 显示图片选择对话框
     */
    private fun showMultiImageDialog() {
        if (null == mPictureSelectDialog) {
            mPictureSelectDialog = PictureSelectDialog(this)
            mPictureSelectDialog!!.setOnPictureSelectDialogClickListener(MyOnPictureSelectDialogClickListener())
        }
        mPictureSelectDialog!!.show()
    }

    /**
     * 跳转到图片预览界面
     *
     * @param position 要显示的页面的索引
     */
    private fun launcherToImageViewer(position: Int) {
        val intent = Intent(this, ImageViewerAndDeleteActivity::class.java)
        val imageList = mAdditionImageWrapper!!.getImageList()
                Log.e("Tag","1111111111imageList="+imageList.size)
        intent.putStringArrayListExtra(ImageViewerAndDeleteActivity.IMGS_DATA_KEY, imageList as ArrayList<String>)
        intent.putExtra(ImageViewerAndDeleteActivity.INDEX_KEY, position)
        intent.putExtra(ImageViewerAndDeleteActivity.SHOW_DELETE_BTN, true)
        startActivityForResult(intent, REQUEST_VIEWER_IMG)
    }

    /**
     * 图片选择对话框回调接口
     */
    private inner class MyOnPictureSelectDialogClickListener : PictureSelectDialog.OnPictureSelectDialogClickListener {
        override fun onCamera() {
            launcherToMultiImageSelect(true)
        }

        override fun onPhoto() {
            launcherToMultiImageSelect(false)
        }
    }

    /**
     * 跳到图片多选页面
     *
     * @param goToCamera 是否直接跳到拍照
     */
    private fun launcherToMultiImageSelect(goToCamera: Boolean) {
        val intent = Intent(mContext, AlbumSelectActivity::class.java)
        intent.putExtra("limit", mAdditionImageWrapper!!.getRemainCount())
        intent.putExtra(AlbumSelectActivity.KEY_GO_TO_CAMERA_ACTION, goToCamera)
        startActivityForResult(intent, 2000)
    }

    override fun setImageResult(pictures: MutableList<String>?) {
        resultImages = pictures!!
        mAdditionImageWrapper!!.setData(resultImages)
    }

    override fun onCommentSuccess(msg: String?) {
        hideLoadingDialog()
        toast(msg!!)
        setResult(com.micropole.sxwine.common.Constants.COMMENT_RESULT_CODE)
        finish()
    }

    override fun onCommentFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }
}