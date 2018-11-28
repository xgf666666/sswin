package com.micropole.sxwine.module.personal.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.module.personal.Bean.AddressManagerEntity
import com.micropole.sxwine.widgets.SwipeLayout
import java.util.*

/**
 * Created by JohnnyH on 2018/6/8.
 */
class AddressManagerAdapter(resLayoutId : Int,data : List<AddressManagerEntity>?) : BaseQuickAdapter<AddressManagerEntity,BaseViewHolder>(resLayoutId,data) {

    //存放所有已经打开的菜单
    private val openList = ArrayList<SwipeLayout>()

    override fun convert(helper: BaseViewHolder?, item: AddressManagerEntity?) {
        helper!!.setText(R.id.tv_name,item!!.receiver)
        /*val phone = item!!.mobile.replaceRange(3, 7, "****")
        helper!!.setText(R.id.tv_phone,phone)*/
        helper!!.setText(R.id.tv_phone,item!!.mobile)
        val tv_def = helper.getView<TextView>(R.id.tv_def)
        if ("0"==item.is_default){//不是默认地址
            tv_def.visibility= View.GONE
        }else{
            tv_def.visibility= View.VISIBLE
        }
        helper.setText(R.id.tv_address,item.address_detail)
        helper.addOnClickListener(R.id.ll_delete)
        helper.addOnClickListener(R.id.iv_pen)
        val swipeLayout = helper.getView<SwipeLayout>(R.id.swipe)
        /**
         * 1.必须设置监听才能侧滑
         * 2.openList的操作代表只能打开一个侧滑(不做操作代表可以打开所有item的侧滑)
         */
        swipeLayout.setSwipeChangeListener(object : SwipeLayout.OnSwipeChangeListener {
            override fun onDraging(mSwipeLayout: SwipeLayout) {}

            override fun onOpen(mSwipeLayout: SwipeLayout) {
                openList.add(mSwipeLayout)
            }

            override fun onClose(mSwipeLayout: SwipeLayout) {
                openList.remove(mSwipeLayout)
            }

            override fun onStartOpen(mSwipeLayout: SwipeLayout) {
                for (layout in openList) {
                    layout.close()
                }
                openList.clear()
            }

            override fun onStartClose(mSwipeLayout: SwipeLayout) {}
        })

    }
}