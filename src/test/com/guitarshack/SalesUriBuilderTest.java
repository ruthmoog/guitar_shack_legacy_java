package com.guitarshack;

import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SalesUriBuilderTest {

    @Test
    public void testGetSalesUri() {
        // Given
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new GregorianCalendar(2021, Calendar.JUNE, 12).getTime());
        Product product = new Product(1,2,3);
        String baseURL = "http://localhost.not/a/real/url/";
        SalesUriBuilder salesUriBuilder = new SalesUriBuilder(calendar, baseURL);
        String expected = baseURL + "?productId=1&endDate=6/12/2021&action=total&startDate=5/13/2021&";

        // When
        URI actual = salesUriBuilder.getSalesUri(product);

        // Then
        Assert.assertEquals(expected, actual.toString());
    }
}
