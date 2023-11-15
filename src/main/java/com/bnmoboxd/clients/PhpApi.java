package com.bnmoboxd.clients;

import com.bnmoboxd.core.Config;
import com.bnmoboxd.struct.SubscriptionStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class PhpApi implements AutoCloseable {
    private final HttpURLConnection connection;
    private final Map<String, Object> params;

    private PhpApi(String path, String method) throws Exception {
        params = new HashMap<>();

        URL apiUrl = new URL(Config.get("PHP_API_URL") + path);
        String apiKey = Config.get("PHP_API_KEY");
        connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("X-Api-Key", apiKey);
    }

    public static String updateSubscription(String curatorUsername, String subscriberUsername, SubscriptionStatus status) {
        try(PhpApi api = new PhpApi("/subscription", "PUT")
            .setParam("curatorUsername", curatorUsername)
            .setParam("subscriberUsername", subscriberUsername)
            .setParam("status", status)
        ) {
            return api.send();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PhpApi setParam(String key, Object value) {
        params.put(key, value);
        return this;
    }

    public String send() {
        try {
            Gson gson = new GsonBuilder().create();
            String body = gson.toJson(params);

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", Integer.toString(body.getBytes().length));
            connection.setDoOutput(true);

            try(DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
                os.writeBytes(body);
            }

            System.out.printf("[%s] %-8s PHP server response code: %s %s%n",
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
                System.out.printf("[%s] %-8s Received response from PHP server: %s%n",
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
