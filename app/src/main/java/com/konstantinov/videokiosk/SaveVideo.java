package com.konstantinov.videokiosk;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class SaveVideo {
    private EventHandler handler;
    private String videoUrl;
    private Response<ResponseBody> response;
    MainActivity myActivity;
    private Uri uri;

    SaveVideo(Response<ResponseBody> response,  String videoUrl, MainActivity myActivity, EventHandler handler){
    this.response = response;
    this.videoUrl = videoUrl;
    this.myActivity = myActivity;
    this.handler= handler;
    }


    void startSaveVideo(){

        int indexFile = videoUrl.indexOf("media/");
        String nameVideo = videoUrl.substring(indexFile +6);//из url генерим имя файла

        new AsyncTask<Void, Void, Boolean> () {

            protected void onPreExecute(){
                super.onPreExecute();
            }
            @Override
            protected Boolean doInBackground(Void... voids) {
                boolean writtenToDisk = writeResponseBodyToDisk(myActivity, response.body(), null, nameVideo);
                Log.d("RX", "загрузка файла " + nameVideo + " прошла успешно? " + writtenToDisk);
                return writtenToDisk;
            }
            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result); // это ОБЯЗАТЕЛЬНО нужно сделать, иначе
                // .get() не вернёт ничего, поскольку ни о каком результате понятия
                // иметь не будет.
                if (result == true) {
                    handler.execute(uri);
                }else {
                    handler.falure (videoUrl);
                    Log.d("RX", "не сохранилось видео " + nameVideo);
                }

            }
        }.execute();
    }
    private boolean writeResponseBodyToDisk(MainActivity myActivity, ResponseBody body, Object o, String nameVideo) {
        try {

            File mFile = new File(myActivity.getFilesDir().getAbsolutePath() + File.separator + nameVideo);
            uri= Uri.fromFile(mFile);
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream (mFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    // Log.d("-----", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

}


