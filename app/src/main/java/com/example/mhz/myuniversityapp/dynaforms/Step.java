package com.example.mhz.myuniversityapp.dynaforms;

import com.google.gson.JsonArray;

/**
 * Created by mhz on 26/04/19.
 */

public class Step {

    private String step_uid;
    private String step_type_obj;
    private String step_uid_obj;
    private String step_condition;
    private String step_position;
    private String step_mode;
    private String obj_title;
    private String obj_description;
    private JsonArray triggers;

    public Step(String step_uid, String step_type_obj, String step_uid_obj, String step_condition, String step_position, String step_mode, String obj_title, String obj_description, JsonArray triggers) {
        this.step_uid = step_uid;
        this.step_type_obj = step_type_obj;
        this.step_uid_obj = step_uid_obj;
        this.step_condition = step_condition;
        this.step_position = step_position;
        this.step_mode = step_mode;
        this.obj_title = obj_title;
        this.obj_description = obj_description;
        this.triggers = triggers;
    }

    public String getStep_uid() {
        return step_uid;
    }

    public String getStep_type_obj() {
        return step_type_obj;
    }

    public String getStep_uid_obj() {
        return step_uid_obj;
    }

    public String getStep_condition() {
        return step_condition;
    }

    public String getStep_position() {
        return step_position;
    }

    public String getStep_mode() {
        return step_mode;
    }

    public String getObj_title() {
        return obj_title;
    }

    public String getObj_description() {
        return obj_description;
    }

    public JsonArray getTriggers() {
        return triggers;
    }
}
