package com.maya.virtusa.virtusa;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user!=null)
                {
                    Intent i = new Intent(getApplicationContext(),Dashboard.class);
                    startActivity(i);
                }
                else
                {
                    Intent i = new Intent(getApplicationContext(),Authentication.class);
                    startActivity(i);

                }
            }
        },3000);
    }

}
