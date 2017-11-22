package com.exsun.commonlibrary.frame.network.result;

import com.exsun.commonlibrary.frame.network.exception.HttpTimeException;

import rx.functions.Func1;


/**
 * 服务器返回数据判断
 *
 * Created by MrKong on 2017/9/12.
 */

public class ResulteFunc implements Func1<Object,Object>
{
    @Override
    public Object call(Object o) {
        if (o == null || "".equals(o.toString())) {
            throw new HttpTimeException("数据错误");
        }
        return o;
    }
   
}
