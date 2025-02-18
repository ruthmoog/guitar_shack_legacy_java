package com.guitarshack;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Calendar;

public class HttpSalesService implements SalesService {

    private SalesUriBuilder uriBuilder;

    public HttpSalesService(String baseURL) {
        this.uriBuilder = new SalesUriBuilder(Calendar.getInstance(), baseURL);
    }

    @Override
    public SalesTotal getSalesTotal(Product product) {
        URI salesUri = uriBuilder.getSalesUri(product);
        String salesResponseBody = getHttpResponseBody(salesUri);
        return SalesTotal.createFromJson(salesResponseBody);
    }

    private String getHttpResponseBody(URI uri) {
        HttpRequest request = HttpRequest
                .newBuilder(uri)
                .build();
        String result = "";
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            result = response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
