package com.example.hydroid;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    public static final String SERVER_IP = "192.168.1.127";
    public static final String BASE_URL = "http://" + SERVER_IP + ":5000/";
    public static final String LOGIN_URL = BASE_URL + "auth/login";
    Context context;

    public AuthService(Context context) {
        this.context = context;
    }

    public interface LoginResponseListener {
        void onError(String message);
        void onResp(String resp);
    }

    public void loginUser(LoginResponseListener loginResponseListener, String username, String password){

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
                //if (resp.equals(""))
                loginResponseListener.onResp(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loginResponseListener.onError(error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    throw new RuntimeException(uee);
                }
            }
        };

        RequestSingleton.getInstance(context).addToRequestQueue(request);
    }
}
