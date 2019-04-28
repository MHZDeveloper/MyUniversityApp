package com.example.mhz.myuniversityapp.dynaforms.post;

import com.google.gson.JsonArray;

/**
 * Created by mhz on 28/04/19.
 */

public class PostRequest {

    private String pro_uid;
    private String tas_uid;
    private JsonArray variables;

    public PostRequest(String pro_uid, String tas_uid, JsonArray variables) {
        this.pro_uid = pro_uid;
        this.tas_uid = tas_uid;
        this.variables = variables;
    }
}
