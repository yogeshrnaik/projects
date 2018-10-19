package com.tws.hunt.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tws.hunt.config.HuntGameConfig;

@Service
public class HuntGameService {

    @Autowired
    private HuntGameConfig config;

    public void play() throws ClientProtocolException, IOException, ParseException {
        HttpResponse res = executeGetRequest(config.getInputUrl());
        int count = getCount(res);
        sendCount(count);
    }

    private void sendCount(int count) throws ClientProtocolException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(config.getOutputUrl());
        addUserIdHeader(post);
        post.addHeader("content-type", "application/json");

        String countResponse = "{\"count\": " + count + "}";
        System.out.println(countResponse);
        StringEntity params = new StringEntity(countResponse);
        post.setEntity(params);

        HttpResponse response = client.execute(post);
        System.out.println("Response Code : "
            + response.getStatusLine().getStatusCode());

        String result = readResponse(response);
        System.out.println(result);
    }

    private int getCount(HttpResponse res) throws IOException, ParseException {
        String resp = readResponse(res);
        System.out.println("Response : " + resp);
        JSONArray obj = (JSONArray)new JSONParser().parse(resp);
        return obj.size();
    }

    private HttpResponse executeGetRequest(String url) throws IOException, ClientProtocolException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        addUserIdHeader(request);
        return client.execute(request);
    }

    private void addUserIdHeader(HttpRequestBase request) {
        request.addHeader("userId", config.getUserId());
    }

    private String readResponse(HttpResponse response) throws IOException {
        BufferedReader rd = new BufferedReader(
            new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line).append("\n");
        }
        return result.toString();
    }
}
