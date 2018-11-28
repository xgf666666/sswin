package com.micropole.sxwine.utils.network

import com.micropole.sxwine.bean.*
import com.micropole.sxwine.module.login.bean.ADEntity
import com.micropole.sxwine.module.login.bean.LoginEntity
import com.micropole.sxwine.module.login.bean.UserProtocolEntity
import com.micropole.sxwine.module.personal.Bean.*
import com.micropole.sxwine.module.personal.Bean.MyOrderItemEntity
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Description:
 *
 * @author DarkHorse
 * @date 2018/5/24
 */
interface BaseService {

    /**
     * 刷新toke
     */
    @Headers(API.TOKEN_LONG + ":1")
    @POST("apitoken_refresh")
    fun refreshToken(): Call<BaseResponseBean<TokenBean>>

    /**
     * 获取首页轮播图
     */
    @POST("silde_pictures")
    fun getBanner(): Observable<BaseResponseBean<ArrayList<BannerBean>>>

    /**
     * 获取首页分类
     */
    @POST("categories")
    @FormUrlEncoded
    fun getHomeClassify(
            @Field("page") page: String = "",
            @Field("type") type: String = "1",
            @Field("per_page") per_page: String = ""
    ): Observable<BaseResponseBean<ArrayList<ClassifyBean>>>

    /**
     * 获取更多分类
     */
    @POST("categories")
    @FormUrlEncoded
    fun getMoreClassify(
            @Field("page") page: String,
            @Field("type") type: String = "2",
            @Field("per_page") per_page: String = ""
    ): Observable<BaseResponseBean<ArrayList<ClassifyBean>>>

    /**
     * 获取商品列表
     */
    @POST("goodses")
    @FormUrlEncoded
    fun getGoods(
            @Field("category_id") category_id: String = "",
            @Field("keyword") keyword: String = "",
            @Field("page") page: Int = 0,
            @Field("per_page") per_page: String = "",
            @Field("sort") sort: String = "",
            @Field("direction") direction: String = "",
            @Field("type") type: String = ""
    ): Observable<BaseResponseBean<HomeResult>>

    /**
     * 获取商品详情
     */
    @POST("goods_detail")
    @FormUrlEncoded
    fun getGoodsDetail(@Field("goods_id") goods_id: Int): Observable<BaseResponseBean<GoodDetailBean>>

    /**
     * 发送验证码
     */
    @FormUrlEncoded
    @POST("verify_code")
    fun sendCode(
            @Field("mobile") mobile: String?,
            @Field("type") type: String?,
            @Field("message_code") message_code: String?
    ): Observable<BaseResponseBean<Any>>

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("user")
    fun register(
            @Field("mobile") mobile: String?,
            @Field("password") password: String?,
            @Field("password_confirmation") password_confirmation: String?,
            @Field("verify_code") verify_code: String?,
            @Field("recommend_code") recommend_code: String?,
            @Field("credentials_type") credentials_type: String?,
            @Field("credentials_no") credentials_no: String?,
            @Field("real_name") real_name: String?,
            @Field("message_code") message_code: String?
    ): Observable<BaseResponseBean<LoginEntity>>

    /**
     * 密码登录
     */
    @FormUrlEncoded
    @POST("login_password")
    fun pwdLogin(
            @Field("mobile") mobile: String?,
            @Field("password") password: String?
    ): Observable<BaseResponseBean<LoginEntity>>

    /**
     * 短信验证码登录
     */
    @FormUrlEncoded
    @POST("login_verify")
    fun codeLogin(
            @Field("mobile") mobile: String?,
            @Field("verify_code") verify_code: String?,
            @Field("message_code") message_code: String?
    ): Observable<BaseResponseBean<LoginEntity>>

    /**
     * 忘记密码
     */
    @FormUrlEncoded
    @POST("seekpassword")
    fun resetPwd(
            @Field("mobile") mobile: String?,
            @Field("password") password: String?,
            @Field("password_confirmation") password_confirmation: String?,
            @Field("verify_code") verify_code: String?,
            @Field("message_code") message_code: String?
    ): Observable<BaseResponseBean<LoginEntity>>

    /**
     * 我的分享
     */
    @POST("user_invite_code")
    fun getShareData(): Observable<BaseResponseBean<ShareBean>>;

    /**
     *获取购物车列表
     */
    @POST("carts")
    @FormUrlEncoded
    fun getCarGoods(@Field("page") page: Int, @Field("per_page") per_page: String = ""): Observable<BaseResponseBean<CarResult>>

    /**
     * 增加商品到购物车
     */
    @POST("add_cart")
    @FormUrlEncoded
    fun addCar(@Field("goods_id") goods_id: Int, @Field("quantity") quantity: Int = 1): Observable<BaseResponseBean<Any>>

    /**
     * 减少购物车商品数量
     */
    @POST("reduce_cart")
    @FormUrlEncoded
    fun subCar(@Field("goods_id") goods_id: Int, @Field("quantity") quantity: Int = 1): Observable<BaseResponseBean<Any>>

    /**
     * 购物车结算
     */
    @POST("settle_cart")
    @FormUrlEncoded
    fun settleCar(@Field("cart_ids") cart_ids: String): Observable<BaseResponseBean<SettleResult>>

    /**
     * 立即购买
     */
    @POST("buy_now")
    @FormUrlEncoded
    fun buyNow(@Field("goods_id") goods_id: String, @Field("quantity") quantity: String): Observable<BaseResponseBean<SettleResult>>

    /**
     * 购物车删除
     */
    @POST("destroy_cart")
    @FormUrlEncoded
    fun deleteGoods(@Field("cart_ids") cart_ids: String): Observable<BaseResponseBean<Any>>

    /**
     * 修改商品数量
     */
    @POST("edit_quantity")
    @FormUrlEncoded
    fun updateGoods(@Field("cart_id") cart_id: Int, @Field("quantity") quantity: Int): Observable<BaseResponseBean<Any>>

    /**
     * 提交订单
     */
    @POST("commit_order")
    @FormUrlEncoded
    fun submitOrder(@Field("temp_id") temp_id: String): Observable<BaseResponseBean<ConfirmResult>>

    /**
     * 更新临时订单
     */
    @POST("change_order_info")
    @FormUrlEncoded
    fun updateOrder(@Field("temp_id") temp_id: String, @Field("address_id") address_id: String?, @Field("self_pick") self_pick: Int, @Field("self_pick_address_id") self_pick_address_id: Int?, @Field("mobile") mobile: String?, @Field("receiver") receiver: String?): Observable<BaseResponseBean<SettleResult>>

    /**
     * 确认支付
     */
    @POST("pay_order")
    @FormUrlEncoded
    fun payOrder(@Field("order_id") order_id: String, @Field("type") type: String): Observable<BaseResponseBean<PayResult>>

    @POST("braintree_notify")
    @FormUrlEncoded
    fun paypalBack(@Field("order_id") order_id: String, @Field("nonce") nonce: String): Observable<BaseResponseBean<Any>>

    /**
     * 余额支付
     */
    @POST("balance_pay")
    @FormUrlEncoded
    fun balancePay(@Field("order_id") order_id: String, @Field("pay_password") pay_password: String): Observable<BaseResponseBean<Any>>

    /**
     * 收藏商品
     */
    @POST("collect")
    @FormUrlEncoded
    fun collectGoods(@Field("goods_id") goods_id: Int): Observable<BaseResponseBean<Any>>

    /**
     * 取消收藏
     */
    @POST("cancel_collect")
    @FormUrlEncoded
    fun cancelGoods(@Field("goods_id") goods_id: Int): Observable<BaseResponseBean<Any>>

    /**
     * 获取地址列表
     */
    @FormUrlEncoded
    @POST("address_list")
    fun getAddressList(
            @Field("page") page: String?,
            @Field("per_page") per_page: String?
    ): Observable<BaseResponseBean<List<AddressManagerEntity>>>

    /**
     * 添加地址
     */
    @FormUrlEncoded
    @POST("address")
    fun addAddress(
            @Field("mobile") mobile: String?,
            @Field("receiver") receiver: String?,
            @Field("address_detail") address_detail: String?,
            @Field("is_default") is_default: String?
    ): Observable<BaseResponseBean<AddAddressEntity>>

    /**
     * 编辑收货地址
     */
    @FormUrlEncoded
    @POST("address_modify")
    fun compileAddress(
            @Field("mobile") mobile: String?,
            @Field("receiver") receiver: String?,
            @Field("address_detail") address_detail: String?,
            @Field("is_default") is_default: String?,
            @Field("address_id") address_id: String?
    ): Observable<BaseResponseBean<AddAddressEntity>>

    /**
     * 用户资料
     */
    @POST("user_info")
    fun getUserInfo(): Observable<BaseResponseBean<UserInfoEntity>>

    /**
     * 收藏列表
     */
    @FormUrlEncoded
    @POST("collect_list")
    fun getMyCollectList(
            @Field("page") page: String?,
            @Field("per_page") per_page: String?
    ): Observable<BaseResponseBean<List<MyCollectEntity>>>

    /**
     * 常见问题
     */
    @POST("questions")
    fun getFQAList(): Observable<BaseResponseBean<List<FQAEntity>>>

    /**
     * 我的团队
     */
    @POST("under_team")
    @FormUrlEncoded
    fun getMyTeam(@Field("type") type: Int, @Field("page") page: Int): Observable<BaseResponseBean<MyTeamResult>>
    /*fun getFQAList() : Observable<BaseResponseBean<List<FQAEntity>>>*/

    /**
     * 我的钱包
     */
    @POST("user_wallet_info")
    fun getMyWallet(): Observable<BaseResponseBean<MyWalletEntity>>

    /**
     * 今天,昨天收益详情
     */
    @FormUrlEncoded
    @POST("everyday_detail")
    fun getEarningsDetails1Data(
            @Field("type") type: String?
    ): Observable<BaseResponseBean<EarningsDetails1Entity>>

    /**
     * 累积收益详情
     */
    @POST("commission_total_list")
    fun getAccumulateEarnings(): Observable<BaseResponseBean<AccumulateEarningsEntity>>

    /**
     * 获取年,季度 收益详情
     */
    @FormUrlEncoded
    @POST("best_detail")
    fun getEarningsDetails2Data(
            @Field("type") type: String?,
            @Field("page") page: String?,
            @Field("per_page") per_page: String?
    ): Observable<BaseResponseBean<List<EarningsDetails2Entity>>>

    /**
     * 提现明细列表
     */
    @FormUrlEncoded
    @POST("user_withdraw_list")
    fun getWithDrawListData(
            @Field("page") page: String?
    ): Observable<BaseResponseBean<List<WithdrawListEntity>>>

    @FormUrlEncoded
    @POST("organization_detail")
    fun getOrganizationEarningsData(
            @Field("page") page: String?,
            @Field("per_page") per_page: String?
    ): Observable<BaseResponseBean<List<OrganizationEarningsEntity>>>

    /**
     * 修改昵称
     */
    @FormUrlEncoded
    @POST("nickname")
    fun alterNickname(
            @Field("nickname") nickname: String?
    ): Observable<BaseResponseBean<Any>>

    /**
     * 修改性别
     */
    @FormUrlEncoded
    @POST("sex")
    fun alterSex(
            @Field("sex") sex: String?
    ): Observable<BaseResponseBean<Any>>

    /**
     * 上传图片
     */
    @Multipart
    @POST("file_store")
    fun uploadPrice(
            @Part newfile: MultipartBody.Part,
            @Part("type") type: RequestBody
    ): Observable<BaseResponseBean<UploadPriceEntity>>

    /**
     * 修改头像
     */
    @FormUrlEncoded
    @POST("avatar")
    fun alterAvatar(
            @Field("new_avatar") new_avatar: String?
    ): Observable<BaseResponseBean<UploadPriceEntity>>

    /**
     * 修改/设置登录密码
     */
    @FormUrlEncoded
    @POST("modify_password")
    fun alterOrSettingLoginPwd(
            @Field("mobile") mobile: String?,
            @Field("password") password: String?,
            @Field("password_confirmation") password_confirmation: String?,
            @Field("verify_code") verify_code: String?,
            @Field("message_code") message_code: String?
    ): Observable<BaseResponseBean<Any>>

    /**
     * 修改/设置交易密码
     */
    @FormUrlEncoded
    @POST("modify_pay_password")
    fun alterOrSettingPayPwd(
            @Field("mobile") mobile: String?,
            @Field("user_pay_password") password: String?,
            @Field("password_confirmation") password_confirmation: String?,
            @Field("verify_code") verify_code: String?,
            @Field("message_code") message_code: String?
    ): Observable<BaseResponseBean<Any>>

    /**
     * 意见反馈
     */
    @FormUrlEncoded
    @POST("feedback")
    fun feedback(
            @Field("content") content: String?
    ): Observable<BaseResponseBean<Any>>

    /**
     * 在线客服
     */
    @POST("service_online")
    fun serviceOnline(): Observable<BaseResponseBean<ContactServiceEntity>>

    /**
     * 客服电话
     */
    @POST("consume_service")
    fun consumeService(): Observable<BaseResponseBean<ContactServiceEntity>>

    /**
     * 我的订单
     */
    @FormUrlEncoded
    @POST("order_list")
    fun getMyOrderData(
            @Field("page") page: String?,
            @Field("per_page") per_page: String?,
            @Field("order_status") order_status: String?
    ): Observable<BaseResponseBean<List<MyOrderItemEntity>>>

    /**
     * 取消订单
     */
    @FormUrlEncoded
    @POST("cancel_order")
    fun cancelOrder(
            @Field("order_id") order_id: String?
    ): Observable<BaseResponseBean<Any>>

    /**
     * 删除订单
     */
    @FormUrlEncoded
    @POST("delete_order")
    fun deleteOrder(
            @Field("order_id") order_id: String?
    ): Observable<BaseResponseBean<Any>>

    /**
     * 确认收货
     */
    @FormUrlEncoded
    @POST("confirm_order")
    fun confirmReceive(
            @Field("order_id") order_id: String?
    ): Observable<BaseResponseBean<Any>>

    /**
     * 订单详情
     */
    @FormUrlEncoded
    @POST("order_detail")
    fun getOrderDetailsData(
            @Field("order_id") order_id: String?
    ): Observable<BaseResponseBean<OrderDetailsEntity>>

    /**
     * 获取订单状态红点显示
     */
    @POST("order_status")
    fun getOrderStatus(): Observable<BaseResponseBean<OrderStatusEntity>>

    /**
     * 提现
     */
    @FormUrlEncoded
    @POST("apply_withdraw")
    fun earningsWithdraw(
            @Field("account") account: String?,
            @Field("type") type: String?,
            @Field("withdraw_amount") withdraw_amount: String?,
            @Field("deal_password") deal_password: String?
    ): Observable<BaseResponseBean<EarningsWithdrawEntity>>

    /**
     * 提现2
     */
    @FormUrlEncoded
    @POST("apply_withdraw")
    fun earningsWithdraw2(
            @Field("account") account: String?,
            @Field("card_holder") card_holder: String?,
            @Field("withdraw_amount") withdraw_amount: String?,
            @Field("deal_password") deal_password: String?,
            @Field("bank_name") bank_name: String?,
            @Field("swift_code") swift_code: String?,
            @Field("bank_address") bank_address: String?
    ): Observable<BaseResponseBean<EarningsWithdrawEntity>>

    /**
     * 检测是否设置支付密码
     */
    @POST("check_pay_password")
    fun checkPayPwd(): Observable<BaseResponseBean<CheckPayPwdEntity>>

    /**
     * 删除地址
     */
    @FormUrlEncoded
    @POST("address_delete")
    fun deleteAddress(
            @Field("address_id") address_id: String?
    ): Observable<BaseResponseBean<Any>>

    /**
     * 广告
     */
    @POST("ad")
    fun getAd(): Observable<BaseResponseBean<ADEntity>>

    /**
     * 获取消息列表
     */
    @POST("message_list")
    fun getMessageList(): Observable<BaseResponseBean<ArrayList<MessageBean>>>


    /**
     * 待评价商品列表
     */
    @FormUrlEncoded
    @POST("wait_comment_goods")
    fun getCommentGoodsList(
            @Field("order_id") order_id: String?
    ): Observable<BaseResponseBean<List<CommentGoodsListEntity>>>

    /**
     * 订单商品评价
     */
    @FormUrlEncoded
    @POST("publish_comment")
    fun orderGoodsComment(
            @Field("order_id") order_id: String?,
            @Field("goods_id") goods_id: String?,
            @Field("score") score: String?,
            @Field("content") content: String?,
            @Field("photos") photos: String?
    ): Observable<BaseResponseBean<Any>>

    /**
     * 获取评论列表
     */
    @POST("user_comment_list")
    @FormUrlEncoded
    fun getCommentList(@Field("goods_id") goods_id: Int): Observable<BaseResponseBean<ArrayList<GoodDetailBean.Comment>>>

    /**
     * 直推奖详情
     */
    @FormUrlEncoded
    @POST("directly_detail")
    fun getDirectlyDetail(
            @Field("page") page: String?,
            @Field("per_page") per_page: String?
    ): Observable<BaseResponseBean<List<OrganizationEarningsEntity>>>

    /**
     * 组织奖详情
     */
    @FormUrlEncoded
    @POST("organization_detail")
    fun getOrganizationDetail(
            @Field("page") page: String?,
            @Field("per_page") per_page: String?
    ): Observable<BaseResponseBean<List<OrganizationEarningsEntity>>>

    /**
     * 重复消费奖详情
     */
    @FormUrlEncoded
    @POST("consume_detail")
    fun getConsumeDetail(@Field("page") page: String?, @Field("per_page") per_page: String?): Observable<BaseResponseBean<List<OrganizationEarningsEntity>>>

    @FormUrlEncoded
    @POST("user_comment_list")
    fun getCommentList(@Field("goods_id") goods_id: Int, @Field("page") page: Int): Observable<BaseResponseBean<ArrayList<GoodDetailBean.Comment>>>

    /**
     * 获取自提地址
     */
    @POST("/api/pick_address_list")
    fun getConfirmAddressList(): Observable<BaseResponseBean<ConfirmAddressResult>>

    @POST("register_protocol")
    fun getUserProtocol(): Observable<BaseResponseBean<UserProtocolEntity>>

    /**
     * 银行卡记录
     */
    @POST("bank_record")
    fun getBankRecord(): Observable<BaseResponseBean<BankRecordEntity>>
}
