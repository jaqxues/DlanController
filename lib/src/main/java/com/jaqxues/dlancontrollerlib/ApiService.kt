package com.jaqxues.dlancontrollerlib

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project DlanController.<br>
 * Date: 29.07.2019 - Time 12:34.
 */
internal interface ApiService {
    @POST("/cgi-bin/htmlmgr")
    @Headers("Authorization: Basic YWRtaW46MDkwMDg4NzgwMzMzNDg3OA==")
    @FormUrlEncoded
    suspend fun sendRequest(
        @Field(":sys:Wireless.WLANRadio") wlan: String,
        @Field("_file") file: String,
        @Field("_style") style: String,
        @Field("_lang") lang: String,
        @Field("_dir") dir: String,
        @Field("_page") page: String,
        @Field("_idx") idx: String,
        @Field("_sid") sid: String,
        @Field("_csrf") csrf: String
    )

    @GET("/cgi-bin/htmlmgr?_file=%2Fwgl%2Fmain.wgl&_sid=&_style=std&_lang=en&_page=home&_dir=status")
    @Headers(
        "Authorization: Basic YWRtaW46MDkwMDg4NzgwMzMzNDg3OA==",
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3",
        "Referer: http://192.168.178.22/cgi-bin/htmlmgr?_file=%2Fwgl%2Fmain.wgl&_sid=&_style=std&_lang=&_page=home&_dir=status",
        "Accept-Encoding: gzip, deflate",
        "Connection: keep-alive",
        "Upgrade-Insecure-Requests: 1",
        "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36"
    )
    suspend fun getState(): Response<ResponseBody>
}