package com.example.mvpframe

import com.example.baseframe.permission.PermissionActivity

/**
 * Description:
 * Created by DarkHorse on 2018/5/24.
 */
abstract class BaseMvpPermissionActivity<M, V : BaseMvpView, P : BaseMvpPresenter<M, V>> : PermissionActivity() {

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

    protected abstract fun createPresenter(): P

    override fun onDestroy() {
        super.onDestroy()
        if (hasAttach) {
            mPresenter.detachView()
        } else {
            hasAttach = false
        }
    }
}
