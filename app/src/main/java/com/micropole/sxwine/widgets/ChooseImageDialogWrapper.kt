package com.dgshanger.sanhxiaofeisc.dialog

import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.micropole.sxwine.R
import com.micropole.sxwine.utils.XxImageChooseHelper

/**
 * ChooseImageDialogWrapper
 * 沉迷学习不能自拔
 * Describe：
 * Created by 雷小星🍀 on 2018/3/16 15:46.
 */

class ChooseImageDialogWrapper(imageChooseHelper: XxImageChooseHelper) {

    private var chooseImageDialog: AlertDialog? = null
    /**
     * 初始化默认图片选择对话框
     */
    private fun initChooseImageDialog(imageChooseHelper: XxImageChooseHelper) {
        if (chooseImageDialog == null) {
            val contentView = LayoutInflater.from(imageChooseHelper.context).inflate(
                    R.layout.popup_picture, null)
            chooseImageDialog = AlertDialog.Builder(imageChooseHelper.context, R.style.Animation_Bottom_Rising)
                    .setView(contentView)
                    .create()

            val onClickListener = View.OnClickListener { v ->
                when (v.id) {
                    R.id.tv_camera -> imageChooseHelper.startCamearPic()
                    R.id.tv_picture -> imageChooseHelper.startImageChoose()
                    R.id.tv_cancel -> dismissChooseImage()
                }
                dismissChooseImage()
            }

            contentView.findViewById<View>(R.id.tv_camera).setOnClickListener(onClickListener)
            contentView.findViewById<View>(R.id.tv_picture).setOnClickListener(onClickListener)
            contentView.findViewById<View>(R.id.tv_cancel).setOnClickListener(onClickListener)

            val window = chooseImageDialog!!.window
            if (window != null) {
                window.setGravity(Gravity.BOTTOM)//设置窗口位于底部
                window.getDecorView().setPadding(0, 0, 0, 0)
                val lp = window.getAttributes()
                window.setBackgroundDrawable(BitmapDrawable())
                lp.width = WindowManager.LayoutParams.MATCH_PARENT
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT
                window.setAttributes(lp)
            }
        }
    }

    /**
     * 显示选择图片的对话框
     */
    fun showChooseImage() {
        chooseImageDialog?.show()
    }

    /**
     * 隐藏对话框
     */
    fun dismissChooseImage() {
        if (chooseImageDialog != null && chooseImageDialog!!.isShowing()) {
            chooseImageDialog?.dismiss()
        }

    }

    init {
        initChooseImageDialog(imageChooseHelper)
    }
}
