package com.iiitb.laundry.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.iiitb.laundry.beans.LaundryBooking;
import com.iiitb.laundry.service.BookingService;
import com.iiitb.laundry.service.StudentService;
import com.iiitb.laundry.utils.DBUtils;
import com.iiitb.laundry.utils.MessageConstants;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;

@Path("/api")
public class ChatController {
	
	@GET  
	@Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
		DBUtils.getSession();
		LocalTime time=LocalTime.now();
		LocalTime dbTime=LocalTime.parse("02:44");
		LocalDate date=LocalDate.now();
		System.out.println(time.toString()+" "+dbTime.toString());
		System.out.println(time.compareTo(dbTime));
        return "working...";
    }
	
	@POST 
    @Path("/bot")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_XML)
    public String chatbot(MultivaluedMap<String,String> request) {
        String receivedMsg  = request.get("Body").get(0).toString().toLowerCase();
        String mobileNo=request.get("From").get(0).toString().toLowerCase().substring(12);//ignore whatsapp:+91
        Body body=null;
        String reply=null;
        BookingService bookingService=null;
        if ("hello".equalsIgnoreCase(receivedMsg))
        {
        	try {
				String studentName=new StudentService().fetchNameByMobileNo(Long.parseLong(mobileNo));
				reply=MessageConstants.HELLO_MSG+" "+studentName+"!\n\n"+MessageConstants.INFO_MSG+"\n"+MessageConstants.MENU_MSG;
				body = new Body.Builder(reply).build();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }
        else if("book".equalsIgnoreCase(receivedMsg) )
        {
        	String availableSlots;
			try {
				availableSlots = new BookingService().fetchAvailableSlot(Long.parseLong(mobileNo));
				reply = availableSlots.trim().isEmpty() ? MessageConstants.NO_SLOTS_AVAILABLE_MSG + MessageConstants.END_OF_MSG
						: availableSlots + "\n" + MessageConstants.AVAILABLE_SLOTS_TAIL_MSG + "\n\n"
								+ MessageConstants.END_OF_MSG;
	            body = new Body.Builder(reply).build();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else if(receivedMsg.startsWith("s"))//slot no for booking will come as s1,s2 etc
        {
        	bookingService=new BookingService();
        	try {
        		if(bookingService.fetchBookedSlot(Long.parseLong(mobileNo))!=null) {
        			reply=MessageConstants.NACK_FOR_NO_MORE_BOOKING_FOR_TODAY_MSG+"\n\n"+MessageConstants.END_OF_MSG;
        		}else {
        			int slotNo=Integer.parseInt(receivedMsg.substring(1));
    				bookingService.bookSlot(slotNo, Long.parseLong(mobileNo));
    				reply=MessageConstants.ACK_FOR_CONFIRMED_BOOKING_MSG+receivedMsg+"\n\n"+MessageConstants.END_OF_MSG;
        		}
			} catch (NumberFormatException e) {
				reply=MessageConstants.NACK_FOR_WRONG_BOOKING_INPUT_MSG+"\n\n"+MessageConstants.END_OF_MSG;
			} catch(Exception e) { //can implement a custom exception if time permits
				reply=MessageConstants.NACK_FOR_ALREADY_BOOKED_MSG+"\n\n"+MessageConstants.END_OF_MSG;
			}
        	body = new Body.Builder(reply).build();
        }
        else if("cancel".equals(receivedMsg) )
        {
        	try {
        		bookingService=new BookingService();
        		if(bookingService.fetchBookedSlot(Long.parseLong(mobileNo))==null) throw new Exception();
				new BookingService().cancelSlot(Long.parseLong(mobileNo));
				reply=MessageConstants.ACK_FOR_CONFIRMED_CANCELLATION_MSG+"\n\n"+MessageConstants.END_OF_MSG;
			} catch (Exception e) {
				reply=MessageConstants.NACK_FOR_CANCELLATION_MSG+"\n\n"+MessageConstants.END_OF_MSG;
			}
        	body = new Body.Builder(reply).build();
        }
        else if("view".equalsIgnoreCase(receivedMsg)) 
        {
        	try {
        		bookingService=new BookingService();
				LaundryBooking laundryBooking=bookingService.fetchBookedSlot(Long.parseLong(mobileNo));
				if(laundryBooking==null) throw new Exception();
				String slotSchedule=laundryBooking.getSlot().getStartTime()+"-"+laundryBooking.getSlot().getEndTime();
				SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
				String bookingDate=dateFormatter.format(laundryBooking.getBookingDate());
				reply=MessageConstants.ACK_FOR_VIEW_MSG+slotSchedule+"("+bookingDate+")"+"\n\n"+MessageConstants.END_OF_MSG;
			} catch (Exception e) {
				reply=MessageConstants.NACK_FOR_VIEW_MSG+"\n\n"+MessageConstants.END_OF_MSG;
			}
        	body = new Body.Builder(reply).build();
        }
        else if("menu".equalsIgnoreCase(receivedMsg))
        {
			reply = MessageConstants.INFO_MSG + "\n" + MessageConstants.MENU_MSG;
			body = new Body.Builder(reply).build();
        }
        Message sms = new Message
                .Builder()
                .body(body)
                .build();
        MessagingResponse twiml = new MessagingResponse
                .Builder()
                .message(sms)
                .build();
        System.out.println("message "+twiml.toXml());
        return twiml.toXml();
    }
}
