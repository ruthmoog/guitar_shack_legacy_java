package com.guitarshack;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class StockMonitorTest {

    @Mock Alert alert;
    @Mock ProductService productService;
    @Mock SalesService salesService;

    private int someProductId = 811;
    private int twoWeeksLeadTime = 14;
    private SalesTotal someSalesTotal = new SalesTotal(1);

    @Before
    public void setUp() {
        alert = mock(Alert.class);
        productService = mock(ProductService.class);
        salesService = mock(SalesService.class);
    }

    @Test
    public void testAlertForOutOfStockProduct() {

        // Given
        Product outOfStockProduct = new Product(someProductId, 0, twoWeeksLeadTime);

        when(productService.getProduct(outOfStockProduct.getId())).thenReturn(outOfStockProduct);
        when(salesService.getSalesTotal(outOfStockProduct)).thenReturn(someSalesTotal);

        StockMonitor monitor = new StockMonitor(alert, productService, salesService);

        // When
        monitor.productSold(outOfStockProduct.getId(), 1);

        // Then
        Mockito.verify(alert).send(outOfStockProduct);
    }

    @Test
    public void testNoAlertForWellStockedProduct() {

        // Given
        Product wellStockedProduct = new Product(someProductId, 500, twoWeeksLeadTime);

        when(productService.getProduct(wellStockedProduct.getId())).thenReturn(wellStockedProduct);
        when(salesService.getSalesTotal(wellStockedProduct)).thenReturn(someSalesTotal);

        StockMonitor monitor = new StockMonitor(alert, productService, salesService);

        // When
        monitor.productSold(wellStockedProduct.getId(), 1);

        // Then
        Mockito.verify(alert, never()).send(wellStockedProduct);

    }
}