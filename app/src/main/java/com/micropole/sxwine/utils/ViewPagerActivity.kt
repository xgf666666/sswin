/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.micropole.sxwine.utils

import uk.co.senab.photoview.PhotoView

import android.app.Activity
import android.media.Image
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter

import com.micropole.sxwine.R
import com.micropole.sxwine.base.i
import com.micropole.sxwine.base.loadImg
import com.micropole.sxwine.utils.network.API

class ViewPagerActivity : Activity(), OnClickListener, OnPageChangeListener {

    private var mViewPager: ViewPager? = null
    internal var imgUrls: Array<String>? = null
    internal var imgNames: Array<String>? = null
    internal var index: Int = 0


    private val isViewPagerActive: Boolean
        get() = mViewPager != null && mViewPager is HackyViewPager

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        initView()
        if (savedInstanceState != null) {
            val isLocked = savedInstanceState.getBoolean(ISLOCKED_ARG,
                    false)
            (mViewPager as HackyViewPager).isLocked = isLocked
        }
        setUpView()
    }

    private fun initView() {
        mViewPager = findViewById<View>(R.id.view_pager) as HackyViewPager
    }

    private fun setUpView() {
        index = intent.getIntExtra("imgUrl_index", 0)
        imgUrls = intent.extras!!.getStringArray("imgUrls")

        mViewPager!!.adapter = SamplePagerAdapter()
        mViewPager!!.addOnPageChangeListener(this)
        mViewPager!!.setOnClickListener(this)

        for (i in 0 until imgUrls!!.size) {
            imgUrls!![i] = API.HOST + imgUrls!![i]
        }
        // 设置初始位置
        mViewPager!!.currentItem = index
        findViewById<View>(R.id.ib_back).setOnClickListener(this)

    }

    private inner class SamplePagerAdapter : PagerAdapter() {
        override fun getCount(): Int {
            return if (imgUrls == null) 0 else imgUrls!!.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): View {
            val photoView = PhotoView(container.context)
            photoView.loadImg(container.context, imgUrls!![position],transform = FitCenter())
            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT)

            return photoView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (isViewPagerActive) {
            outState.putBoolean(ISLOCKED_ARG,
                    (mViewPager as HackyViewPager).isLocked)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onClick(v: View) {
        finish()
    }

    override fun onPageScrollStateChanged(arg0: Int) {

    }

    override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {

    }

    override fun onPageSelected(poisition: Int) {
    }

    companion object {

        private val ISLOCKED_ARG = "isLocked"
    }

}
