package com.bnmoboxd.clients;

import com.bnmoboxd.core.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
class BaseResponse<T> {
    private String message;
    private T data;
}

public class RestApi implements AutoCloseable {
    private final HttpURLConnection connection;
    private final Map<String, Object> params;

    private RestApi(String path, String method) throws Exception {
        params = new HashMap<>();

        URL apiUrl = new URL(Config.get("REST_API_URL") + path);
        String apiKey = Config.get("REST_API_KEY");
        connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("X-Api-Key", apiKey);
    }

    public static String getAdminEmail() {
        try(RestApi api = new RestApi("/email/admin", "GET")
        ) {

            String response = api.send();
            Gson gson = new GsonBuilder().create();
            BaseResponse<String> parsedResponse = gson.fromJson(response, new TypeToken<BaseResponse<String>>() {}.getType());
            return parsedResponse.getData();

        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String send() {
        try {
            System.out.printf("[%s] %-8s REST server response code: %s %s%n",
                    LocalDateTime.now(), getClass().getSimpleName(),
                    connection.getResponseCode(), connection.getResponseMessage()
            );
            InputStream is = connection.getInputStream();
            try(BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                StringBuilder response = new StringBuilder();
                String line;
                while((line = br.readLine()) != null) {
                    response.append(line).append('\n');
                }
                String responseStr = response.toString();
                System.out.printf("[%s] %-8s Received response from REST server: %s%n",
                        LocalDateTime.now(), getClass().getSimpleName(), responseStr
                );
                return responseStr;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {
        if(connection != null) connection.disconnect();
    }
}
