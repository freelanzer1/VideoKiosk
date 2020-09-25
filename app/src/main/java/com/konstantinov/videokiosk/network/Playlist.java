package com.konstantinov.videokiosk.network;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Playlist {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private List<String> data = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

}
