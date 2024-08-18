package com.example.hydroid;

import android.content.Context;
import android.widget.Toast;

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

    public static final String SERVER_IP = "192.168.237.103";
    public static final String PH_URL = "http://" + SERVER_IP + ":3000/ph/";
    public static final String TDS_URL = "http://" + SERVER_IP + ":3000/tds/";
    public static final String WATER_URL = "http://" + SERVER_IP + ":3000/water/";
    public static final String ENV_URL = "http://" + SERVER_IP + ":3000/env/";
    Context context;

    public HydroDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResp(String resp);
    }

    public void getCurrPH(VolleyResponseListener volleyResponseListener){
        String url = PH_URL + "current";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String pHValue = response.getString("value");
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

    public void getCurrTDS(VolleyResponseListener volleyResponseListener){
        String url = TDS_URL + "current";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String tdsValue = response.getString("value");
                            volleyResponseListener.onResp(tdsValue);
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

    public void getCurrWater(VolleyResponseListener volleyResponseListener){
        String url = WATER_URL + "current";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String tempValue = response.getString("tempValue");
                            volleyResponseListener.onResp(tempValue);
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

    public interface getEnvResponse {
        void onError(String message);
        void onResp(EnvironmentData environmentData);
    }

    public void getCurrEnv(getEnvResponse getEnvResponse){
        String url = ENV_URL + "current";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            EnvironmentData environmentData = new EnvironmentData();
                            environmentData.setTemperature(response.getLong("tempValue"));
                            environmentData.setLight_intensity(response.getLong("lightValue"));
                            environmentData.setHumidity(response.getLong("humidityValue"));
                            environmentData.setCo2(response.getLong("co2Value"));
                            environmentData.setBaro(response.getLong("baroValue"));

                            getEnvResponse.onResp(environmentData);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getEnvResponse.onError("didn't work");
            }
        });

        RequestSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public interface getEnvHistoryResponse {
        void onError(String message);
        void onResp(List<EnvironmentData> environmentDataList);
    }

    public void getEnvHistory(getEnvHistoryResponse getEnvHistoryResponse){
        String url = ENV_URL + "history";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<EnvironmentData> environmentDataList = new ArrayList<>();
                        try {
                            JSONArray envData = response.getJSONArray("envData");

                            for (int i = 0; i < envData.length(); i++) {
                                try {
                                    JSONObject env = envData.getJSONObject(i);

                                    EnvironmentData environmentData = new EnvironmentData();
                                    environmentData.setTemperature(env.getLong("tempValue"));
                                    environmentData.setLight_intensity(env.getLong("lightValue"));
                                    environmentData.setHumidity(env.getLong("humidityValue"));
                                    environmentData.setCo2(env.getLong("co2Value"));
                                    environmentData.setBaro(env.getLong("baroValue"));

                                    environmentDataList.add(environmentData);

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            getEnvHistoryResponse.onResp(environmentDataList);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getEnvHistoryResponse.onError("didn't work");
            }
        });

        RequestSingleton.getInstance(context).addToRequestQueue(request);
    }
}
