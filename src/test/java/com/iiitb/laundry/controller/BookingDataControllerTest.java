package com.iiitb.laundry.controller;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.iiitb.laundry.service.BookingDataService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;



public class BookingDataControllerTest {
    private static final String request,result;

    static {
        request= "{\"startDate\":\"2020-05-02\", \"endDate\":\"2020-05-03\" }";
        result="{\"dataList\":[{\"date\":\"2020-05-02\",\"slotEnd\":\"14:00\",\"card\":633,\"slotStart\":\"13:00\"},{\"date\":\"2020-05-03\",\"slotEnd\":\"14:00\",\"card\":633,\"slotStart\":\"13:00\"}]}";
    }
    @InjectMocks
    BookingDataController bookingDataController;

    @Mock
    BookingDataService bookingDataService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testForFetchBooking() throws Exception{
        String expected="{\"dataList\":[{\"date\":\"2020-05-02\",\"slotEnd\":\"14:00\",\"card\":633,\"slotStart\":\"13:00\"},{\"date\":\"2020-05-03\",\"slotEnd\":\"14:00\",\"card\":633,\"slotStart\":\"13:00\"}]}";
        when(bookingDataService.fetchBookingData(Mockito.anyString(),Mockito.anyString())).thenReturn(result);
        String actual= bookingDataController.getBookingData(request);
        assertEquals(expected,actual);
    }
}
