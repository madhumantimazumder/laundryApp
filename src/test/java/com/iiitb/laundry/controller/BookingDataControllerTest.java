package com.iiitb.laundry.controller;

import com.iiitb.laundry.service.BookingDataService;
import com.iiitb.laundry.utils.MessageConstants;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class BookingDataControllerTest {
    private static final String request;

    static {
        request= "{\"startDate\":\"2020-05-02\", \"endDate\":\"2020-05-03\" }";
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
        JSONObject jObj = (JSONObject)new JSONParser().parse(request);
        String startDate = (String) jObj.get("startDate");
        String endDate = (String) jObj.get("endDate");
        BookingDataService bookingDataService=new BookingDataService();
        String actual= bookingDataService.fetchBookingData(startDate,endDate);
        assertEquals(expected,actual);
    }
}
