package com.guitarshack;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SalesUriBuilder {

    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("M/d/yyyy");
    private final Calendar calendar;
    private final String baseURL;

    public SalesUriBuilder(Calendar calendar, String baseURL) {
        this.calendar = calendar;
        this.baseURL = baseURL;
    }

    public URI getSalesUri(Product product) {
        Map<String, Object> queryParameters = buildQueryParameters(product);
        String queryString = buildQueryString(queryParameters);
        return URI.create(baseURL + queryString);
    }

    private String buildQueryString(Map<String, Object> queryParameters) {
        String queryString = "?";
        for (String key : queryParameters.keySet()) {
            queryString += key + "=" + queryParameters.get(key).toString() + "&";
        }
        return queryString;
    }

    private Map<String, Object> buildQueryParameters(Product product) {
        return new HashMap<>(){{
                put("productId", product.getId());
                put("startDate", FORMAT.format(getStartDate()));
                put("endDate", FORMAT.format(calendar.getTime()));
                put("action", "total");
            }};
    }

    private Date getStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.calendar.getTime());
        calendar.add(Calendar.DATE, -30);
        return calendar.getTime();
    }
}
