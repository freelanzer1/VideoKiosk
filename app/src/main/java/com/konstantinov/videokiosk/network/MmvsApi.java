package com.konstantinov.videokiosk.network;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MmvsApi {
    @GET("playlist.json")
    Call<Playlist> getRequest();
}
