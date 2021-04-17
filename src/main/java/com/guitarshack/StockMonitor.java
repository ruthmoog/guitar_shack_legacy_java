package com.guitarshack;

interface ProductService {
    Product getProduct(int productId);
}

interface SalesService {
    SalesTotal getSalesTotal(Product product);
}

public class StockMonitor {
    private final Alert alert;
    private ProductService httpProductService;
    private SalesService httpSalesService;

    public StockMonitor(Alert alert) {
        this.alert = alert;
        this.httpProductService = new HttpProductService();
        this.httpSalesService = new HttpSalesService();
    }

    public void productSold(int productId, int quantitySold) {
        Product product = httpProductService.getProduct(productId);
        SalesTotal sales = httpSalesService.getSalesTotal(product);

        if(isStockLow(quantitySold, product, sales))
            alert.send(product);
    }

    private boolean isStockLow(int quantitySold, Product product, SalesTotal salesInPast30Days) {
        int averageSalesPerDay = salesInPast30Days.getTotal() / 30;
        int currentStock = product.getStock() - quantitySold;
        return currentStock <= (int) ((double) averageSalesPerDay * product.getLeadTime());
    }
}
