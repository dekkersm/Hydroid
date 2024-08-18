package com.example.hydroid;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HydroDataService {

    public static final String PH_URL = "https://petstore.swagger.io/v2/store/inventory";
    public static final String TDS_URL = "https://petstore.swagger.io/v2/store/inventory";
    public static final String PH_HISTORICAL_URL = "https://petstore.swagger.io/v2/store/inventory";
    public static final String TDS_HISTORICAL_URL = "https://petstore.swagger.io/v2/store/inventory";
    public static final String ENV_HISTORICAL_URL = "https://petstore.swagger.io/v2/pet/findByStatus?status=available";
    Context context;

    public HydroDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResp(String resp);
    }

    public void getCurrPH(VolleyResponseListener volleyResponseListener){
        String url = PH_URL;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String pHValue = response.getString("sold");
                            volleyResponseListener.onResp(pHValue);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError("didn't work");
            }
        });

        RequestSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public interface GetEnvDataResponse {
        void onError(String message);
        void onResp(List<EnvironmentData> environmentDataList);
    }

    public void getEnvData(GetEnvDataResponse getEnvDataResponse){
        String url = ENV_HISTORICAL_URL;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<EnvironmentData> environmentDataList = new ArrayList<>();
                        int s  = response.length();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject env = response.getJSONObject(i);

                                EnvironmentData environmentData = new EnvironmentData();
                                environmentData.setTemperature(env.getLong("id"));
                                environmentData.setLight_intensity(env.getLong("id"));
                                environmentData.setHumidity(env.getInt("id"));

                                environmentDataList.add(environmentData);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        getEnvDataResponse.onResp(environmentDataList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getEnvDataResponse.onError("didn't work");
            }
        });

        RequestSingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getCurrTDS(VolleyResponseListener volleyResponseListener){
        String url = TDS_URL;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String pHValue = response.getString("sold");
                            volleyResponseListener.onResp(pHValue);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError("didn't work");
            }
        });

        RequestSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
