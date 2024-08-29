package com.example.hydroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button login_btn, register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username_input);
        password = findViewById(R.id.password_input);
        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.register_btn);

        AuthService authService = new AuthService(getApplicationContext());

        login_btn.setOnClickListener(v -> authService.loginUser(new AuthService.AuthResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResp(String resp) {
                Intent i = new Intent(MainActivity.this, HomePage.class);
                startActivity(i);
            }
        }, username.getText().toString(), password.getText().toString()));

        register_btn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RegisterActivity.class)));
    }
}