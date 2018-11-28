package com.example.mvpframe

import android.view.View
import com.example.baseframe.BaseFragment

/**
 * Description:
 * Created by DarkHorse on 2018/5/28.
 */
abstract class BaseMvpFragment<M, V : BaseMvpView, P : BaseMvpPresenter<M, V>> : BaseFragment() {

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