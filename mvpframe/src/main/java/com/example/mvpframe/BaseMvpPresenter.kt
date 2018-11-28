package com.example.mvpframe

import java.lang.ref.WeakReference

/**
 * Description:
 * Created by DarkHorse on 2018/5/8.
 */
abstract class BaseMvpPresenter<M, V : BaseMvpView> {
    private lateinit var mWeakReference: WeakReference<V>

    protected var mView: V? = null
        get() {
            return mWeakReference.get()
        }

    protected val mModel: M by lazy {
        createModel()
    }

    fun attachView(view: V) {
        mWeakReference = WeakReference(view)
    }

    fun detachView() {
        mWeakReference.clear()
    }

    abstract fun createModel(): M
}