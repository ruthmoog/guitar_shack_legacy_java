package com.guitarshack;

import org.junit.Assert;
import org.junit.Test;

import java.net.URI;

public class SalesUriBuilderTest {

    @Test
    public void testGetSalesUri() {
        // Given
        Product product = new Product(1,2,3);
        SalesUriBuilder salesUriBuilder = new SalesUriBuilder();
        String expected = "https://gjtvhjg8e9.execute-api.us-east-2.amazonaws.com/default/sales?productId=1&endDate=6/12/2021&action=total&startDate=5/13/2021&";

        // When
        URI actual = salesUriBuilder.getSalesUri(product);

        // Then
        Assert.assertEquals(expected, actual.toString());
    }
}
