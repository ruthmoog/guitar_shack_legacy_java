package com.guitarshack;

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

public class HttpSalesService implements SalesService {

    @Override
    public SalesTotal getSalesTotal(Product product) {
        String salesResponseBody = getHttpResponseBody(getSalesUri(product));
        return SalesTotal.createFromJson(salesResponseBody);
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
        String url = "https://gjtvhjg8e9.execute-api.us-east-2.amazonaws.com/default/sales" + paramString1;
        System.out.println("url: " + url);
        return URI.create(url);
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
}
