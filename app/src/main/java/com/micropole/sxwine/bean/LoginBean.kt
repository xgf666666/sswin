package com.micropole.sxwine.bean

/**
 * Description:
 * Created by DarkHorse on 2018/5/24.
 */

data class LoginResult(
        val user_id: String,
        val user_phone: String,
        val user_sex: String,
        val invite: String,
        val user_img: String,
        val nickname: String,
        val number: Number,
        val token: String
)

data class Number(
        val obligation: String,
        val delivery: String,
        val send_goods: String,
        val after_sale: String,
        val evaluate: String
)