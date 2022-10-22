package com.etaman.etaman;

import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiRepository {
    private static volatile ApiRepository INSTANCE = null;

    private OkHttpClient client = new OkHttpClient();
    private MediaType mediaType = MediaType.parse("application/json");
    private String endpointUrl = "http://192.168.0.96:8800";

    private JSONParser parser = new JSONParser();
    public JSONObject userInfo;
    public JSONArray requestsData;
    public JSONArray eventsData;

    private ApiRepository() {}
    public static ApiRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (ApiRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ApiRepository();
                }
            }
        }
        return INSTANCE;
    }

    public void registerUserAPI(String username, String email, String password) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", username);
        jsonObject.put("username", username);
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        jsonObject.put("location", "KL");

        RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder()
                .url(endpointUrl + "/api/auth/register")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("Register", response.body().string());
            }
        });
    }

    public void loginUserAPI(String username, String password) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("username", username);
        jsonObject.put("password", password);

        RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder()
                .url(endpointUrl + "/api/auth/login")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.code() == 200) {
                        userInfo = (JSONObject) parser.parse(response.body().string());
                        Log.d("Login", userInfo.toString());
                    } else {
                        userInfo = null;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getAllRequestsAPI() {
        Request request = new Request.Builder()
                .url(endpointUrl + "/api/posts/requests/")
                .method("GET", null)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.code() == 200) {
                        requestsData = (JSONArray) parser.parse(response.body().string());
                        Log.d("Requests", requestsData.toString());
                    } else {
                        requestsData = null;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getAllEventsAPI() {
        Request request = new Request.Builder()
                .url(endpointUrl + "/api/posts/events/")
                .method("GET", null)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.code() == 200) {
                        eventsData = (JSONArray) parser.parse(response.body().string());
                        Log.d("Events", eventsData.toString());
                    } else {
                        eventsData = null;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
