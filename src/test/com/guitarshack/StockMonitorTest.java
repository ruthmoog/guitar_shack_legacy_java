package com.guitarshack;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class StockMonitorTest {

    @Mock Alert alert;
    @Mock ProductService productService;

    @Test
    public void testWhenProductSoldAlertNeeded() {

        // Given
        Product product = new Product(811, 53, 14);

        alert = mock(Alert.class);
        productService = mock(ProductService.class);
        when(productService.getProduct(product.getId())).thenReturn(product);

        StockMonitor monitor = new StockMonitor(alert, productService);

        // When
        monitor.productSold(product.getId(), 1000);

        // Then
        Mockito.verify(alert).send(product);
    }

    @Test
    public void testWhenNoProductSold() {

        // Given
        Product product = new Product(811, 53, 14);

        alert = mock(Alert.class);
        productService = mock(ProductService.class);
        when(productService.getProduct(product.getId())).thenReturn(product);

        StockMonitor monitor = new StockMonitor(alert, productService);

        // When
        monitor.productSold(product.getId(), 0);

        // Then
        Mockito.verify(alert, never()).send(product);

    }
}