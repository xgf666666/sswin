package com.micropole.sxwine.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.View
import com.example.baseframe.BaseFragment

/**
 * Description:
 * Created by DarkHorse on 2018/6/1.
 */
class TabAdapter(fragments: ArrayList<BaseFragment>, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var mFragments: ArrayList<BaseFragment> = fragments

    override fun getCount(): Int = mFragments.size

    override fun getItem(position: Int): Fragment = mFragments[position]

}
