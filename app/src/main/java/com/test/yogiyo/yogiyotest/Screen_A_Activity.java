package com.test.yogiyo.yogiyotest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
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

    private int arrayLength = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_screen_a);

        mListView = (ListView) findViewById(R.id.listView);

        aAdapter = new ScreenA_Adapter(this, App.getInstance().getUserData());
        mListView.setAdapter(aAdapter);

        mSearch = (EditText) findViewById(R.id.search);
        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){

                    String encoded_query ="";
                    try {
                        encoded_query = App.GITHUBUSER + URLEncoder.encode(String.valueOf(mSearch.getText()), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    CustomRequest jsonReq = new CustomRequest(Request.Method.GET, encoded_query,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {
                                        if(response.getInt("total_count") > 0){
                                            if(response.has("items")){
                                                JSONArray jsonArray = response.getJSONArray("items");
                                                arrayLength = jsonArray.length();
                                                if(arrayLength > 0){
                                                    for(int i = 0 ; i < jsonArray.length(); i++ ){
                                                        JSONObject itemObj = (JSONObject) jsonArray.get(i);
                                                        UserData userData = new UserData(itemObj);
                                                        App.getInstance().getUserData().add(userData);
                                                    }
                                                }
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    App.getInstance().addToRequestQueue(jsonReq);
                }

                return true;
            }
        });
    }
}
