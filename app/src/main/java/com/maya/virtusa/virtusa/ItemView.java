package com.maya.virtusa.virtusa;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

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
        final String contentpreview = extras.getString("contentpreview");
        String src = extras.getString("src");

        ImageView img = findViewById(R.id.video);

        tv1 = findViewById(R.id.viewContentName);
        tv2 = findViewById(R.id.viewauthor);
        tv3 = findViewById(R.id.viewfeed);

        tv1.setText(contentname);
        tv2.setText(contentauthor);

        if (src.equals("video"))
        {
            tv3.setVisibility(View.INVISIBLE);
            img.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.videoicon).into(img);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(contentpreview));
                    startActivity(i);
                }
            });

        }
        else
        {
            img.setVisibility(View.INVISIBLE);
            tv3.setText(contentpreview);

        }
    }

}
