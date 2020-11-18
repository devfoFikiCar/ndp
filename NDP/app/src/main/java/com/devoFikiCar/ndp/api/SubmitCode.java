package com.devoFikiCar.ndp.api;

import android.util.Log;

import org.json.JSONObject;

import okhttp3.*;

public class SubmitCode {
    public static String requestToken(String code, int languageID) {
        String token = "";
        System.out.println(parseCode(code));

        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody requestBody = RequestBody.create("{\n" +
                    "\"language_id\":" + languageID + ",\n" +
                    "\"source_code\":\"" + parseCode(code) + "\"\n" +
                    "}", mediaType);
            Request request = new Request.Builder()
                    .url("https://judge0.p.rapidapi.com/submissions")
                    .post(requestBody)
                    .addHeader("accept", "application/json")
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
            //token = rd.split(":")[1].substring(1, rd.split(":").length - 2);
            JSONObject jsonObject = new JSONObject(rd);
            token = jsonObject.getString("token");
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("POST - API", ex.toString());
        }

        return token;
    }

    private static String parseCode(String code) {
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '"') {
                code = addChar(code, '\\', i);
                i += 2;
            }
        }
        return code;
    }

    private static String addChar(String str, char ch, int position) {
        StringBuilder sb = new StringBuilder(str);
        sb.insert(position, ch);
        return sb.toString();
    }
}
