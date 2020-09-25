package com.konstantinov.videokiosk;

import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;


public class DownloadVideoHandler implements EventHandler {
    private int lengthPlaylist;
    private int cursorPlaylist = 0;
    private MainActivity myActivity;
    private ArrayList<Uri> playUri;

    DownloadVideoHandler(int lengthPlaylist, MainActivity myActivity) {
        this.lengthPlaylist = lengthPlaylist;
        this.myActivity = myActivity;
        playUri = new ArrayList <Uri>();
    }


    @Override
    public void execute(Uri uri) {

        playUri.add ( uri );
        cursorPlaylist++;
        Log.d("RX", "Загрузили и сохранили видео "+ cursorPlaylist + " из " +lengthPlaylist);
        myActivity.textLoad.setText ( "Загрузили и сохранили видео "+ cursorPlaylist + " из " +lengthPlaylist );

        if (cursorPlaylist == lengthPlaylist) {
            myActivity.playVideo(playUri);
        }
    }

    @Override
    public void falure(String videoUrl) {
        //Видео не сохранилось, запускаем загрузку заново
        DownloadVideo downloadVideo = new DownloadVideo ( this, videoUrl, myActivity);
        downloadVideo.startDownloadVideo ();
    }

}
