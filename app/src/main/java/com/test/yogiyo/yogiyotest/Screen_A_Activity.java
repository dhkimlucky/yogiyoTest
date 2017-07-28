package com.test.yogiyo.yogiyotest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.test.yogiyo.yogiyotest.Adapter.ScreenA_Adapter;
import com.test.yogiyo.yogiyotest.Model.UserData;
import com.test.yogiyo.yogiyotest.Util.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Screen_A_Activity extends AppCompatActivity{

    private EditText mSearch;
    private ListView mListView;
    private ScreenA_Adapter aAdapter;
    private TextView mMessage;
    private int arrayLength = 0;
    private Boolean addingMore = false, viewMore = false;
    private int page = 1;
    private long totalPage = 0;
    final int PERPAGE = 20;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_screen_a);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.actionBarTItleA);
        }

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.container_items);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        mListView = (ListView) findViewById(R.id.listView);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastScreen  = firstVisibleItem + visibleItemCount;
                totalPage = App.getInstance().totalCount / PERPAGE + 1;
                if((lastScreen == totalItemCount) && (viewMore) && (!swipeRefreshLayout.isRefreshing()) && page <= totalPage){
                    if(App.getInstance().isConnected()){
                        addingMore = true;
                        getItems();
                    }
                }
            }
        });

        mMessage = (TextView) findViewById(R.id.message);

        aAdapter = new ScreenA_Adapter(this, App.getInstance().getUserData());
        mListView.setAdapter(aAdapter);

        mSearch = (EditText) findViewById(R.id.search);
        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){

                    // 새로운 검색 시 변수 초기화
                    App.getInstance().getUserData().clear();
                    App.getInstance().getLikedUserData().clear();
                    App.getInstance().totalCount = 0;

                    getItems();
                }
                return true;
            }
        });
    }

    private void loadingComplete() {

        viewMore = PERPAGE == arrayLength;

        aAdapter.notifyDataSetChanged();

        page++;

        addingMore = false;

        swipeRefreshLayout.setRefreshing(false);

        if(aAdapter.getCount() == 0){
            showMessage(getString(R.string.noListData));
        }
    }

    private void showMessage(String message){
        mMessage.setText(message);
        mMessage.setVisibility(View.VISIBLE);
    }

    public void getItems() {

        String encoded_query ="";
        try {
            encoded_query = URLEncoder.encode(String.valueOf(mSearch.getText()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(!encoded_query.equals("")){
            encoded_query = App.GITHUBUSER + encoded_query + "&page="+page+"&per_page=20";
        }

        swipeRefreshLayout.setRefreshing(true);

        CustomRequest jsonReq = new CustomRequest(Request.Method.GET, encoded_query,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if(response.has("total_count")){
                                App.getInstance().totalCount = response.getLong("total_count");
                                if(response.getInt("total_count") > 0){
                                    if(response.has("items")){
                                        JSONArray jsonArray = response.getJSONArray("items");
                                        arrayLength = jsonArray.length();
                                        if(arrayLength > 0){
                                            for(int i = 0 ; i < arrayLength; i++ ){
                                                JSONObject itemObj = (JSONObject) jsonArray.get(i);
                                                UserData userData = new UserData(itemObj);
                                                App.getInstance().getUserData().add(userData);
                                            }
                                        }
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            loadingComplete();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        App.getInstance().addToRequestQueue(jsonReq);
    }
}
