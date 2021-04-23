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

    @Test
    public void testWhenProductSoldAlertNeeded() {

        // Given
        alert = mock(Alert.class);
        StockMonitor monitor = new StockMonitor(alert, new HttpProductService());

        // When
        monitor.productSold(811, 1000);

        // Then
        Mockito.verify(alert).send(new Product(811, 53, 14));
    }

    @Test
    public void testWhenNoProductSold() {

        // Given
        alert = mock(Alert.class);
        StockMonitor monitor = new StockMonitor(alert, new HttpProductService());

        // When
        monitor.productSold(811, 0);

        // Then
        Mockito.verify(alert, never()).send(new Product(811, 53, 14));

    }
}