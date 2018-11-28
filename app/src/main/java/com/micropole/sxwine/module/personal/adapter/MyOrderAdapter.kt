package com.micropole.sxwine.module.personal.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.module.personal.Bean.MyOrderItemEntity

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DefaultObserver
import java.util.concurrent.TimeUnit

/**
 * Created by JohnnyH on 2018/6/19.
 */
class MyOrderAdapter(data: List<MyOrderItemEntity>?) : BaseMultiItemQuickAdapter<MyOrderItemEntity, BaseViewHolder>(data) {

    init {
        addItemType(1, R.layout.item_wait_pay_order)//待付款
        addItemType(2, R.layout.item_wait_send_order)//待发货
        addItemType(3, R.layout.item_wait_receive_order)//待收货
        addItemType(4, R.layout.item_complete_order)//已完成
        addItemType(7, R.layout.item_refunding_order)//退款中
        addItemType(8, R.layout.item_refunded_order)//已退款
        addItemType(-1, R.layout.item_canceled_order)//已取消

    }

    override fun convert(helper: BaseViewHolder?, item: MyOrderItemEntity?) {
        when (helper!!.itemViewType) {
            1 -> {//代付款
                val tv_down_time = helper.getView<TextView>(R.id.tv_down_time)
                //开始倒计时
                val timeCount = item!!.limit_time.toLong()
                Observable.interval(0, 1, TimeUnit.SECONDS)
                        .take((timeCount + 1).toLong())
                        .map { aLong -> timeCount - aLong }
                        .doOnSubscribe {
                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : DefaultObserver<Long>() {
                            override fun onNext(t: Long) {
                                if (t >= 60) {
                                    val string = mContext.getString(R.string.order_canceled)
                                    val format = String.format(string, (t / 60).toString() + ":" + (t % 60))
                                    tv_down_time.text = format
                                } else {
                                    val string = mContext.getString(R.string.order_canceled)
                                    val format = String.format(string, (t).toString())
                                    tv_down_time.text = format
                                }
                            }

                            override fun onError(e: Throwable) {
                                tv_down_time.text = ""
                            }

                            override fun onComplete() {
                                if (null != mCountdownEndingListener) {//倒计时结束
                                    mCountdownEndingListener.countdownEnding()
                                }
                            }
                        })
                val rec_wait_pay = helper.getView<RecyclerView>(R.id.rec_wait_pay)
                rec_wait_pay.layoutManager = LinearLayoutManager(mContext)
                rec_wait_pay.adapter = OrderGoodsAdapter(R.layout.item_order_goods, item.items)

                helper.setText(R.id.tv_order_describe, mContext.getString(R.string.tv_total) +" "+item.items.size+" " + mContext.getString(R.string.total_price) + item.order_amount/*+ mContext.getString(R.string.tv_freight)+item.delivery_amount+")"*/)
                helper.addOnClickListener(R.id.btn_pay)
                helper.addOnClickListener(R.id.btn_cancel)
            }
            2 -> {//待发货
                val rec_wait_send = helper.getView<RecyclerView>(R.id.rec_wait_send)
                rec_wait_send.layoutManager = LinearLayoutManager(mContext)
                rec_wait_send.adapter = OrderGoodsAdapter(R.layout.item_order_goods, item!!.items)
                if (item.self_pick=="0"){//待发货
                    helper.setText(R.id.tv_title_status,mContext.getString(R.string.tv_seller_send))
                }else{// 1 待提货
                    helper.setText(R.id.tv_title_status,mContext.getString(R.string.pending_collect1))
                }
                helper.setText(R.id.tv_order_describe, mContext.getString(R.string.tv_total) +" "+item.items.size+" "+ mContext.getString(R.string.total_price) + item.order_amount/*+ mContext.getString(R.string.tv_freight)+item.delivery_amount+")"*/)
                helper.addOnClickListener(R.id.btn_cancel)
            }
            3 -> {//待收货
                val rec_wait_receive = helper.getView<RecyclerView>(R.id.rec_wait_receive)
                rec_wait_receive.layoutManager = LinearLayoutManager(mContext)
                rec_wait_receive.adapter = OrderGoodsAdapter(R.layout.item_order_goods, item!!.items)

                helper.setText(R.id.tv_order_describe, mContext.getString(R.string.tv_total) +" "+item.items.size+" "+ mContext.getString(R.string.total_price) + item.order_amount/*+ mContext.getString(R.string.tv_freight)+item.delivery_amount+")"*/)
                helper.addOnClickListener(R.id.btn_confirm_receive)
                helper.addOnClickListener(R.id.btn_cancel)
            }
            4 -> {//已完成
                val rec_complete = helper.getView<RecyclerView>(R.id.rec_complete)
                rec_complete.layoutManager = LinearLayoutManager(mContext)
                rec_complete.adapter = OrderGoodsAdapter(R.layout.item_order_goods, item!!.items)

                helper.setText(R.id.tv_order_describe, mContext.getString(R.string.tv_total) + " "+item.items.size+" " + mContext.getString(R.string.total_price) + item.order_amount/*+ mContext.getString(R.string.tv_freight)+item.delivery_amount+")"*/)
                helper.addOnClickListener(R.id.btn_delete)
                helper.addOnClickListener(R.id.btn_comment)
                val btn_comment = helper.getView<Button>(R.id.btn_comment)
                if(item.is_commented=="0"){
                    btn_comment.visibility= View.VISIBLE
                }else{
                    btn_comment.visibility=View.GONE
                }
            }
            7 -> {//退款中
                val rec_complete = helper.getView<RecyclerView>(R.id.rec_refunding)
                rec_complete.layoutManager = LinearLayoutManager(mContext)
                rec_complete.adapter = OrderGoodsAdapter(R.layout.item_order_goods, item!!.items)

                helper.setText(R.id.tv_order_describe, mContext.getString(R.string.tv_total) + " "+item.items.size+" " + mContext.getString(R.string.total_price) + item.order_amount/*+ mContext.getString(R.string.tv_freight)+item.delivery_amount+")"*/)
                helper.addOnClickListener(R.id.btn_delete)
            }
            8 -> {//已退款
                val rec_complete = helper.getView<RecyclerView>(R.id.rec_refunded)
                rec_complete.layoutManager = LinearLayoutManager(mContext)
                rec_complete.adapter = OrderGoodsAdapter(R.layout.item_order_goods, item!!.items)

                helper.setText(R.id.tv_order_describe, mContext.getString(R.string.tv_total) + " "+item.items.size+" " + mContext.getString(R.string.total_price) + item.order_amount/*+ mContext.getString(R.string.tv_freight)+item.delivery_amount+")"*/)
                helper.addOnClickListener(R.id.btn_delete)
            }
            -1 -> {//已取消
                val rec_cancel = helper.getView<RecyclerView>(R.id.rec_cancel)
                rec_cancel.layoutManager = LinearLayoutManager(mContext)
                rec_cancel.adapter = OrderGoodsAdapter(R.layout.item_order_goods, item!!.items)

                helper.setText(R.id.tv_order_describe, mContext.getString(R.string.tv_total) + " "+item.items.size+" " + mContext.getString(R.string.total_price) + item.order_amount/*+ mContext.getString(R.string.tv_freight)+item.delivery_amount+")"*/)
                helper.addOnClickListener(R.id.btn_delete)
            }
        }
    }

    private lateinit var mCountdownEndingListener: OnCountdownEndingListener

    interface OnCountdownEndingListener {
        fun countdownEnding()
    }

    public fun setOnCountdownEndingListener(listener: OnCountdownEndingListener) {
        mCountdownEndingListener = listener
    }
}