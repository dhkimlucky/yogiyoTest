package com.test.yogiyo.yogiyotest.Model;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class UserData {

    private String login;
    private String ava_url;
    private long id;
    private boolean like = false;

    public UserData(JSONObject jsonObject) {
        try {
            this.setLogin(jsonObject.getString("login"));
            this.setId(jsonObject.getLong("id"));
            this.setAva_url(jsonObject.getString("avatar_url"));

        } catch (JSONException e ){
            Log.e("UserData", "Could not parse malformed JSON: \"" + jsonObject.toString() + "\"");
        } finally {
            Log.d("UserData", jsonObject.toString());
        }

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAva_url() {
        return ava_url;
    }

    public void setAva_url(String ava_url) {
        this.ava_url = ava_url;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}
