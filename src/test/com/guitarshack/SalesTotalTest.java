package com.guitarshack;

import org.junit.Assert;
import org.junit.Test;

public class SalesTotalTest {

    @Test
    public void testCreateSalesTotalFromJson() {
        // Given
        String responseInJSON = "{\"productID\":811,\"startDate\":\"4/1/2021\",\"endDate\":\"5/1/2021\",\"total\":20}";
        SalesTotal expectedSalesTotal = new SalesTotal(20);

        // When
        SalesTotal actualSalesTotal = SalesTotal.createFromJson(responseInJSON);

        // Then
        Assert.assertEquals("Sales total should match", expectedSalesTotal, actualSalesTotal);
    }
}
