package com.exsun.commonlibrary.frame.network.exception;

/**
 * 回调统一请求异常
 *
 * Created by MrKong on 2017/9/12.
 */

public class ApiException extends Exception{
    /*错误码*/
    private int code;
    /*显示的信息*/
    private String displayMessage;
    
    public ApiException(Throwable e) {
        super(e);
    }
    
    public ApiException(Throwable cause,@CodeException.CodeEp int code, String showMsg) {
        super(showMsg, cause);
        setCode(code);
        setDisplayMessage(showMsg);
    }
    
    @CodeException.CodeEp
    public int getCode() {
        return code;
    }
    
    public void setCode(@CodeException.CodeEp int code) {
        this.code = code;
    }
    
    public String getDisplayMessage() {
        return displayMessage;
    }
    
    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
