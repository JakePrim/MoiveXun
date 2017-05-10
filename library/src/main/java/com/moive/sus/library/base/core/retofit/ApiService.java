package com.moive.sus.library.base.core.retofit;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by linksus on 4/28 0028.
 * 初始化Retrofit
 */

public interface ApiService {

    @GET("{url}")
    Observable<BaseResponse<Object>> executeGet(@Path("url") String url, @QueryMap Map<String, String> maps);

    @GET("{url}")
    Observable<BaseResponse<Object>> executeGet(@Path("url") String url);

    @POST("{url}")
    Observable<ResponseBody> executePost(@Path("url") String url, @FieldMap Map<String, String> maps);

    @GET("/v2/movie/in_theaters")
    Observable<theaterBean> executePostno();

}
