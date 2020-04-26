package com.iiitb.laundry.service;

import java.util.List;

import com.iiitb.laundry.beans.Gender;
import com.iiitb.laundry.beans.Hostel;
import com.iiitb.laundry.beans.LaundrySlot;
import com.iiitb.laundry.beans.Student;
import com.iiitb.laundry.repository.LaundrySlotRepository;
import com.iiitb.laundry.repository.StudentRepository;

public class BookingService {
	
	LaundrySlotRepository laundrySlotRepository=new LaundrySlotRepository();
	StudentRepository studentRepository=new StudentRepository();

	public String fetchAvailableSlot(long mobileNo) throws Exception {
		Student student=studentRepository.findByMobileNumber(mobileNo);
		List<LaundrySlot> totalSlot = student.getGender().name().equals(Gender.MALE.name())
				? laundrySlotRepository.findSlotByHostel(Hostel.BHASKARA)
				: laundrySlotRepository.findSlotByHostel(Hostel.LILAVATI);
		//TO-DO need to add filtering logic for the already booked slots
		StringBuilder slotNames=new StringBuilder();
		int count=1;
		for(LaundrySlot slot:totalSlot) {
			String startTime=slot.getStartTime();
			String endTime=slot.getEndTime();
			slotNames.append(String.valueOf(count++)).append(") ".concat(startTime)).append("-".concat(endTime).concat("\n"));
		}
		return slotNames.toString();
	}
}
