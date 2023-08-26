package com.hoanh.androidapp.assignment8;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.huy.androidcourseapp.R;

public class Assignment8_SharedPreferences extends AppCompatActivity {

    EditText textUsername, textPassword;
    TextView textWelcome;
    Button btnLogin, btnSignup, btnLogout;
    LinearLayout layoutLogin, layoutWelcome;
    SharedPreferences prefs;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice8_shared_preferences);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.getIntent().getStringExtra("practiceName") + "");

        prefs = getSharedPreferences("practice8_user_login", Context.MODE_PRIVATE);

        // layout login
        layoutLogin = findViewById(R.id.layoutLogin);
        textUsername = findViewById(R.id.textUsername);
        textPassword = findViewById(R.id.textPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignup();
            }
        });

        // layout welcome
        layoutWelcome = findViewById(R.id.layoutWelcome);
        textWelcome = findViewById(R.id.textWelcome);
        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleToggleWelcome(false);
            }
        });
        handleToggleWelcome(false);
    }

    private void handleLogin() {
        String username = textUsername.getText().toString();
        String password = textPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }
        String storedPass = prefs.getString(username, null);
        System.out.println("storedPass " + storedPass);
        if (storedPass == null || !storedPass.equals(password)) {
            System.out.println("storedPass: " + storedPass + " | login pass: " + password);
            Toast.makeText(this, "Username or password is incorrect!", Toast.LENGTH_SHORT).show();
        } else {
            currentUser = username;
            System.out.println("Login success: " + username + "/" + storedPass);
            handleToggleWelcome(true);
        }
    }

    private void handleSignup() {
        String username = textUsername.getText().toString();
        String password = textPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(username, password);
        editor.apply();
        Toast.makeText(this, "Signup success!", Toast.LENGTH_SHORT).show();
        System.out.println("Signup: " + username + "/" + password);
        currentUser = username;
        handleToggleWelcome(true);
    }

    private void handleToggleWelcome(boolean isLoggedIn) {
        if (isLoggedIn) {
            layoutLogin.setVisibility(View.GONE);
            layoutWelcome.setVisibility(View.VISIBLE);
            textWelcome.setText(currentUser);
        } else {
            layoutLogin.setVisibility(View.VISIBLE);
            layoutWelcome.setVisibility(View.GONE);
            textWelcome.setText(null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}