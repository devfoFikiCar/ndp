/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.api;

import android.os.SystemClock;
import android.util.Log;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RetrieveOutput {
    private static final int LEVEL_1 = 1000;
    private static final int LEVEL_2 = 2000;
    private static final int LEVEL_3 = 3000;
    private static final int LEVEL_4 = 4000;
    private static final int LEVEL_5 = 5000;

    public static String getOutput(String token) {
        boolean flag = true;
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://judge0-ce.p.rapidapi.com/submissions/" + token)
                    .get()
                    .addHeader("x-rapidapi-key", "12bc36a072msh7fbc668fb4575d0p14dc53jsn8776abdff029")
                    .addHeader("x-rapidapi-host", "judge0-ce.p.rapidapi.com")
                    .build();
            int count = 0;
            String status;
            JSONObject jsonObject;
            do {
                Response response = client.newCall(request).execute();
                String rd = response.body().string();
                Log.i("API - GET", rd);
                jsonObject = new JSONObject(rd);
                JSONObject jsonStatus = new JSONObject(jsonObject.getString("status"));
                status = jsonStatus.getString("description");
                System.out.println(status);
                switch (count) {
                    case 0:
                        SystemClock.sleep(LEVEL_1);
                        break;
                    case 1:
                    case 2:
                        SystemClock.sleep(LEVEL_2);
                        break;
                    case 3:
                        SystemClock.sleep(LEVEL_3);
                        break;
                    case 4:
                        SystemClock.sleep(LEVEL_4);
                        break;
                    case 5:
                        SystemClock.sleep(LEVEL_5);
                        break;
                    default:
                        return "Network error";
                }
                count++;
                if (status.equals("Accepted") || status.equals("Compilation Error") || status.equals("Runtime Error") || status.equals("Runtime Error (NZEC)")) {
                    flag = false;
                }
            } while (flag);

            String out = jsonObject.getString("stdout");
            if (out.equals("null") && !jsonObject.getString("compile_output").equals("null")) {
                out = jsonObject.getString("compile_output");
            }

            return out;
        } catch (Exception ex) {
            Log.e("API - GET", ex.getMessage());
        }

        return "Try again later.";
    }
}
