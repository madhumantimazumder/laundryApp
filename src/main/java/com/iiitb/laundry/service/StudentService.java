package com.iiitb.laundry.service;

import com.iiitb.laundry.repository.StudentRepository;

public class StudentService {
	
	StudentRepository studentRepository=new StudentRepository();
	
	public String fetchNameByMobileNo(long mobileNo) throws Exception {
		return studentRepository.findByMobileNumber(mobileNo).getFirstName();
	}
	
}
