package com.iiitb.laundry.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.iiitb.laundry.beans.Gender;
import com.iiitb.laundry.beans.Hostel;
import com.iiitb.laundry.beans.LaundryBooking;
import com.iiitb.laundry.beans.LaundrySlot;
import com.iiitb.laundry.beans.Student;
import com.iiitb.laundry.repository.LaundryBookingRepository;
import com.iiitb.laundry.repository.LaundrySlotRepository;
import com.iiitb.laundry.repository.StudentRepository;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BookingService.class)
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
		boyHostelSlot=(new ArrayList<LaundrySlot>(Arrays.asList(slots)));
		
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
		girlHostelSlot=(new ArrayList<LaundrySlot>(Arrays.asList(slots)));
		
		girlLaundryBooking=new ArrayList<LaundryBooking>();
		LaundryBooking mockBooking1=new LaundryBooking();
		mockBooking1.setSlot(slots[2]);
		LaundryBooking mockBooking2=new LaundryBooking();
		mockBooking2.setSlot(slots[2]);
		girlLaundryBooking.add(mockBooking1);
		girlLaundryBooking.add(mockBooking2);
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
	BookingService spy=PowerMockito.spy(new BookingService());
	
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
	
	
	@Test
	public void testFetchAvailableSlotForBoyWhenMachineAvailableInAllSlot() throws Exception{
		PowerMockito.doReturn(LocalTime.parse("20:00")).when(spy, "getCurrentServerTime");
		PowerMockito.doReturn("2020-05-02").when(spy, "fetchBookingDate",Mockito.any());
		PowerMockito.when(studentRepository.findByMobileNumber(Mockito.anyLong())).thenReturn(maleStudent);
		PowerMockito.when(laundrySlotRepository.findSlotByHostel(Mockito.any())).thenReturn(boyHostelSlot);
		PowerMockito.when(laundryBookingRepository.fetchAllBookedSlots(Mockito.anyString())).thenReturn(boyLaundryBooking);
		String expected="Available Laundry Slots for *2020-05-02*\n" +"\n"+ 
				"S0) 10:00-11:00\n" + 
				"S1) 11:00-12:00\n" + 
				"S2) 12:00-13:00\n";
		String actual=spy.fetchAvailableSlot(Long.valueOf(maleStudent.getMobileNo()));
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFetchAvailableSlotForGirlWhenMachineNotAvailableInOneSlot() throws Exception{
		PowerMockito.doReturn(LocalTime.parse("20:00")).when(spy, "getCurrentServerTime");
		PowerMockito.doReturn("2020-05-02").when(spy, "fetchBookingDate",Mockito.any());
		PowerMockito.when(studentRepository.findByMobileNumber(Mockito.anyLong())).thenReturn(femaleStudent);
		PowerMockito.when(laundrySlotRepository.findSlotByHostel(Mockito.any())).thenReturn(girlHostelSlot);
		PowerMockito.when(laundryBookingRepository.fetchAllBookedSlots(Mockito.anyString())).thenReturn(girlLaundryBooking);
		String expected="Available Laundry Slots for *2020-05-02*\n" +"\n"+ 
				"S0) 11:00-12:00\n" + 
				"S1) 12:00-13:00\n"; 
		String actual=spy.fetchAvailableSlot(Long.valueOf(femaleStudent.getMobileNo()));
		assertEquals(expected, actual);
	}
}
