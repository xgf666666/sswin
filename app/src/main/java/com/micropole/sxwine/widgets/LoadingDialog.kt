package com.micropole.sxwine.widgets

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.micropole.sxwine.R
import kotlinx.android.synthetic.main.item_loading_dialog.*

class LoadingDialog(context: Context) : Dialog(context, R.style.TransparentDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.item_loading_dialog)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        mAVLoadingIndicatorView.show()
    }

    override fun show() {
        super.show()
    }

    override fun dismiss() {
        super.dismiss()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        dismiss()
    }
}