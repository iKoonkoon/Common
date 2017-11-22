package com.ikoon.common.api;

import com.ikoon.common.bean.HttpRequestEntityMessageInfos;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by MrKong on 2017/9/13.
 */

public interface HttpApiService
{
    
    //上传图片
    @Multipart
    @POST(GlobalUrls.UPDATA_PICTURE)
    Observable<String> updataPicture(@Part MultipartBody.Part file,
                                     @Part("ServiceNum") RequestBody serviceNum,
                                     @Part("ConstructId") RequestBody constructId,
                                     @Part("VehicleNo") RequestBody vehicleNo,
                                     @Part("RecordHisId") RequestBody recordHisId);
    
    @GET(GlobalUrls.LOGIN_URL)
    Observable<String> loginService(@Query("username") String username, @Query("userpwd") String userpwd);
    
    @POST(GlobalUrls.GET_MESSAGE_INFO)
    Observable<String> messageInfos(@Body HttpRequestEntityMessageInfos httpRequestEntityMessageInfos);

}
