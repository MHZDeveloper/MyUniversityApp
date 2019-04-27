package com.example.mhz.myuniversityapp.dynaforms;

/**
 * Created by mhz on 26/04/19.
 */

public class DynaForm {
    private String dyn_uid;
    private String dyn_title;
    private String dyn_description;
    private String dyn_type;
    private String dyn_content;
    private String dyn_version;
    private String dyn_update_date;

    public DynaForm(String dyn_uid, String dyn_title, String dyn_description, String dyn_type, String dyn_content, String dyn_version, String dyn_update_date) {
        this.dyn_uid = dyn_uid;
        this.dyn_title = dyn_title;
        this.dyn_description = dyn_description;
        this.dyn_type = dyn_type;
        this.dyn_content = dyn_content;
        this.dyn_version = dyn_version;
        this.dyn_update_date = dyn_update_date;
    }

    public String getDyn_uid() {
        return dyn_uid;
    }

    public String getDyn_title() {
        return dyn_title;
    }

    public String getDyn_description() {
        return dyn_description;
    }

    public String getDyn_type() {
        return dyn_type;
    }

    public String getDyn_content() {
        return dyn_content;
    }

    public String getDyn_version() {
        return dyn_version;
    }

    public String getDyn_update_date() {
        return dyn_update_date;
    }
}
