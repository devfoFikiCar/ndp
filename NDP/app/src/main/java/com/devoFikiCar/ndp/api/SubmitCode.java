package com.devoFikiCar.ndp.api;

import android.util.Log;

import okhttp3.*;

public class SubmitCode {
    public static String requestToken(String code, int languageID) {
        final String token = "";

        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody requestBody = RequestBody.create("{\n" +
                    "\"language_id\":" + languageID + ",\n" +
                    "\"source_code\":\"" + parseCode(code) + "\",\n" +
                    "}", mediaType);
            Request request = new Request.Builder()
                    .url("https://judge0.p.rapidapi.com/submissions")
                    .post(requestBody)
                    .addHeader("content-type", "application/json")
                    .addHeader("x-rapidapi-key", "12bc36a072msh7fbc668fb4575d0p14dc53jsn8776abdff029")
                    .addHeader("x-rapidapi-host", "judge0.p.rapidapi.com")
                    .addHeader("wait", "true")
                    .build();
            Response response = client.newCall(request).execute();
            String rd = response.body().string();
            Log.i("POST - API", rd);
            //token[0] = rd.split("\"")[3];
            //Log.i("POST - API", token[0]);
            System.out.println("rd" + " " + rd);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("POST - API", ex.toString());
        }

        return token;
    }

    private static String parseCode(String code) {
        String parsed = "";
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '\\' || code.charAt(i) == '"') {
                code = addChar(code, '\\', i - 1);
                i++;
            }
        }
        return parsed;
    }

    private static String addChar(String str, char ch, int position) {
        StringBuilder sb = new StringBuilder(str);
        sb.insert(position, ch);
        return sb.toString();
    }
}
