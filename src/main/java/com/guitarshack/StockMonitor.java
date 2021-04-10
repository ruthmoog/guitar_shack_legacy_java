package com.guitarshack;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StockMonitor {
    private final Alert alert;

    public StockMonitor(Alert alert) {
        this.alert = alert;
    }

    public void productSold(int productId, int quantity) {
        String productResponseBody = getHttpResponseBody(buildProductUri(productId));
        Product product = new Gson().fromJson(productResponseBody, Product.class);

        String salesResponseBody = getHttpResponseBody(getSalesUri(product));
        SalesTotal sales = new Gson().fromJson(salesResponseBody, SalesTotal.class);

        int averageSalesInPast30Days = sales.getTotal() / 30;
        int currentStock = product.getStock() - quantity;
        if(currentStock <= (int) ((double) averageSalesInPast30Days * product.getLeadTime()))
            alert.send(product);
    }

    private URI getSalesUri(Product product) {
        Calendar calendar = Calendar.getInstance();
        Date endDate = getEndDate(calendar);
        Date startDate = getStartDate(calendar);
        DateFormat format = new SimpleDateFormat("M/d/yyyy");
        Map<String, Object> params1 = new HashMap<>(){{
            put("productId", product.getId());
            put("startDate", format.format(startDate));
            put("endDate", format.format(endDate));
            put("action", "total");
        }};
        String paramString1 = "?";

        for (String key : params1.keySet()) {
            paramString1 += key + "=" + params1.get(key).toString() + "&";
        }
        return URI.create("https://gjtvhjg8e9.execute-api.us-east-2.amazonaws.com/default/sales" + paramString1);
    }

    private Date getStartDate(Calendar calendar) {
        calendar.add(Calendar.DATE, -30);
        return calendar.getTime();
    }

    private Date getEndDate(Calendar calendar) {
        calendar.setTime(Calendar.getInstance().getTime());
        return calendar.getTime();
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

    private URI buildProductUri(int productId) {
        Map<String, Object> params = new HashMap<>() {{
            put("id", productId);
        }};
        String paramString = "?";

        for (String key : params.keySet()) {
            paramString += key + "=" + params.get(key).toString() + "&";
        }
        String baseURL = "https://6hr1390c1j.execute-api.us-east-2.amazonaws.com/default/product";
        return URI.create(baseURL + paramString);
    }

}
