package com.guitarshack;

interface ProductService {
    Product getProduct(int productId);
}

interface SalesService {
    SalesTotal getSalesTotal(Product product);
}

public class StockMonitor {
    private final Alert alert;
    private ProductService productService;
    private SalesService salesService;

    public StockMonitor(Alert alert, ProductService productService, SalesService salesService) {
        this.alert = alert;
        this.productService = productService;
        this.salesService = salesService;
    }

    public void productSold(int productId, int quantitySold) {
        Product product = productService.getProduct(productId);
        SalesTotal sales = salesService.getSalesTotal(product);

        if(isStockLow(quantitySold, product, sales))
            alert.send(product);
    }

    private boolean isStockLow(int quantitySold, Product product, SalesTotal salesInPast30Days) {
        int averageSalesPerDay = salesInPast30Days.getTotal() / 30;
        int currentStock = product.getStock() - quantitySold;
        System.out.println("av sales/day" + averageSalesPerDay);
        System.out.println("current stock DONKEYYEYEYE" + currentStock);

        return currentStock <= (int) ((double) averageSalesPerDay * product.getLeadTime());
    }
}
