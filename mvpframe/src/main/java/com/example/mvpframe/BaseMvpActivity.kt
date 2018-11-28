package com.example.mvpframe

import com.example.baseframe.BaseActivity


/**
 * Description:
 * Created by DarkHorse on 2018/5/8.
 */
abstract class BaseMvpActivity<M, V : BaseMvpView, P : BaseMvpPresenter<M, V>> : BaseActivity() {

    protected val mPresenter: P by lazy {
        initPresenter()
    }

    private var hasAttach: Boolean = false

    @Suppress("UNCHECKED_CAST")
    private fun initPresenter(): P {
        val presenter = createPresenter()
        presenter.attachView(this as V)
        hasAttach = true
        return presenter
    }

    override fun onDestroy() {
        super.onDestroy()
        if (hasAttach) {
            mPresenter.detachView()
        } else {
            hasAttach = false
        }
    }

    protected abstract fun createPresenter(): P
}