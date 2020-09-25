package com.konstantinov.videokiosk;

import android.net.Uri;

public interface EventHandler {

    void execute(Uri uri);
    void falure(String videoUrl);

}
