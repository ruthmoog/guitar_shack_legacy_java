package com.guitarshack;

public class Program {

    private static StockMonitor monitor = new StockMonitor(product -> {
        // We are faking this for now
        System.out.println(
                "You need to reorder product " + product.getId() +
                        ". Only " + product.getStock() + " remaining in stock");
    }, new HttpProductService(Configuration.PRODUCT_SERVICE_BASE_URL), new HttpSalesService(Configuration.SALES_SERVICE_BASE_URL));

    public static void main(String[] args) {
        int productId = Integer.parseInt(args[0]);
        int quantity = Integer.parseInt(args[1]);

        monitor.productSold(productId, quantity);
    }
}
