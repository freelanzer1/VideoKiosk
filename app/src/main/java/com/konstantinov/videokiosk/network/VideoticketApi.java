package com.konstantinov.videokiosk.network;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface VideoticketApi {
    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
