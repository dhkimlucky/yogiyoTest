package com.test.yogiyo.yogiyotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnScrA;
    private Button btnScrB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScrA = (Button)findViewById(R.id.btn_a);
        btnScrB = (Button)findViewById(R.id.btn_b);

        btnScrA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Screen_A_Activity.class);
                startActivity(intent);
            }
        });

        btnScrB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Screen_B_Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        App.getInstance().getUserData().clear();
        App.getInstance().getLikedUserData().clear();
        App.getInstance().totalCount = 0;
    }
}
