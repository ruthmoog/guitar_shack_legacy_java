package com.guitarshack;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SalesUriBuilder {


    private final Calendar calendar;
    private final String baseURL;

    public SalesUriBuilder(Calendar calendar, String baseURL) {
        this.calendar = calendar;
        this.baseURL = baseURL;
    }

    public URI getSalesUri(Product product) {
        Date endDate = getEndDate();
        Date startDate = getStartDate();
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
        String url = baseURL + paramString1;
        System.out.println("url: " + url);
        return URI.create(url);
    }

    private Date getStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.calendar.getTime());
        calendar.add(Calendar.DATE, -30);
        return calendar.getTime();
    }

    private Date getEndDate() {
        return calendar.getTime();
    }
}
