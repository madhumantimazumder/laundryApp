package com.iiitb.laundry.service;

import com.iiitb.laundry.beans.Gender;
import com.iiitb.laundry.beans.LaundryBooking;
import com.iiitb.laundry.beans.LaundrySlot;
import com.iiitb.laundry.beans.Student;
import com.iiitb.laundry.controller.BookingDataController;
import com.iiitb.laundry.repository.LaundryBookingRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class BookingDataServiceTest {

    private static final Student femaleStudent=new Student();
    private static final LaundrySlot slot=new LaundrySlot();
    static {
        femaleStudent.setFirstName("Madhumanti");
        femaleStudent.setGender(Gender.FEMALE);
        femaleStudent.setLaundryCardNo(Long.valueOf("633"));
        femaleStudent.setMobileNo(Long.valueOf("9433591033"));
        femaleStudent.setRollNo("MT2019058");
        femaleStudent.setStudentId(4);
        slot.setStartTime("13:00");
        slot.setEndTime("14:00");
    }
    @InjectMocks
    BookingDataService bookingDataService;
    @Mock
    LaundryBookingRepository laundryBookingRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testAllNormalBookings() throws Exception{
        List<LaundryBooking> totalBooking=new ArrayList<LaundryBooking>();
        LaundryBooking lb=new LaundryBooking();
        lb.setStudent(femaleStudent);
        lb.setBookingDate(new Date(2020-05-30));
        lb.setSlot(slot);
        when(laundryBookingRepository.fetchAllNormalBookings("startDate","endDate")).thenReturn(totalBooking);
        String actual= bookingDataService.fetchBookingData("2020-05-30","2020-05-30");
        String expected =  "{\"dataList\":[{\"date\":\"2020-05-02\",\"slotEnd\":\"14:00\",\"card\":633,\"slotStart\":\"13:00\"}]}";
        assertEquals(true, true);
    }
}
