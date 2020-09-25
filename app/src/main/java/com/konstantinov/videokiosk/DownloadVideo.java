package com.konstantinov.videokiosk;

import android.util.Log;
import androidx.annotation.NonNull;

import com.konstantinov.videokiosk.network.NetworkService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadVideo {
    private EventHandler handler;
    private String videoUrl;
    MainActivity myActivity;
    DownloadVideo(EventHandler handler, String videoUrl, MainActivity myActivity){

        this.handler= handler; //переданный в параметре обработчик сохраняем в локальной переменной
        this.videoUrl = videoUrl;
        this.myActivity = myActivity;
    }
    public void startDownloadVideo(){
        NetworkService.getInstance()// выполняем запрос через retrofit для загрузки видео
                .getVideoticketApi ()
                .downloadFile(videoUrl)
                .enqueue(new Callback<ResponseBody> () {
                    @Override
                    public void onResponse( Call<ResponseBody> call,
                                            Response<ResponseBody> response) {
                        if (response.isSuccessful()) {

                            SaveVideo saveVideo = new SaveVideo ( response,  videoUrl , myActivity, handler);
                            saveVideo.startSaveVideo (); // сохраняем видео
                        }
                        else {
                            Log.d("RX", "не удалось связаться с сервером");
                            handler.falure (videoUrl);
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Log.d ( "RX", "ошибка загрузки : " +t );
                        handler.falure (videoUrl);
                    }
                });
    }
}
