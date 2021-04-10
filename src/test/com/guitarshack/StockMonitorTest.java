package com.guitarshack;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class StockMonitorTest {

    @Mock
    Alert alert;

    @Test
    public void productSold() {

        // Given
        alert = mock(Alert.class);
        StockMonitor monitor = new StockMonitor(alert);

        // When
        monitor.productSold(811, 100);

        // Then
        Mockito.verify(alert).send(any(Product.class));

    }
}