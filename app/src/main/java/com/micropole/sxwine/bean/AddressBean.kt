package com.micropole.sxwine.bean

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * Description:
 * Created by DarkHorse on 2018/6/7.
 */
class AddressBean(
        var address_id: String,
        var receiver: String,
        var mobile: String,
        var address_detail: String
) : Serializable
