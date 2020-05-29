package com.iiitb.laundry.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import com.iiitb.laundry.beans.Gender;
import com.iiitb.laundry.beans.Hostel;
import com.iiitb.laundry.beans.LaundryBooking;
import com.iiitb.laundry.beans.LaundrySlot;
import com.iiitb.laundry.beans.Status;
import com.iiitb.laundry.beans.Student;
import com.iiitb.laundry.repository.LaundryBookingRepository;
import com.iiitb.laundry.repository.LaundrySlotRepository;
import com.iiitb.laundry.repository.StudentRepository;
import com.iiitb.laundry.utils.MessageConstants;

public class BookingService {
	
	LaundrySlotRepository laundrySlotRepository=new LaundrySlotRepository();
	StudentRepository studentRepository=new StudentRepository();
	LaundryBookingRepository laundryBookingRepository=new LaundryBookingRepository();
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
	}
	
	private void filterBookedSlots(List<LaundrySlot> slots,String bookingDate,LocalTime currTime) throws Exception {
		List<LaundryBooking> bookedSlot=laundryBookingRepository.fetchAllBookedSlots(bookingDate);
		
		for(Iterator itr=slots.iterator();itr.hasNext();) {
			LaundrySlot slot=((LaundrySlot)itr.next());
			LocalTime startTime=LocalTime.parse(slot.getStartTime());
			if(currTime.compareTo(END_TIME_FOR_TODAY_BOOKING)<=0 && currTime.compareTo(startTime)>=0) {
				itr.remove();
				continue;
			}
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
		LocalTime currTime=LocalTime.now();
		String bookingDate=fetchBookingDate(currTime);
		System.out.println(bookingDate);
		filterBookedSlots(totalSlot,bookingDate,currTime);
				
		StringBuilder slotNames=new StringBuilder();
		if(totalSlot.size()!=0) {
			slotNames.append(MessageConstants.AVAILABLE_SLOTS_HEAD_MSG).append("*").append(bookingDate).append("*\n\n");
		}
		
		for(LaundrySlot slot:totalSlot) {
			String startTime=slot.getStartTime();
			String endTime=slot.getEndTime();
			slotNames.append("S").append(String.valueOf(slot.getSlotId())).append(") ".concat(startTime)).append("-".concat(endTime).concat("\n"));
		}
		//message when slotNames is Empty
		return slotNames.toString();
	}
	
	public LaundryBooking fetchBookedSlot(long mobileNo) throws Exception{
		String bookingDate=fetchBookingDate(LocalTime.now());
		Student student=studentRepository.findByMobileNumber(mobileNo);
		return laundryBookingRepository.fetchBookedSlot(student, bookingDate);
	}
	
	public void bookSlot(int slotNo,long mobileNo) throws Exception {
		LaundryBooking laundryBooking=new LaundryBooking();
		LaundrySlot laundrySlot=laundrySlotRepository.findSlotBySlotNo(slotNo);
		LocalTime startTime=LocalTime.parse(laundrySlot.getStartTime());
		if(LocalTime.now().compareTo(END_TIME_FOR_TODAY_BOOKING)<=0 && LocalTime.now().compareTo(startTime)>=0) throw new Exception();// trying to book a slot from the past
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String bookingDate=fetchBookingDate(LocalTime.now());
		laundryBooking.setBookingDate(dateFormatter.parse(bookingDate));
		laundryBooking.setStudent(studentRepository.findByMobileNumber(mobileNo));
		laundryBooking.setSlot(laundrySlot);
		laundryBooking.setStatus(Status.NORMAL);
		laundryBookingRepository.saveLaundryBooking(laundryBooking);
		/*StringBuilder str=new StringBuilder();
		str.append(laundryBooking.getSlot().getStartTime()).append("-").append(laundryBooking.getSlot().getEndTime())
				.append(",").append(bookingDate);*/
	}
	
	public void cancelSlot(long mobileNo) throws Exception{
		String bookingDate=fetchBookingDate(LocalTime.now());
		Student student=studentRepository.findByMobileNumber(mobileNo);
		laundryBookingRepository.cancelLaundryBooking(bookingDate, student);
	}
}
