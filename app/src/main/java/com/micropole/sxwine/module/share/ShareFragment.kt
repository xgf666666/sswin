package com.micropole.sxwine.module.share

import android.content.Intent
import android.support.design.widget.BottomSheetDialog
import android.view.View
import com.darkhorse.preferencesmanager.PreferencesHelper
import com.example.mvpframe.BaseMvpFragment
import com.micropole.sxwine.R
import com.micropole.sxwine.base.loadImg
import com.micropole.sxwine.bean.ShareBean
import com.micropole.sxwine.common.Constants
import com.micropole.sxwine.module.personal.Bean.UserInfoEntity
import com.micropole.sxwine.module.share.mvp.contract.ShareContract
import com.micropole.sxwine.module.share.mvp.presenter.SharePresenter
import com.micropole.sxwine.utils.network.API
import kotlinx.android.synthetic.main.fragment_share.*
import kotlinx.android.synthetic.main.fragment_share.view.*
import kotlinx.android.synthetic.main.item_toolbar.view.*
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color.BLACK
import android.graphics.Matrix
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import java.util.*
import android.graphics.drawable.BitmapDrawable
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout

class ShareFragment : BaseMvpFragment<ShareContract.Model, ShareContract.View, SharePresenter>(), View.OnClickListener, ShareContract.View {

    private lateinit var mShareLink: String;
    private lateinit var mTitle: String
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun createPresenter(): SharePresenter = SharePresenter()

    private val mBottomSheetDialog: BottomSheetDialog by lazy {
        val dialog = BottomSheetDialog(mContext)
        val view = layoutInflater.inflate(R.layout.item_dialog_share, null, false)
        view.findViewById<View>(R.id.iv_close).setOnClickListener(this)
        view.findViewById<View>(R.id.share_1).setOnClickListener(this)
        view.findViewById<View>(R.id.share_2).setOnClickListener(this)
        view.findViewById<View>(R.id.share_3).setOnClickListener(this)
        view.findViewById<View>(R.id.share_4).setOnClickListener(this)
        view.findViewById<View>(R.id.share_5).setOnClickListener(this)
        view.findViewById<View>(R.id.share_6).setOnClickListener(this)
        dialog.setContentView(view)
        dialog
    }

    override fun getLayoutId(): Int = R.layout.fragment_share

    override fun initView(rootView: View) {
        mSwipeRefreshLayout = rootView.mSwipeRefreshLayout
        mSwipeRefreshLayout.setOnRefreshListener {
            mPresenter.getShareData()
        }
        rootView.toolbar_title.text = getString(R.string.myShare)
    }

    override fun initListener(rootView: View) {
        rootView.btn_share.setOnClickListener(this)
//        rootView.btn_buy_num.setOnClickListener(this)
    }

    override fun initData() {
        mSwipeRefreshLayout.isRefreshing = true
        mPresenter.getShareData()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_share -> {
                val textIntent = Intent(Intent.ACTION_SEND)
                textIntent.type = "text/plain"
                textIntent.putExtra(Intent.EXTRA_SUBJECT, mTitle)
                textIntent.putExtra(Intent.EXTRA_TEXT, mShareLink)
                startActivity(Intent.createChooser(textIntent, getString(R.string.home_tab_2)))
            }
            R.id.iv_close -> {
            }
//            R.id.btn_buy_num -> {
//                val intent = Intent(mContext, MainActivity::class.java)
//                val bundle = Bundle()
//                bundle.putBoolean("isLogin", true)
//                intent.putExtra("data", bundle)
//                startActivity(intent)
//            }
        }
    }

    override fun onSuccess(shareBean: ShareBean) {
        val bean = PreferencesHelper.get(Constants.USER_INFO, UserInfoEntity()) as UserInfoEntity
        iv_icon.loadImg(mContext, API.HOST + bean.avatar)
        tv_nickname.text = bean.nickname
        tv_vip.text = bean.vip_name

        mSwipeRefreshLayout.isRefreshing = false
        mShareLink = shareBean.share_link!!
        tv_code.text = shareBean.invite_code
        tv_num.text = shareBean.p1_quota
        tv_num2.text = shareBean.p2_quota
        mTitle = shareBean.title!!
        iv_code.setImageBitmap(createQRCode(mShareLink, 300))
    }

    override fun onFailure(err: String) {

    }

    //生成二维码图片（不带图片）
    @Throws(WriterException::class)
    fun createQRCode(url: String, widthAndHeight: Int): Bitmap? {
        val hints = Hashtable<EncodeHintType, String>()
        hints[EncodeHintType.CHARACTER_SET] = "utf-8"
        val matrix = MultiFormatWriter().encode(url,
                BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight)

        val width = matrix.width
        val height = matrix.height
        val pixels = IntArray(width * height)
        //画黑点
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = BLACK //0xff000000
                }
            }
        }
        val bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        val bitmapDrawable = ContextCompat.getDrawable(mContext, R.mipmap.logo_bottom) as BitmapDrawable
        return createWaterMaskCenter(bitmap, bitmapDrawable.bitmap)
    }

    private fun createWaterMaskCenter(src: Bitmap, watermark: Bitmap?): Bitmap? {
        if (watermark != null) {

            val newMark = zoomImg(watermark, src.width / 6, src.height / 6)
            return createWaterMaskBitmap(src, newMark,
                    ((src.width - newMark.width) / 2).toFloat(),
                    ((src.height - newMark.height) / 2).toFloat())
        }
        return null
    }

    fun zoomImg(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        // 获得图片的宽高
        val width = bm.width
        val height = bm.height
        // 计算缩放比例
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // 取得想要缩放的matrix参数
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true)
    }

    private fun createWaterMaskBitmap(src: Bitmap?, watermark: Bitmap,
                                      paddingLeft: Float, paddingTop: Float): Bitmap? {
        if (src == null) {
            return null
        }
        val width = src.width
        val height = src.height
        //创建一个bitmap
        val newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)// 创建一个新的和SRC长度宽度一样的位图
        //将该图片作为画布
        val canvas = Canvas(newb)
        //在画布 0，0坐标上开始绘制原始图片
        canvas.drawBitmap(src, 0f, 0f, null)
        //在画布上绘制水印图片
        canvas.drawBitmap(watermark, paddingLeft, paddingTop, null)
        // 保存
        canvas.save(Canvas.ALL_SAVE_FLAG)
        // 存储
        canvas.restore()
        return newb
    }
}
