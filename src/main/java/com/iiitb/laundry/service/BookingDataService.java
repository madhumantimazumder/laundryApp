package com.iiitb.laundry.service;

import com.iiitb.laundry.beans.*;
import com.iiitb.laundry.repository.LaundryBookingRepository;
import com.iiitb.laundry.repository.LaundrySlotRepository;
import com.iiitb.laundry.repository.StudentRepository;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public  class BookingDataService {
    List<JSONObject> bookingData=new ArrayList<>();
    StudentRepository studentRepository=new StudentRepository();
    LaundryBookingRepository laundryBookingRepository=new LaundryBookingRepository();
    public String fetchBookingData(String start,String end) throws Exception {
        List<LaundryBooking> totalBooking=laundryBookingRepository.fetchBookedSlots(start,end);
        for(LaundryBooking lb : totalBooking){
            JSONObject jo = new JSONObject();
            Student student=lb.getStudent();
            LaundrySlot slot=lb.getSlot();
            jo.put("card", student.getLaundryCardNo());
            jo.put("date",lb.getBookingDate().toString());
            jo.put("slotStart",slot.getStartTime());
            jo.put("slotEnd",slot.getEndTime());
            bookingData.add(jo);
        }
        JSONObject totalData = new JSONObject();
        totalData.put("dataList",bookingData);
        //message when slotNames is Empty
        return totalData.toJSONString();
    }
}
