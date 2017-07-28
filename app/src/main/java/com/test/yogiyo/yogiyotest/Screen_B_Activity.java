package com.test.yogiyo.yogiyotest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.test.yogiyo.yogiyotest.Adapter.ScreenB_Adapter;

public class Screen_B_Activity extends AppCompatActivity{

    private ScreenB_Adapter bAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_screen_b);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.actionBarTItleB);
        }

        mListView = (ListView) findViewById(R.id.listView);

        bAdapter = new ScreenB_Adapter(this, App.getInstance().getLikedUserData());
        mListView.setAdapter(bAdapter);
    }
}
