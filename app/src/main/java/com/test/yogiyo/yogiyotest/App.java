package com.test.yogiyo.yogiyotest;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.test.yogiyo.yogiyotest.Model.UserData;
import com.test.yogiyo.yogiyotest.Util.LruBitmapCache;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    public static final String TAG = App.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static App mInstance;
    private  List<UserData> userDataList, likedUserList;
    public static String GITHUBUSER = "https://api.github.com/search/users?q=";
    public long totalCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        userDataList = new ArrayList<>();
        likedUserList = new ArrayList<>();
    }

    public static synchronized App getInstance(){
        return mInstance;
    }

    public ImageLoader getImageLoader (){
        getRequestQueue();

        if(mImageLoader == null){
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public List getUserData(){
        return userDataList;
    }

    public List getLikedUserData(){
        return likedUserList;
    }

    public RequestQueue getRequestQueue() {
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req){
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            return  true;
        }
        return false;
    }
}
