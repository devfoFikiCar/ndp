package com.devoFikiCar.ndp.api;

import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class SubmitCode {
    public static String requestToken(String code, int languageID) {
        final String[] token = {""};

        try {
            System.out.println("here1");
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody requestBody = RequestBody.create(mediaType, "{\n" +
                    "\"language_id\":" + languageID + ",\n" +
                    "\"source_code\":\"" + parseCode(code) + "\",\n" +
                    "}");
            System.out.println("here2");
            Request request = new Request.Builder()
                    .url("https://judge0.p.rapidapi.com/submissions")
                    .post(requestBody)
                    .addHeader("content-type", "application/json")
                    .addHeader("x-rapidapi-key", "12bc36a072msh7fbc668fb4575d0p14dc53jsn8776abdff029")
                    .addHeader("x-rapidapi-host", "judge0.p.rapidapi.com")
                    .build();
            Response response = client.newCall(request).execute();
            System.out.println("here3");
            String rd = response.body().string();
            Log.i("POST - API", rd);
            token[0] = rd.split("\"")[3];
            Log.i("POST - API", token[0]);
        } catch (Exception ex) {
            Log.e("POST - API", ex.toString());
        }

        return token[0];
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
