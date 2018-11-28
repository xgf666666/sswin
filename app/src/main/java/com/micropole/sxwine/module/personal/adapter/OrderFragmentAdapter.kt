package com.micropole.sxwine.module.personal.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.micropole.sxwine.module.personal.MyOrderFragment

/**
 * Created by JohnnyH on 2018/6/15.
 */
class OrderFragmentAdapter(fm : FragmentManager,var fragmentList : List<Fragment>,var titles : Array<String>) : FragmentPagerAdapter(fm) {

    private lateinit var mMyOrderFragment : MyOrderFragment

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

    override fun getItem(position: Int): Fragment {
        when(position){
            0->{//全部
                mMyOrderFragment = fragmentList[position] as MyOrderFragment
                val bundle = Bundle()
                bundle.putInt("status",0)
                mMyOrderFragment.arguments=bundle
            }
            1->{//代付款
                mMyOrderFragment = fragmentList[position] as MyOrderFragment
                val bundle = Bundle()
                bundle.putInt("status",1)
                mMyOrderFragment.arguments=bundle
            }
            2->{//待发货
                mMyOrderFragment = fragmentList[position] as MyOrderFragment
                val bundle = Bundle()
                bundle.putInt("status",2)
                mMyOrderFragment.arguments=bundle
            }
            3->{//待收货
                mMyOrderFragment = fragmentList[position] as MyOrderFragment
                val bundle = Bundle()
                bundle.putInt("status",3)
                mMyOrderFragment.arguments=bundle
            }
            4->{//已完成
                mMyOrderFragment = fragmentList[position] as MyOrderFragment
                val bundle = Bundle()
                bundle.putInt("status",4)
                mMyOrderFragment.arguments=bundle
            }
        }
        return mMyOrderFragment
    }

    override fun getCount(): Int = fragmentList.size
}