package com.micropole.sxwine.bean

import com.google.gson.annotations.SerializedName


/**
 * Description:
 * Created by DarkHorse on 2018/9/14.
 */

data class ConfirmAddressResult(
        @SerializedName("list") var list: List<X>,
        @SerializedName("default_info") var defaultInfo: DefaultInfo
) {
    data class X(
            @SerializedName("address_id") var addressId: String,
            @SerializedName("shop_name") var shopName: String,
            @SerializedName("address") var address: String,
            @SerializedName("en_address") var enAddress: String
    )

    data class DefaultInfo(
            @SerializedName("mobile") var mobile: String,
            @SerializedName("receiver") var receiver: String
    )
}


