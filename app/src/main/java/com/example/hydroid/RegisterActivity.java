package com.example.hydroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText name, username, email, password;
    Button signup_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.full_name_input);
        username = findViewById(R.id.username_input);
        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        signup_btn = findViewById(R.id.signup_btn);

        AuthService authService = new AuthService(getApplicationContext());

        signup_btn.setOnClickListener(v -> authService.registerUser(new AuthService.AuthResponseListener() {
                                                                        @Override
                                                                        public void onError(String message) {
                                                                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                                                                        }

                                                                        @Override
                                                                        public void onResp(String resp) {
                                                                            Toast.makeText(RegisterActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                                                                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                                                        }
                                                                    },
                name.getText().toString(),
                username.getText().toString(),
                email.getText().toString(),
                password.getText().toString()));
    }
}