package com.example.mhz.myuniversityapp.dynaforms.post;

/**
 * Created by mhz on 28/04/19.
 */

public class PostResponse {

    private String app_uid;
    private String app_number;

    public PostResponse(String app_uid, String app_number) {
        this.app_uid = app_uid;
        this.app_number = app_number;
    }
}
