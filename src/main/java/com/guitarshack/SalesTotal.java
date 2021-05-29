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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SalesTotal)) {
            return false;
        }
        SalesTotal other = (SalesTotal) obj;
        return other.getTotal() == this.total;
    }

    @Override
    public String toString() {
        return "Sales total is " + String.valueOf(this.getTotal());
    }
}
