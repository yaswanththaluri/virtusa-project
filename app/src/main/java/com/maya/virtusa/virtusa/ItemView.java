package com.maya.virtusa.virtusa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ItemView extends AppCompatActivity {

    private TextView tv1,tv2,tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String contentname = extras.getString("contentname");
        String contentauthor = extras.getString("contentauthor");
        String contentpreview = extras.getString("contentpreview");

        tv1 = findViewById(R.id.viewContentName);
        tv2 = findViewById(R.id.viewauthor);
        tv3 = findViewById(R.id.viewfeed);

        tv1.setText(contentname);
        tv2.setText(contentauthor);
        tv3.setText(contentpreview);
    }

}
