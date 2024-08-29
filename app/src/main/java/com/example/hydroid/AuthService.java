package com.example.hydroid;

import static com.example.hydroid.HydroDataService.BASE_URL;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class AuthService {
    public static final String LOGIN_URL = BASE_URL + "auth/login";
    public static final String SIGNUP_URL = BASE_URL + "auth/register";
    Context context;

    public AuthService(Context context) {
        this.context = context;
    }

    public interface AuthResponseListener {
        void onError(String message);
        void onResp(String resp);
    }

    public void loginUser(AuthResponseListener authResponseListener, String username, String password){

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        final String mRequestBody = jsonBody.toString();

        // making a string request on below line.
        StringRequest request = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                authResponseListener.onResp(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                authResponseListener.onError(error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return mRequestBody.getBytes(StandardCharsets.UTF_8);
            }
        };

        RequestSingleton.getInstance(context).addToRequestQueue(request);
    }

    public void registerUser(AuthResponseListener authResponseListener, String name, String username, String email, String password){

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", name);
            jsonBody.put("username", username);
            jsonBody.put("email", email);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        final String mRequestBody = jsonBody.toString();

        // making a string request on below line.
        StringRequest request = new StringRequest(Request.Method.POST, SIGNUP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                authResponseListener.onResp(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                authResponseListener.onError(error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return mRequestBody.getBytes(StandardCharsets.UTF_8);
            }
        };

        RequestSingleton.getInstance(context).addToRequestQueue(request);
    }
}
