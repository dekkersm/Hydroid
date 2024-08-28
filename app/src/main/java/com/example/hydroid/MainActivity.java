package com.example.hydroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username_input);
        password = findViewById(R.id.password_input);
        login_btn = findViewById(R.id.login_btn);

        AuthService authService = new AuthService(getApplicationContext());

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authService.loginUser(new AuthService.LoginResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResp(String resp) {
                        Intent i = new Intent(MainActivity.this, HomePage.class);
                        //i.putExtra("username", username.getText().toString());
                        startActivity(i);
                    }
                }, username.getText().toString(), password.getText().toString());
            }
        });
    }
}