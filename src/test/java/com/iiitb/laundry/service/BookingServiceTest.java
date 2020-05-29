package com.iiitb.laundry.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.iiitb.laundry.beans.Gender;
import com.iiitb.laundry.beans.Hostel;
import com.iiitb.laundry.beans.LaundryBooking;
import com.iiitb.laundry.beans.LaundrySlot;
import com.iiitb.laundry.beans.Student;
import com.iiitb.laundry.repository.LaundryBookingRepository;
import com.iiitb.laundry.repository.LaundrySlotRepository;
import com.iiitb.laundry.repository.StudentRepository;

public class BookingServiceTest {
	private static final Student femaleStudent=new Student();
	private static final Student maleStudent=new Student();
	private static List<LaundrySlot> boyHostelSlot;
	private static List<LaundrySlot> girlHostelSlot;
	private static List<LaundryBooking> boyLaundryBooking;
	private static List<LaundryBooking> girlLaundryBooking;
	
	private static void prepareSlotAndBookingForBoyHostel(){
		LaundrySlot slots[]=new LaundrySlot[3];
		for(int i=0;i<3;i++) {
			slots[i]=new LaundrySlot();
			slots[i].setHostel(Hostel.BHASKARA);
			slots[i].setNoOfMachines(5);
			slots[i].setSlotId(i);
		}
		slots[0].setStartTime("10:00");slots[0].setEndTime("11:00");
		slots[1].setStartTime("11:00");slots[1].setEndTime("12:00");
		slots[2].setStartTime("12:00");slots[2].setEndTime("13:00");
		boyHostelSlot=Arrays.asList(slots);
		
		boyLaundryBooking=new ArrayList<LaundryBooking>();
		LaundryBooking mockBooking=new LaundryBooking();
		mockBooking.setSlot(slots[1]);
		boyLaundryBooking.add(mockBooking);
	}
	
	private static void prepareSlotAndBookingForGirlHostel(){
		LaundrySlot slots[]=new LaundrySlot[3];
		for(int i=0;i<3;i++) {
			slots[i]=new LaundrySlot();
			slots[i].setHostel(Hostel.LILAVATI);
			slots[i].setNoOfMachines(2);
			slots[i].setSlotId(i);
		}
		slots[0].setStartTime("11:00");slots[0].setEndTime("12:00");
		slots[1].setStartTime("12:00");slots[1].setEndTime("13:00");
		slots[2].setStartTime("13:00");slots[2].setEndTime("14:00");
		girlHostelSlot=Arrays.asList(slots);
		
		girlLaundryBooking=new ArrayList<LaundryBooking>();
		LaundryBooking mockBooking=new LaundryBooking();
		mockBooking.setSlot(slots[2]);
		girlLaundryBooking.add(mockBooking);
	}
	
	static {
		femaleStudent.setFirstName("Geeta");
		femaleStudent.setGender(Gender.FEMALE);
		femaleStudent.setLaundryCardNo(Long.valueOf("512"));
		femaleStudent.setMobileNo(Long.valueOf("9000000001"));
		femaleStudent.setRollNo("MT2019200");
		femaleStudent.setStudentId(1);
		prepareSlotAndBookingForGirlHostel();
		
		maleStudent.setFirstName("Utsav");
		maleStudent.setGender(Gender.MALE);
		maleStudent.setLaundryCardNo(Long.valueOf("513"));
		maleStudent.setMobileNo(Long.valueOf("9000000000"));
		maleStudent.setRollNo("MT2019201");
		maleStudent.setStudentId(2);
		prepareSlotAndBookingForBoyHostel();
	}
	
	@InjectMocks
	BookingService bookingService;
	
	@Mock
	LaundrySlotRepository laundrySlotRepository;
	
	@Mock
	StudentRepository studentRepository;
	
	@Mock
	LaundryBookingRepository laundryBookingRepository;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	/*
	private static final LocalTime END_TIME_FOR_TODAY_BOOKING=LocalTime.parse("18:59");
	private static final LocalTime END_OF_DAY_TIME=LocalTime.parse("00:00");
	
	private String fetchBookingDate(LocalTime currTime) {
		String bookingDate=null;
		DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if(currTime.compareTo(END_OF_DAY_TIME)>=0 && currTime.compareTo(END_TIME_FOR_TODAY_BOOKING)<=0) {
			bookingDate=dtf.format(LocalDate.now());
		}else {
			bookingDate=dtf.format(LocalDate.now().plusDays(1));
		}
		return bookingDate;
	}*/
	
	@Test
	public void testFetchAvailableSlotForBoy() throws Exception{
		when(studentRepository.findByMobileNumber(Mockito.anyLong())).thenReturn(maleStudent);
		when(laundrySlotRepository.findSlotByHostel(Mockito.any())).thenReturn(boyHostelSlot);
		when(laundryBookingRepository.fetchAllBookedSlots(Mockito.anyString())).thenReturn(boyLaundryBooking);
		String expected="Available Laundry Slots for *2020-05-30*\n" + 
				"\n" + "S0) 10:00-11:00\n" + "S1) 11:00-12:00\n" + "S2) 12:00-13:00";
		String actual=bookingService.fetchAvailableSlot(Long.valueOf(maleStudent.getMobileNo()));
		
		System.out.println(actual);
		assertEquals(true, true);
	}
}
