package com.micropole.sxwine.utils.network;


/**
 * 描述：http数据异常类
 */

 class MyAPIException extends RuntimeException {
     public  int errorCode;

     MyAPIException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;

    }
}
