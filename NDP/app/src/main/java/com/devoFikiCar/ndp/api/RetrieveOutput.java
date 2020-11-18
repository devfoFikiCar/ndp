package com.devoFikiCar.ndp.api;

import android.os.SystemClock;
import android.util.Log;

import org.json.JSONObject;

import okhttp3.*;

public class RetrieveOutput {
    public static String getOutput(String token) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://judge0.p.rapidapi.com/submissions/" + token)
                    .get()
                    .addHeader("x-rapidapi-key", "12bc36a072msh7fbc668fb4575d0p14dc53jsn8776abdff029")
                    .addHeader("x-rapidapi-host", "judge0.p.rapidapi.com")
                    .build();
            int count = 0;
            String status = "";
            JSONObject jsonObject;
            do {
                Response response = client.newCall(request).execute();
                String rd = response.body().string();
                Log.i("API - GET", rd);
                jsonObject = new JSONObject(rd);
                JSONObject jsonStatus = new JSONObject(jsonObject.getString("status"));
                status = jsonStatus.getString("description");
                System.out.println(status);
                if (count != 0) {
                    SystemClock.sleep(1000);
                }
                count++;
            } while (status.equals("In Queue"));

            if (!jsonObject.getString("stdout").equals("null")) {
                return jsonObject.getString("stdout");
            } else {
                return jsonObject.getString("stderr");
            }

        } catch (Exception ex) {
            Log.e("API - GET", ex.getMessage());
        }

        return "Try again later.";
    }
}
