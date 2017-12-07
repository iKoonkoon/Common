package com.ikoon.common.model;

import com.ikoon.common.api.MvpTestApi;
import com.ikoon.common.bean.HttpRequestEntityMessageInfos;
import com.ikoon.common.contract.MvpContract;

/**
 * @author MrKong
 * @date 2017/10/27
 */

public class MvpModel implements MvpContract.Model
{
    @Override
    public void startGet(String username, String password, MvpTestApi mvpTestApi)
    {
        mvpTestApi.getApi(username, password);
    }
    
    @Override
    public void startPost(HttpRequestEntityMessageInfos httpRequestEntityMessageInfos, MvpTestApi mvpTestApi)
    {
        mvpTestApi.postApi(httpRequestEntityMessageInfos);
    }
    
    @Override
    public void testDo(String s)
    {
        
    }
}
