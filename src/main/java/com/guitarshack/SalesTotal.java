package com.guitarshack;

import com.google.gson.Gson;

public class SalesTotal {
    private int total = 0;

    public SalesTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public static SalesTotal createFromJson(String responseBody) {
        return new Gson().fromJson(responseBody, SalesTotal.class);
    }
}
