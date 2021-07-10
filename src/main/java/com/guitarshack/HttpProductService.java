package com.guitarshack;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class HttpProductService implements ProductService {

    private final String baseURL;

    public HttpProductService(String baseURL) {
        this.baseURL = baseURL;
    }

    @Override
    public Product getProduct(int productId) {
        String productResponseBody = getHttpResponseBody(buildProductUri(productId));
        return Product.createFromJson(productResponseBody);
    }

    private URI buildProductUri(int productId) {
        Map<String, Object> params = new HashMap<>() {{
            put("id", productId);
        }};
        String paramString = "?";

        for (String key : params.keySet()) {
            paramString += key + "=" + params.get(key).toString() + "&";
        }
        return URI.create(baseURL + paramString);
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
