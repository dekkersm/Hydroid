package com.example.hydroid;
import static com.example.hydroid.Constants.SERVER_IP;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HydroDataService {
    public static final String BASE_URL = "http://" + SERVER_IP + ":5000/";

    public static final String PH_URL = BASE_URL+ "ph/";
    public static final String TDS_URL = BASE_URL + "tds/";
    public static final String WATER_URL = BASE_URL + "water/";
    public static final String ENV_URL = BASE_URL + "env/";
    public static final String CONFIG_URL = BASE_URL + "config/";
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
                response -> {
                    try {
                        String pHValue = response.getString("value");
                        volleyResponseListener.onResp(pHValue);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> volleyResponseListener.onError("didn't work"));

        RequestSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void getCurrTDS(VolleyResponseListener volleyResponseListener){
        String url = TDS_URL + "current";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String tdsValue = response.getString("value");
                        volleyResponseListener.onResp(tdsValue);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> volleyResponseListener.onError("didn't work"));

        RequestSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void getCurrWater(VolleyResponseListener volleyResponseListener){
        String url = WATER_URL + "current";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String tempValue = response.getString("value");
                        volleyResponseListener.onResp(tempValue);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> volleyResponseListener.onError("didn't work"));

        RequestSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public interface GetCurrConfigResp {
        void onError(String message);
        void onResp(JSONArray resp);
    }

    public void getCurrConfig(GetCurrConfigResp getCurrConfigResp){
        String url = CONFIG_URL + "current";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray tempValue = response.getJSONArray("confData");
                        getCurrConfigResp.onResp(tempValue);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> getCurrConfigResp.onError("didn't work"));

        RequestSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void setCurrConfig(String type, float value){
        String url = CONFIG_URL + type + "?value=" + value +"&pumpTime=" + 1 + "&recheckTimeout=" + 2;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, url, null,
                response -> {

                }, error -> {

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
                response -> {
                    try {
                        EnvironmentData environmentData = new EnvironmentData();
                        environmentData.setTemperature(response.getDouble("tempValue"));
                        environmentData.setLight_intensity(response.getInt("lightValue"));
                        environmentData.setHumidity(response.getInt("humidityValue"));
                        environmentData.setCo2(response.getInt("co2Value"));
                        environmentData.setBaro(response.getInt("baroValue"));

                        getEnvResponse.onResp(environmentData);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> getEnvResponse.onError("didn't work"));

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
    public interface getWaterHistoryResponse {
        void onError(String message);
        void onResp(List<WaterData> waterData);
    }
    public interface getEnvHistoryResponse {
        void onError(String message);
        void onResp(List<EnvironmentData> environmentDataList);
    }

    public void getPhHistory(getPhHistoryResponse getPhHistoryResponse, long startDate, long endDate){
        Date startdate = new Date(startDate);
        Date enddate = new Date(endDate);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        String formattedStartDate = dateFormat.format(startdate);
        String formattedEndDate = dateFormat.format(enddate);
        String url = PH_URL + "history?from="+formattedStartDate+"&to="+formattedEndDate;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    List<PhData> phDataArrayList = new ArrayList<>();
                    try {
                        JSONArray phDataList = response.getJSONArray("phData");

                        for (int i = 0; i < phDataList.length(); i++) {
                            try {
                                JSONObject ph = phDataList.getJSONObject(i);

                                PhData phData = new PhData();
                                phData.setValue(ph.getLong("value"));
                                phData.setDate(Instant.parse(ph.getString("createdAt")).toEpochMilli());
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
                }, error -> getPhHistoryResponse.onError("didn't work"));

        RequestSingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getTdsHistory(getTdsHistoryResponse getTdsHistoryResponse, long startDate, long endDate){
        Date startdate = new Date(startDate);
        Date enddate = new Date(endDate);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        String formattedStartDate = dateFormat.format(startdate);
        String formattedEndDate = dateFormat.format(enddate);
        String url = TDS_URL + "history?from="+formattedStartDate+"&to="+formattedEndDate;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    List<TdsData> tdsDataList = new ArrayList<>();
                    try {
                        JSONArray tdsDataArray = response.getJSONArray("tdsData");

                        for (int i = 0; i < tdsDataArray.length(); i++) {
                            try {
                                JSONObject tds = tdsDataArray.getJSONObject(i);

                                TdsData tdsData = new TdsData();
                                tdsData.setValue(tds.getInt("value"));
                                tdsData.setDate(Instant.parse(tds.getString("createdAt")).toEpochMilli());
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
                }, error -> getTdsHistoryResponse.onError("didn't work"));

        RequestSingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getWaterHistory(getWaterHistoryResponse getWaterHistoryResponse, long startDate, long endDate){
        Date startdate = new Date(startDate);
        Date enddate = new Date(endDate);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        String formattedStartDate = dateFormat.format(startdate);
        String formattedEndDate = dateFormat.format(enddate);
        String url = WATER_URL + "history?from="+formattedStartDate+"&to="+formattedEndDate;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    List<WaterData> waterDataList = new ArrayList<>();
                    try {
                        JSONArray waterDataArray = response.getJSONArray("waterData");

                        for (int i = 0; i < waterDataArray.length(); i++) {
                            try {
                                JSONObject water = waterDataArray.getJSONObject(i);

                                WaterData waterData = new WaterData();
                                waterData.setTempValue(water.getDouble("value"));
                                waterData.setDate(Instant.parse(water.getString("createdAt")).toEpochMilli());
                                waterData.setPumpOn(water.getBoolean("pumpOn"));

                                waterDataList.add(waterData);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        getWaterHistoryResponse.onResp(waterDataList);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> getWaterHistoryResponse.onError("didn't work"));

        RequestSingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getEnvHistory(getEnvHistoryResponse getEnvHistoryResponse, long startDate, long endDate){
        Date startdate = new Date(startDate);
        Date enddate = new Date(endDate);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        String formattedStartDate = dateFormat.format(startdate);
        String formattedEndDate = dateFormat.format(enddate);
        String url = ENV_URL + "history?from="+formattedStartDate+"&to="+formattedEndDate;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    List<EnvironmentData> environmentDataList = new ArrayList<>();
                    try {
                        JSONArray envData = response.getJSONArray("envData");

                        for (int i = 0; i < envData.length(); i++) {
                            try {
                                JSONObject env = envData.getJSONObject(i);

                                EnvironmentData environmentData = new EnvironmentData();
                                environmentData.setTemperature(env.getDouble("tempValue"));
                                environmentData.setLight_intensity(env.getInt("lightValue"));
                                environmentData.setHumidity(env.getInt("humidityValue"));
                                environmentData.setCo2(env.getInt("co2Value"));
                                environmentData.setBaro(env.getInt("baroValue"));
                                environmentData.setDate(Instant.parse(env.getString("createdAt")).toEpochMilli());

                                environmentDataList.add(environmentData);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        getEnvHistoryResponse.onResp(environmentDataList);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> getEnvHistoryResponse.onError("didn't work"));

        RequestSingleton.getInstance(context).addToRequestQueue(request);
    }
}
