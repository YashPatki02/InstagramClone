package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    public EditText etUsername;
    public EditText etPassword;
    public Button btnLogin, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ParseUser.getCurrentUser() != null) {
            goToMainActivity();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick Login Button");

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick SignUp button");

                ParseUser user = new ParseUser();

                user.setUsername(etUsername.getText().toString());
                user.setPassword(etPassword.getText().toString());

                // Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            //Navigate to the Main Activity if the user is signed in properly
                            goToMainActivity();
                            Toast.makeText(LoginActivity.this, "Signed Up!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Issue with Sign Up", Toast.LENGTH_SHORT).show();

                            Log.e(TAG, "Issue with Sign Up", e);
                            return;
                        }
                    }
                });
            }
        });
    }



            private void loginUser(String username, String password) {
                Log.i(TAG, "Attempting to Login User: " + username);

                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user == null) {
                            Toast.makeText(LoginActivity.this, "Issue with Login", Toast.LENGTH_SHORT).show();

                            Log.e(TAG, "Issue with Login", e);
                            return;
                        }
                        //Navigate to the Main Activity if the user is signed in properly
                        goToMainActivity();
                        Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            private void goToMainActivity() {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
    }
