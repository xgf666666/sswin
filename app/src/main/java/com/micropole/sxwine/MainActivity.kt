package com.micropole.sxwine

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.view.WindowManager
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.example.baseframe.BaseActivity
import com.example.baseframe.utils.AppManager
import com.micropole.tanglong.WebViewActivity
import com.micropole.sxwine.adapter.TabAdapter
import com.micropole.sxwine.module.car.CarFragment
import com.micropole.sxwine.module.home.GoodDetailActivity
import com.micropole.sxwine.module.home.HomeFragment
import com.micropole.sxwine.module.login.bean.ADEntity
import com.micropole.sxwine.module.personal.MineFragment
import com.micropole.sxwine.module.share.ShareFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun preSetContentView() {
        super.preSetContentView()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        initViewPager()
        initBottomNavigationBar()
        initAD()
    }

    /**
     * 广告
     */
    private fun initAD() {
        val flag = intent.getStringExtra("flag")
        if (!TextUtils.isEmpty(flag)){
            val aDEntity = intent.getSerializableExtra("ADEntity") as ADEntity
            val type = aDEntity.type_id
            if (type == "2") {
                val bundle = Bundle()
                bundle.putInt("id", aDEntity.goods_id.toInt())
                startActivity(GoodDetailActivity::class.java, bundle)
            } else if (type == "1") {
                val intent = Intent(mContext, WebViewActivity::class.java)
                intent.putExtra(WebViewActivity.EXTRA_WEB_TYPE, WebViewActivity.TYPE_URL)
                intent.putExtra(WebViewActivity.EXTRA_WEB_URL, aDEntity.link)
                startActivity(intent)
            }else if(type == "3"){
                val intent = Intent(mContext, WebViewActivity::class.java)
                intent.putExtra(WebViewActivity.EXTRA_WEB_TYPE, WebViewActivity.TYPE_CONTENT)
                intent.putExtra(WebViewActivity.EXTRA_WEB_CONTENT, aDEntity.introduction)
                startActivity(intent)
            }
        }

    }

    private fun initViewPager() {
        mViewPager.adapter = TabAdapter(arrayListOf(HomeFragment(), ShareFragment(), CarFragment(), MineFragment()), supportFragmentManager)
        mViewPager.offscreenPageLimit = 4
    }

    private fun initBottomNavigationBar() {
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED)
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
        mBottomNavigationBar.activeColor = R.color.white
        mBottomNavigationBar.inActiveColor = R.color.tab_off
        mBottomNavigationBar.setBarBackgroundColor(R.color.tab_on)

        mBottomNavigationBar.addItem(BottomNavigationItem(R.mipmap.home_label_btn_home, R.string.home_tab_1).setInactiveIcon(ContextCompat.getDrawable(mContext, R.mipmap.home_home)))
        mBottomNavigationBar.addItem(BottomNavigationItem(R.mipmap.home_label_btn_shareit, R.string.home_tab_2).setInactiveIcon(ContextCompat.getDrawable(mContext, R.mipmap.home_share)))
        mBottomNavigationBar.addItem(BottomNavigationItem(R.mipmap.home_label_btn_shoppingcart, R.string.home_tab_3).setInactiveIcon(ContextCompat.getDrawable(mContext, R.mipmap.home_shopping_cart)))
        mBottomNavigationBar.addItem(BottomNavigationItem(R.mipmap.home_label_btn_mine, R.string.home_tab_4).setInactiveIcon(ContextCompat.getDrawable(mContext, R.mipmap.home_my)))

        mBottomNavigationBar.initialise()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val isLogin = intent?.getBundleExtra("data")?.getBoolean("isLogin")
        if (isLogin != null) {
            if (!isLogin) {
                mViewPager.setCurrentItem(2, false)
            } else {
                mViewPager.setCurrentItem(0, false)
            }
        }
    }

    override fun initListener() {
        mBottomNavigationBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {

            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabSelected(position: Int) {
                when (position) {
                    0 -> mViewPager.setCurrentItem(0, false)
                    1 -> mViewPager.setCurrentItem(1, false)
                    2 -> mViewPager.setCurrentItem(2, false)
                    3 -> mViewPager.setCurrentItem(3, false)
                }
            }
        })
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                mBottomNavigationBar.selectTab(position)
            }
        })
    }

    override fun initData() {
    }

    override fun onBackPressed() {
        AppManager.exit()
    }
}
