package com.iiitb.laundry.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import com.iiitb.laundry.beans.Gender;
import com.iiitb.laundry.beans.Hostel;
import com.iiitb.laundry.beans.LaundryBooking;
import com.iiitb.laundry.beans.LaundrySlot;
import com.iiitb.laundry.beans.Student;
import com.iiitb.laundry.repository.LaundryBookingRepository;
import com.iiitb.laundry.repository.LaundrySlotRepository;
import com.iiitb.laundry.repository.StudentRepository;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

public class BookingService {
	
	LaundrySlotRepository laundrySlotRepository=new LaundrySlotRepository();
	StudentRepository studentRepository=new StudentRepository();
	LaundryBookingRepository laundryBookingRepository=new LaundryBookingRepository();
	private static final LocalTime END_TIME_FOR_TODAY_BOOKING=LocalTime.parse("19:40");
	private static final LocalTime END_OF_DAY_TIME=LocalTime.parse("00:00");
	
	private void filterBookedSlots(List<LaundrySlot> slots,String bookingDate) throws Exception {
		List<LaundryBooking> bookedSlot=laundryBookingRepository.fetchAllBookedSlots(bookingDate);
		
		for(Iterator itr=slots.iterator();itr.hasNext();) {
			LaundrySlot slot=((LaundrySlot)itr.next());
			int count=0;
			for(LaundryBooking booking:bookedSlot) {
				if(booking.getSlot().getSlotId()==slot.getSlotId()) {
					count++;
				}
			}
			if(count>=slot.getNoOfMachines())
				itr.remove();
		}
	}

	public String fetchAvailableSlot(long mobileNo) throws Exception {
		Student student=studentRepository.findByMobileNumber(mobileNo);
		List<LaundrySlot> totalSlot = student.getGender().name().equals(Gender.MALE.name())
				? laundrySlotRepository.findSlotByHostel(Hostel.BHASKARA)
				: laundrySlotRepository.findSlotByHostel(Hostel.LILAVATI);
		//TO-DO need to add filtering logic for the already booked slots
		LocalTime currTime=LocalTime.now();
		String bookingDate=null;
		DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if(currTime.compareTo(END_OF_DAY_TIME)>=0 && currTime.compareTo(END_TIME_FOR_TODAY_BOOKING)<=0) {
			bookingDate=dtf.format(LocalDate.now());
		}else {
			bookingDate=dtf.format(LocalDate.now().plusDays(1));
		}
		System.out.println(bookingDate);
		filterBookedSlots(totalSlot,bookingDate);
				
		StringBuilder slotNames=new StringBuilder();
		int count=1;
		for(LaundrySlot slot:totalSlot) {
			String startTime=slot.getStartTime();
			String endTime=slot.getEndTime();
			slotNames.append(String.valueOf(count++)).append(") ".concat(startTime)).append("-".concat(endTime).concat("\n"));
		}
		//message when slotNames is Empty
		return slotNames.toString();
	}
}
