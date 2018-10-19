package com.tws.hunt.stages;

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
import org.springframework.beans.factory.annotation.Autowired;

import com.tws.hunt.config.HuntGameConfig;

public abstract class BaseStage {

    @Autowired
    protected HuntGameConfig config;

    public void play() throws Exception {
        HttpResponse res = executeGetRequest(config.getInputUrl());
        JSONArray result = parseResponse(res);
        sendCount(getCount(result));
    }

    protected abstract long getCount(JSONArray result);

    protected JSONArray parseResponse(HttpResponse res) throws Exception {
        String resp = readResponse(res);
        return (JSONArray)new JSONParser().parse(resp);
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

    protected String readResponse(HttpResponse response) throws IOException {
        BufferedReader rd = new BufferedReader(
            new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line).append("\n");
        }
        return result.toString();
    }

    protected void sendCount(long count) throws ClientProtocolException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(config.getOutputUrl());
        addUserIdHeader(post);
        post.addHeader("content-type", "application/json");

        String countResponse = "{\"count\": " + count + "}";
        System.out.println(countResponse);
        StringEntity params = new StringEntity(countResponse);
        post.setEntity(params);

        HttpResponse response = client.execute(post);
        String result = readResponse(response);
        System.out.println(result);
    }
}
