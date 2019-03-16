package com.maya.virtusa.virtusa;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class Dashboard extends AppCompatActivity {

    private FirebaseAuth auth;

    boolean doubleBackToExitPressedOnce = false;
    private ActionBar toolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        Fragment fragment;
        fragment = new Content();
        loadFragent(fragment);


        toolBar = getSupportActionBar();
        toolBar.setTitle("DashBoard");

        auth = FirebaseAuth.getInstance();



        BottomNavigationView nav = (BottomNavigationView)findViewById(R.id.bottomnavbar);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment;
                switch (item.getItemId())
                {
                    case R.id.contentnav:
                        toolBar.setTitle("Content Feed");
                        fragment = new Content();
                        loadFragent(fragment);
                        return true;


                    case R.id.profilenav:
                        toolBar.setTitle("Profile");
                        fragment = new Profile();
                        loadFragent(fragment);
                        return true;
                }
                return false;
            }
        });


    }

    public void loadFragent(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {


        if (doubleBackToExitPressedOnce) {
            finishAffinity();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbarmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                Intent i = new Intent(Dashboard.this, Authentication.class);
                auth.signOut();
                finishAffinity();
                startActivity(i);
                Toast.makeText(Dashboard.this, "Logged off Successfully", Toast.LENGTH_SHORT).show();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }




}
