package com.ikoon.common.bean;

/**
 * Created by MrKong on 2017/10/25.
 */

public class HttpRequestEntityMessageInfos
{
    public int PageIndex;
    public int PageSize;
    
    public HttpRequestEntityMessageInfos(int pageIndex, int pageSize) {
        PageIndex = pageIndex;
        PageSize = pageSize;
    }
}
