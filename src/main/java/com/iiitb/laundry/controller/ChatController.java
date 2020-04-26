package com.iiitb.laundry.controller;

import java.time.LocalTime;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

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
        String mobileNo="9038178722";//update this from received msg
        Body body=null;
        String reply=null;
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
				reply=availableSlots+"\n"+MessageConstants.AVAILABLE_SLOTS_MSG+"\n\n"+MessageConstants.END_OF_MSG;
	            body = new Body.Builder(reply).build();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else if("view".equals(receivedMsg) )
        {
            body = new Body
                    .Builder("Your details are given below:\n")
                    .build();
        }
        else if("cancel".equals(receivedMsg) )
        {
            body = new Body
                    .Builder("Your slot booking is cancelled.")
                    .build();
        }
        else if("exit".equals(receivedMsg) )
        {
            body = new Body
                    .Builder("Thanks you!\nSay \"hello\" to start again.")
                    .build();
        }
        else
        {
            body = new Body
                    .Builder("Say \"Hello\" to start again.")
                    .build();
        }
		/*Body body = new Body
                .Builder(incoming_message)
                .build();*/
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
