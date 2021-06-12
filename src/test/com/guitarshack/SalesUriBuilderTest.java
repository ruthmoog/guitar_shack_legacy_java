package com.guitarshack;

import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.mock;

public class SalesUriBuilderTest {

    @Test
    public void testGetSalesUri() {
        // Given
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new GregorianCalendar(2021, Calendar.JUNE, 12).getTime());
        Product product = new Product(1,2,3);
        SalesUriBuilder salesUriBuilder = new SalesUriBuilder(calendar);
        String expected = "https://gjtvhjg8e9.execute-api.us-east-2.amazonaws.com/default/sales?productId=1&endDate=6/12/2021&action=total&startDate=5/13/2021&";

        // When
        URI actual = salesUriBuilder.getSalesUri(product);

        // Then
        Assert.assertEquals(expected, actual.toString());
    }
}
