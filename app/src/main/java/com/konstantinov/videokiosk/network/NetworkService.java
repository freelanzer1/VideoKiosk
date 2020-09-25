package com.konstantinov.videokiosk.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class NetworkService {//код для настройки библиотеки Retrofit.
    private static NetworkService mInstance; //singleton
    private static final String BASE_URL_JSON = "https://mmvs.ru/GoBoards/test/";
    private static final String BASE_URL_VIDEO = "https://office1.videoticket.ru:8123/";
    private Retrofit jsonRetrofit;
    private Retrofit videoRetrofit;

    private NetworkService() {
        //RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.create();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()//все данные запроса, включая URL, заголовки, тело, выведены в лог
                .addInterceptor(interceptor);

        jsonRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_JSON)
                //.addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

        videoRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_VIDEO)
                //.addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                //.client(client.build())
                .build();
    }

    public static NetworkService getInstance() {//singleton
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public MmvsApi getMmvsApi() {
        return jsonRetrofit.create(MmvsApi.class);
    }

    public VideoticketApi getVideoticketApi() {
        return videoRetrofit.create(VideoticketApi.class);
    }
}