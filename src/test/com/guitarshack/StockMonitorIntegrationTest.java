package com.guitarshack;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

public class StockMonitorIntegrationTest {

    @Mock
    Alert alert;

    private final Product LES_PAUL_CLASSIC = new Product(811, 27, 14);

    @Test
    public void testWhenProductSoldAlertNeeded() {

        // Given
        alert = mock(Alert.class);
        StockMonitor monitor = new StockMonitor(alert, new HttpProductService(Configuration.PRODUCT_SERVICE_BASE_URL), new HttpSalesService());

        // When
        monitor.productSold(LES_PAUL_CLASSIC.getId(), 1000);

        // Then
        Mockito.verify(alert).send(LES_PAUL_CLASSIC);
    }

    @Test
    public void testWhenNoProductSold() {

        // Given
        alert = mock(Alert.class);
        StockMonitor monitor = new StockMonitor(alert, new HttpProductService(Configuration.PRODUCT_SERVICE_BASE_URL), new HttpSalesService());

        // When
        monitor.productSold(LES_PAUL_CLASSIC.getId(), 0);

        // Then
        Mockito.verify(alert, never()).send(LES_PAUL_CLASSIC);

    }
}