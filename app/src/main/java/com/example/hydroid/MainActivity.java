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

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("user") &&
                password.getText().toString().equals("123")){
                    Toast.makeText(MainActivity.this, "Login Successful ", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    i.putExtra("username", username.getText().toString());
                    startActivity(i);
                }
                else {
                    Toast.makeText(MainActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}