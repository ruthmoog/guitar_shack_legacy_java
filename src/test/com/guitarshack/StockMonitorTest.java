package com.guitarshack;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class StockMonitorTest {

    private StockMonitor monitorUnderTest;

    @Mock Alert alertSpy;
    @Mock ProductService productService;
    @Mock SalesService salesService;

    private int someProductId = 811;

    private int twoWeeksLeadTime = 14;
    private int oneDaysLeadTime = 1;
    private int twoDaysLeadTime = 2;

    private SalesTotal someSalesTotal = new SalesTotal(1);
    private SalesTotal oneSalePerDay = new SalesTotal(30);



    @Before
    public void setUp() {
        alertSpy = mock(Alert.class);
        productService = mock(ProductService.class);
        salesService = mock(SalesService.class);
        monitorUnderTest = new StockMonitor(alertSpy, productService, salesService);
    }

    @Test
    public void testAlertForOutOfStockProduct() {

        // Given
        Product outOfStockProduct = new Product(someProductId, 0, twoWeeksLeadTime);

        when(productService.getProduct(outOfStockProduct.getId())).thenReturn(outOfStockProduct);
        when(salesService.getSalesTotal(outOfStockProduct)).thenReturn(someSalesTotal);

        // When
        sellOneProduct(outOfStockProduct);

        // Then
        Mockito.verify(alertSpy).send(outOfStockProduct);
    }

    @Test
    public void testNoAlertForWellStockedProduct() {

        // Given
        Product wellStockedProduct = new Product(someProductId, 500, twoWeeksLeadTime);

        when(productService.getProduct(wellStockedProduct.getId())).thenReturn(wellStockedProduct);
        when(salesService.getSalesTotal(wellStockedProduct)).thenReturn(someSalesTotal);

        // When
        sellOneProduct(wellStockedProduct);

        // Then
        Mockito.verify(alertSpy, never()).send(wellStockedProduct);
    }

    @Test
    public void testNoAlertWithSufficientLeadTime() {
        // Given
        Product lowStockedProduct = new Product(someProductId, 3, oneDaysLeadTime);

        when(productService.getProduct(lowStockedProduct.getId())).thenReturn(lowStockedProduct);
        when(salesService.getSalesTotal(lowStockedProduct)).thenReturn(oneSalePerDay);

        // When
        sellOneProduct(lowStockedProduct);

        // Then
        Mockito.verify(alertSpy, never()).send(lowStockedProduct);
    }

    @Test
    public void testNoAlertWithInsufficientLeadTime() {
        // Given
        Product lowStockedProduct = new Product(someProductId, 3, twoDaysLeadTime);

        when(productService.getProduct(lowStockedProduct.getId())).thenReturn(lowStockedProduct);
        when(salesService.getSalesTotal(lowStockedProduct)).thenReturn(oneSalePerDay);

        // When
        sellOneProduct(lowStockedProduct);

        // Then
        Mockito.verify(alertSpy).send(lowStockedProduct);
    }

    private void sellOneProduct(Product outOfStockProduct) {
        monitorUnderTest.productSold(outOfStockProduct.getId(), 1);
    }
}