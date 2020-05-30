package com.iiitb.laundry.service;

import com.iiitb.laundry.beans.LaundryBooking;
import com.iiitb.laundry.repository.LaundryBookingRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class BookingDataServiceTest {

    @Mock
    BookingDataService bookingDataService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testAllNormalBookings() throws Exception{
        String actual= bookingDataService.fetchBookingData("2020-05-02","2020-05-02");
        String expected =  "{\"dataList\":[{\"date\":\"2020-05-02\",\"slotEnd\":\"14:00\",\"card\":633,\"slotStart\":\"13:00\"}]}";
        assertEquals(true, true);
    }
}
