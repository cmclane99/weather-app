/**
 * Chris McLane
 */
package com.example.weatherapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHandlerThread implements Runnable{
    private String result = "HTTPS unable to get";
    private String url;

    public HttpHandlerThread(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        try {
            result = downloadUrl(url);
        } catch (IOException e) {
            return;
        }

    }

    public String downloadUrl(String urlString) throws IOException {
        String result = "Download https error";
        InputStream in = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            in = conn.getInputStream();

            StringBuilder stringBuilder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            result = stringBuilder.toString();
        } catch (IOException e) {
        } finally {
            if(in != null)
                try {
                    in.close();
                    reader.close();
                } catch (IOException e) {
                }
        }
        return result;
    }

    public String getResult() {
        return result;
    }

}
