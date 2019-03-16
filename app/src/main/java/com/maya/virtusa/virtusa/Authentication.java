package com.maya.virtusa.virtusa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Authentication extends AppCompatActivity {


    private FirebaseAuth auth;
    private ProgressDialog dialoglogin;
    private ProgressDialog dialogsignup;
    private FirebaseDatabase database;
    private DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        auth = FirebaseAuth.getInstance();

        dialoglogin = new ProgressDialog(Authentication.this);
        dialoglogin.setMessage("Logging in...Please Wait!");
        dialoglogin.setCanceledOnTouchOutside(false);

        dialogsignup = new ProgressDialog(Authentication.this);
        dialogsignup.setMessage("Creating Account...Please Wait!");
        dialogsignup.setCanceledOnTouchOutside(false);

        final LinearLayout signInLay = findViewById(R.id.signinlayout);
        final LinearLayout signUpLay = findViewById(R.id.signuplayout);

        Button changeToSignUp = findViewById(R.id.changetosignup);
        Button changeToLogin = findViewById(R.id.changetologin);

        final EditText signInMail = findViewById(R.id.signinemail);
        final EditText signInPassword = findViewById(R.id.signinpassword);

        Button signInButton = findViewById(R.id.signin);


        final EditText signUpMail = findViewById(R.id.signupemail);
        final EditText signUpPassword = findViewById(R.id.signuppassword);
        final EditText signUpCnfPswd = findViewById(R.id.signupcnfpassword);
        final EditText userName = findViewById(R.id.signupname);


        Button signUpButton = findViewById(R.id.signup);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = signInMail.getText().toString();
                String psw = signInPassword.getText().toString();
                dialoglogin.show();

                if(mail.equals("") || mail.equals(""))
                {
                    Toast.makeText(Authentication.this, "All fields should be filled", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    signInUser(mail, psw);
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = signUpMail.getText().toString();
                String psw = signUpPassword.getText().toString();
                String cnf = signUpCnfPswd.getText().toString();
                String name = userName.getText().toString();


                if(!mail.equals("") && !psw.equals("") && cnf.equals(psw) && !name.equals(""))
                {
                    dialogsignup.show();
                    signUpUser(mail, psw, name);
                }
                else
                {
                    Toast.makeText(Authentication.this, "Fill details correctly", Toast.LENGTH_SHORT).show();
                }


            }
        });

        changeToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInLay.setVisibility(View.VISIBLE);
                signUpLay.setVisibility(View.INVISIBLE);
            }
        });


        changeToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpLay.setVisibility(View.VISIBLE);
                signInLay.setVisibility(View.INVISIBLE);
            }
        });






    }


    public void signInUser(String mail, String psw)
    {


        auth.signInWithEmailAndPassword(mail, psw)
                .addOnCompleteListener(Authentication.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            FirebaseUser user = auth.getCurrentUser();

                            if (user.isEmailVerified())
                            {
                                dialoglogin.dismiss();
                                Toast.makeText(Authentication.this, "User Login Successfull", Toast.LENGTH_SHORT).show();
                                Intent i =new Intent(Authentication.this, Dashboard.class);
                                startActivity(i);
                            }
                            else
                            {
                                dialoglogin.dismiss();
                                Toast.makeText(Authentication.this, "Verify Your Email Id", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            dialoglogin.dismiss();
                            Toast.makeText(Authentication.this, "No User Found", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }


    public void signUpUser(String mail, String psw, final String name)
    {

        auth.createUserWithEmailAndPassword(mail, psw)
                .addOnCompleteListener(Authentication.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            FirebaseUser user = auth.getCurrentUser();
                            user.sendEmailVerification()
                                    .addOnCompleteListener(Authentication.this, new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful())
                                            {
                                                dialogsignup.dismiss();
                                                config();
                                                Toast.makeText(Authentication.this, "Verification sent to registered mail id", Toast.LENGTH_SHORT).show();
                                            }

                                            else
                                            {
                                                dialogsignup.dismiss();
                                                Toast.makeText(Authentication.this, "Problem with sending verification mail", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name).build();
                            user.updateProfile(profile)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                               }
                                            else
                                            { }
                                        }
                                    });
                        }

                        else
                        {
                            dialogsignup.dismiss();
                            Toast.makeText(Authentication.this, "Failed adding user...Try again!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }


    public void config()
    {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("users");

        userEntry userDet = new userEntry("false");

        FirebaseUser user = auth.getCurrentUser();

        reference.child(user.getUid()).setValue(userDet);
    }
}
