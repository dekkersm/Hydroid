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

    public static final String SERVER_IP = "192.168.1.127";
    public static final String PH_URL = "http://" + SERVER_IP + ":3000/ph/";
    public static final String TDS_URL = "http://" + SERVER_IP + ":3000/tds/";
    public static final String WATER_URL = "http://" + SERVER_IP + ":3000/water/";
    public static final String ENV_URL = "http://" + SERVER_IP + ":3000/env/";
    public static final String CONFIG_URL = "http://" + SERVER_IP + ":3000/config/";
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

    public interface GetCurrConfigResp {
        void onError(String message);
        void onResp(JSONArray resp);
    }

    public void getCurrConfig(GetCurrConfigResp getCurrConfigResp){
        String url = CONFIG_URL + "current";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray tempValue = response.getJSONArray("confData");
                            getCurrConfigResp.onResp(tempValue);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getCurrConfigResp.onError("didn't work");
            }
        });

        RequestSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void setCurrConfig(String type, float value){
        String url = CONFIG_URL + type + "?value=" + value +"&pumpTime=" + 1 + "&recheckTimeout=" + 2;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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

    public interface getPhHistoryResponse {
        void onError(String message);
        void onResp(List<PhData> PhData);
    }
    public interface getTdsHistoryResponse {
        void onError(String message);
        void onResp(List<TdsData> tdsData);
    }
    public interface getEnvHistoryResponse {
        void onError(String message);
        void onResp(List<EnvironmentData> environmentDataList);
    }

    public void getPhHistory(getPhHistoryResponse getPhHistoryResponse){
        String url = PH_URL + "history";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<PhData> phDataArrayList = new ArrayList<>();
                        try {
                            JSONArray phDataList = response.getJSONArray("phData");

                            for (int i = 0; i < phDataList.length(); i++) {
                                try {
                                    JSONObject ph = phDataList.getJSONObject(i);

                                    PhData phData = new PhData();
                                    phData.setValue(ph.getLong("value"));
                                    phData.setDate(ph.getLong("date"));
                                    phData.setPhDownOn(ph.getBoolean("phUpOn"));
                                    phData.setPhUpOn(ph.getBoolean("phDownOn"));

                                    phDataArrayList.add(phData);

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            getPhHistoryResponse.onResp(phDataArrayList);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getPhHistoryResponse.onError("didn't work");
            }
        });

        RequestSingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getTdsHistory(getTdsHistoryResponse getTdsHistoryResponse, long startDate, long endDate){
        String url = TDS_URL + "history?from="+startDate+"&to="+endDate;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<TdsData> tdsDataList = new ArrayList<>();
                        try {
                            JSONArray tdsDataArray = response.getJSONArray("tdsData");

                            for (int i = 0; i < tdsDataArray.length(); i++) {
                                try {
                                    JSONObject tds = tdsDataArray.getJSONObject(i);

                                    TdsData tdsData = new TdsData();
                                    tdsData.setValue(tds.getLong("value"));
                                    tdsData.setDate(tds.getLong("date"));
                                    tdsData.setPumpOn(tds.getBoolean("pumpOn"));

                                    tdsDataList.add(tdsData);

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            getTdsHistoryResponse.onResp(tdsDataList);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getTdsHistoryResponse.onError("didn't work");
            }
        });

        RequestSingleton.getInstance(context).addToRequestQueue(request);
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
