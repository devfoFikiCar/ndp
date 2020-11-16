package com.devoFikiCar.ndp.api;

import android.util.Log;

import okhttp3.*;

public class RetrieveOutput {
    public static String getOutput(String token) {
        String output = "";

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://judge0.p.rapidapi.com/submissions/" + token)
                    .get()
                    .addHeader("x-rapidapi-key", "12bc36a072msh7fbc668fb4575d0p14dc53jsn8776abdff029")
                    .addHeader("x-rapidapi-host", "judge0.p.rapidapi.com")
                    .build();
            Response response = client.newCall(request).execute();
            String rd = response.body().string();
            Log.i("API - GET", rd);
            output = rd;
        } catch (Exception ex) {
            Log.e("API - GET", ex.getMessage());
        }

        return output;
    }
}
