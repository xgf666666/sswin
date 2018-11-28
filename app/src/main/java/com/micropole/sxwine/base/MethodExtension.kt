package com.micropole.sxwine.base

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.blankj.ALog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.baseframe.BaseActivity
import com.example.baseframe.utils.AppManager
import com.micropole.sxwine.R
import com.micropole.sxwine.widgets.LoadingDialog
import kotlinx.android.synthetic.main.item_toolbar.*


/**
 * Description:
 * Created by DarkHorse on 2018/5/31.
 */
fun BaseActivity.initToolBar(text: String) {
    setSupportActionBar(toolbar)
    supportActionBar?.run {
        setDisplayHomeAsUpEnabled(true)
        setHomeButtonEnabled(true)
        setHomeAsUpIndicator(R.mipmap.right_arrows)
        setDisplayShowTitleEnabled(false)
        title = text
    }
    toolbar_title.text = text
}

fun ImageView.loadImg(context: Context, url: String?, place: Int = R.mipmap.zhaoshao, error: Int = 0, transform: BitmapTransformation? = null) {
    val options: RequestOptions
    if (transform != null) {
        options = RequestOptions.bitmapTransform(transform)
    } else {
        options = RequestOptions.bitmapTransform(CenterCrop())
    }

    options.placeholder(place)

    if (error != 0) {
        options.error(error)
    }

    Glide.with(context)
            .load(url)
            .apply(options)
            .into(this)
}
fun ImageView.loadADImg(context: Context, url: String?, place: Int = R.mipmap.zhaoshao, error: Int = 0, transform: BitmapTransformation? = null) {
    val options: RequestOptions
    if (transform != null) {
        options = RequestOptions.bitmapTransform(transform)
    } else {
        options = RequestOptions.bitmapTransform(CenterCrop())
    }

    if (error != 0) {
        options.error(error)
    }

    Glide.with(context)
            .load(url)
            .apply(options)
            .into(this)
}

fun toast(msg: String) {
    Toast.makeText(AppManager.currentActivity(), msg, Toast.LENGTH_SHORT).show()
}

fun a(msg: String) {
    ALog.a(msg)
}

fun d(msg: String) {
    ALog.d(msg)
}

fun e(msg: String) {
    ALog.e(msg)
}

fun i(msg: String) {
    ALog.i(msg)
}


fun v(msg: String) {
    ALog.v(msg)
}

fun w(msg: String) {
    ALog.w(msg)
}

fun json(msg: String) {
    ALog.json(msg)
}

fun xml(msg: String) {
    ALog.xml(msg)
}

fun BaseActivity.showLoadingDialog() {
    if (mLoadingDialog == null) {
        mLoadingDialog = LoadingDialog(this)
    }
    if (!mLoadingDialog!!.isShowing) {
        mLoadingDialog!!.show()
    }
}

fun BaseActivity.hideLoadingDialog() {
    if (mLoadingDialog != null) {
        if (mLoadingDialog!!.isShowing) {
            mLoadingDialog!!.dismiss()
        }
    }
}

