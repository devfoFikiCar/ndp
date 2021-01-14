/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.api;

import android.util.Log;

import org.json.JSONObject;

import okhttp3.*;

public class SubmitCode {
    public static String requestToken(String code, int languageID, String stdin) {
        String token = "";

        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            JSONObject reqObject = new JSONObject();
            reqObject.put("language_id", languageID);
            reqObject.put("source_code", code);
            reqObject.put("stdin", stdin);
            reqObject.put("redirect_stderr_to_stdout", "true");
            RequestBody requestBody = RequestBody.create(reqObject.toString(), mediaType);
            Request request = new Request.Builder()
                    .url("https://judge0-ce.p.rapidapi.com/submissions")
                    .post(requestBody)
                    .addHeader("accept", "application/json")
                    .addHeader("x-rapidapi-key", "12bc36a072msh7fbc668fb4575d0p14dc53jsn8776abdff029")
                    .addHeader("x-rapidapi-host", "judge0-ce.p.rapidapi.com")
                    .addHeader("wait", "true")
                    .addHeader("redirect_stderr_to_stdout", "true")
                    .build();
            Response response = client.newCall(request).execute();
            String rd = response.body().string();
            Log.i("POST - API", rd);
            System.out.println("rd" + " " + rd);
            JSONObject jsonObject = new JSONObject(rd);
            token = jsonObject.getString("token");
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("POST - API", ex.toString());
        }

        return token;
    }
}
