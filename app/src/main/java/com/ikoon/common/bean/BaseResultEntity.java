package com.ikoon.common.bean;

/**
 * 回调信息统一封装类
 *
 * Created by WZG on 2016/7/16.
 */
public class BaseResultEntity<T>{
    /**
     * IsSuccess : true
     * Msg : null
     * ReturnCode : null
     * ReturnValue : {"access_token":"e989a8c44ee06b785b31f77d67b462cd","expires_in":31536000,"token_type":"Bearer"}
     */
    
    private boolean IsSuccess;
    private Object Msg;
    private Object ReturnCode;
    private ReturnValueBean ReturnValue;
    
    public boolean isIsSuccess()
    {
        return IsSuccess;
    }
    
    public void setIsSuccess(boolean IsSuccess)
    {
        this.IsSuccess = IsSuccess;
    }
    
    public Object getMsg()
    {
        return Msg;
    }
    
    public void setMsg(Object Msg)
    {
        this.Msg = Msg;
    }
    
    public Object getReturnCode()
    {
        return ReturnCode;
    }
    
    public void setReturnCode(Object ReturnCode)
    {
        this.ReturnCode = ReturnCode;
    }
    
    public ReturnValueBean getReturnValue()
    {
        return ReturnValue;
    }
    
    public void setReturnValue(ReturnValueBean ReturnValue)
    {
        this.ReturnValue = ReturnValue;
    }
    
    public static class ReturnValueBean
    {
        /**
         * access_token : e989a8c44ee06b785b31f77d67b462cd
         * expires_in : 31536000
         * token_type : Bearer
         */
        
        private String access_token;
        private int expires_in;
        private String token_type;
        public String getAccess_token()
        {
            return access_token;
        }
        
        public void setAccess_token(String access_token)
        {
            this.access_token = access_token;
        }
        
        public int getExpires_in()
        {
            return expires_in;
        }
        
        public void setExpires_in(int expires_in)
        {
            this.expires_in = expires_in;
        }
        
        public String getToken_type()
        {
            return token_type;
        }
        
        public void setToken_type(String token_type)
        {
            this.token_type = token_type;
        }
    }


//    //  判断标示
//    private int ret;
//    //    提示信息
//    private String msg;
//    //显示数据（用户需要关心的数据）
//    private T data;
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//
//    public int getRet() {
//        return ret;
//    }
//
//    public void setRet(int ret) {
//        this.ret = ret;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }
}
