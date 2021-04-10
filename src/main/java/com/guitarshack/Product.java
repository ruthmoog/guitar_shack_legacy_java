package com.guitarshack;

import com.google.gson.Gson;

public class Product {
    private final int stock;
    private final int id;
    private final int leadTime;

    public Product(int id, int stock, int leadTime) {
        this.stock = stock;
        this.id = id;
        this.leadTime = leadTime;
    }

    public int getLeadTime() {
        return leadTime;
    }

    public int getId() {
        return id;
    }

    public int getStock() { return stock; }

    public static Product createFromJson(String responseBody) {
        return new Gson().fromJson(responseBody, Product.class);
    }

    @Override
    public boolean equals(Object obj) {

        boolean isAProduct = obj instanceof Product;
        if(!isAProduct) {
            return false;
        }

        Product other = (Product) obj;

        if(other.id!=this.id) {
            return false;
        }
        if(other.stock!=this.stock){
            return false;
        }
        if(other.leadTime!=this.leadTime) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "product: "+this.id+" stock: "+this.stock+" lead time: "+this.leadTime;
    }
}
