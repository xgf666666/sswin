package com.example.baseframe

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.example.baseframe.utils.AppManager

/**
 * Description:
 * Created by DarkHorse on 2018/5/8.
 */
abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var mContext: Activity
    private var mBundle: Bundle? = null
    var mLoadingDialog: Dialog? = null
    var mNetworkErrorDialog: Dialog? = null

    var mHandler = Handler(Looper.myLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.addActivity(this)
        mContext = this
        preSetContentView()
        setContentView(getLayoutId())
        initView()
        initListener()
        initData()
    }

    protected open fun preSetContentView() {
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun initListener()

    abstract fun initData()

    protected fun startActivity(clz: Class<out Activity>, bundle: Bundle? = null, isFinished: Boolean = false) {
        AppManager.startActivity(clz, bundle, isFinished)

    }

    protected fun startActivityForResult(clz: Class<out Activity>, requestCode: Int, bundle: Bundle? = null) {
        AppManager.startActivityForResult(clz, requestCode, bundle)
    }

    protected fun getBundle(): Bundle? {
        if (mBundle == null) {
            mBundle = intent.getBundleExtra("data")
        }
        return mBundle
    }

    protected fun setImmersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mLoadingDialog != null) {
            mLoadingDialog?.cancel()
            mLoadingDialog = null
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        AppManager.finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}