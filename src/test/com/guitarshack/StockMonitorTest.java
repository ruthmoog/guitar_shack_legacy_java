package com.guitarshack;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

public class StockMonitorTest {

    @Mock
    Alert alert;

    @Test
    public void productSold() {

        // Given
        alert = mock(Alert.class);
        StockMonitor monitor = new StockMonitor(alert);

        // When
        monitor.productSold(811, 1000);

        // Then
        Mockito.verify(alert).send(new Product(811, 53, 14));
    }

    @Test
    public void testProductSoldNoAlertNeeded() {

        // Given
        alert = mock(Alert.class);
        StockMonitor monitor = new StockMonitor(alert);

        // When
        monitor.productSold(811, 0);

        // Then
        Mockito.verify(alert, never()).send(new Product(811, 53, 14));

    }
}