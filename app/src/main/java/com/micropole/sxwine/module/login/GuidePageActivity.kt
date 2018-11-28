package com.micropole.sxwine.module.login

import android.content.Context
import android.content.Intent
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.micropole.sxwine.R
import kotlinx.android.synthetic.main.activity_guide_page.*

/**
 * Created by JohnnyH on 2018/8/15.
 * 引导页
 */
class GuidePageActivity : com.example.baseframe.BaseActivity() {

    private var mData:List<Int> = ArrayList<Int>().apply {
        add(R.mipmap.guide_one)
        add(R.mipmap.guide_two)
        add(R.mipmap.guide_three)
    }

    override fun getLayoutId(): Int = R.layout.activity_guide_page
    override fun initView() {

        view_pager.adapter= GuideViewPageAdapter(mData,mContext)
    }

    override fun initListener() {
    }

    override fun initData() {
    }

    class GuideViewPageAdapter(var mData : List<Int>,var mContext : Context) : PagerAdapter(){



        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view==obj
        }

        override fun getCount(): Int {
            return mData.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val imageView = ImageView(mContext)
            imageView.setImageResource(mData[position])
            imageView.scaleType=ImageView.ScaleType.CENTER_CROP
            imageView.setOnClickListener{
                if (position==2){
                    val intent= Intent(mContext,LoginActivity::class.java)
                    mContext.startActivity(intent)
                    (mContext as GuidePageActivity).finish()
                }
            }
            container.addView(imageView)

            return imageView
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            container.removeView(obj as View)
        }

    }

}