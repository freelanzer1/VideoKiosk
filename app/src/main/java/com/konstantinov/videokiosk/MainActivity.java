package com.konstantinov.videokiosk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.konstantinov.videokiosk.network.NetworkService;
import com.konstantinov.videokiosk.network.Playlist;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {
    public TextView textLoad ;

    private int lengthPlaylist;
    private  VideoView videoPlayer;
    private int iPlay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        textLoad = findViewById(R.id.textLoad);

        NetworkService.getInstance()// выполняем запрос через retrofit для получения плейлиста
                .getMmvsApi ()
                .getRequest()
                .enqueue(new Callback<Playlist> () {

                    @Override
                    public void onResponse( Call<Playlist> call,
                                            Response<Playlist> response) {

                        if ( response.body ().getData ().size () != 0 )
                        {

                            textLoad.setText ( "Плейлист загружен, загружаем видео.." );
                            lengthPlaylist = response.body ().getData ().size ();
                            DownloadVideoHandler downloadVideoHandler = new DownloadVideoHandler (lengthPlaylist, MainActivity.this);
                            for (String videoUrl : response.body ().getData ()){// для каждго элемента плейлиста стартуем скачивание и сохранение
                                DownloadVideo downloadVideo = new DownloadVideo ( downloadVideoHandler, videoUrl, MainActivity.this);
                                downloadVideo.startDownloadVideo ();
                            }
                        }
                    }

                    @Override

                    public void onFailure(@NonNull Call<Playlist> call, @NonNull Throwable t) {
                        Log.d ( "RX", "ошибка загрузки плейлиста: " +t );
                    }
                });

    }
    public void playVideo(ArrayList<Uri> playUri){

        textLoad.setVisibility(View.GONE);
        videoPlayer =  (VideoView)findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(this);
        videoPlayer.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoPlayer);
        iPlay = 0;
        videoPlayer.setVideoURI ( playUri.get ( iPlay ) );
        videoPlayer.start ( );
        videoPlayer.setOnCompletionListener ( new MediaPlayer.OnCompletionListener ( ) {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                iPlay++;
                if (playUri.size ( ) == iPlay){
                    iPlay = 0;
                }
                    videoPlayer.setVideoURI ( playUri.get ( iPlay ) );
                    videoPlayer.start ( );
            }

        });

    }


}


