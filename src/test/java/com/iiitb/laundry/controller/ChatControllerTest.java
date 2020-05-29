package com.iiitb.laundry.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.iiitb.laundry.beans.LaundryBooking;
import com.iiitb.laundry.beans.LaundrySlot;
import com.iiitb.laundry.service.BookingService;
import com.iiitb.laundry.service.StudentService;
import com.iiitb.laundry.utils.MessageConstants;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;

public class ChatControllerTest {
	
	private static final MultivaluedMap<String, String> request;
	
	static {
		request=new MultivaluedHashMap<String, String>();
		List<String> fromList=new ArrayList<String>();
		fromList.add("whatsapp:+919038178722");
		request.put("From",fromList);
		request.put("Body",new ArrayList<String>());
	}
	
	@InjectMocks
	ChatController chatController;
	
	@Mock
	StudentService studentService;
	
	@Mock
	BookingService bookingService;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	private String prepareTwilioMsg(String body) {
		Message sms = new Message.Builder().body(new Body.Builder(body).build()).build();
		return new MessagingResponse.Builder().message(sms).build().toXml();
	}
	
	@Test
	public void testChatBotForHello() throws Exception{
		List<String> bodyList=new ArrayList<String>();
		bodyList.add("hello");
		request.put("Body", bodyList);
		when(studentService.fetchNameByMobileNo(Mockito.anyLong())).thenReturn("Utsav");
		String expected = prepareTwilioMsg(MessageConstants.HELLO_MSG + " " + "Utsav" + "!\n\n" + MessageConstants.INFO_MSG + "\n"
				+ MessageConstants.MENU_MSG);
		String actual=chatController.chatbot(request);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testChatBotForWhenBookingAvailable() throws Exception{
		List<String> bodyList=new ArrayList<String>();
		bodyList.add("book");
		request.put("Body", bodyList);
		String availableSlots="Available Laundry Slots for *2020-05-29*\n" + "\n" + "S19) 18:00-19:00\n" + "S20) 19:00-20:00";
		when(bookingService.fetchAvailableSlot(Mockito.anyLong())).thenReturn(availableSlots);
		String expected = prepareTwilioMsg(availableSlots + "\n" + MessageConstants.AVAILABLE_SLOTS_TAIL_MSG + "\n\n"
				+ MessageConstants.END_OF_MSG);
		
		String actual=chatController.chatbot(request);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testChatBotForWhenBookingNotAvailable() throws Exception{
		List<String> bodyList=new ArrayList<String>();
		bodyList.add("book");
		request.put("Body", bodyList);
		String availableSlots="";//representing empty slots
		when(bookingService.fetchAvailableSlot(Mockito.anyLong())).thenReturn(availableSlots);
		String expected = prepareTwilioMsg(MessageConstants.NO_SLOTS_AVAILABLE_MSG + MessageConstants.END_OF_MSG);
		
		String actual=chatController.chatbot(request);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testChatBotForBookingSlotWithSlotNo() throws Exception{
		List<String> bodyList=new ArrayList<String>();
		bodyList.add("S19");
		request.put("Body", bodyList);
		when(bookingService.fetchBookedSlot(Mockito.anyLong())).thenReturn(null);
		doNothing().when(bookingService).bookSlot(Mockito.anyInt(), Mockito.anyLong());
		String expected = prepareTwilioMsg(MessageConstants.ACK_FOR_CONFIRMED_BOOKING_MSG+"S19"+"\n\n"+MessageConstants.END_OF_MSG);
		String actual=chatController.chatbot(request);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testChatBotForBookingSlotWhenAlreadyBooked() throws Exception{
		List<String> bodyList=new ArrayList<String>();
		bodyList.add("S19");
		request.put("Body", bodyList);
		when(bookingService.fetchBookedSlot(Mockito.anyLong())).thenReturn(new LaundryBooking());
		doNothing().when(bookingService).bookSlot(Mockito.anyInt(), Mockito.anyLong());
		String expected = prepareTwilioMsg(MessageConstants.NACK_FOR_NO_MORE_BOOKING_FOR_TODAY_MSG+"\n\n"+MessageConstants.END_OF_MSG);
		String actual=chatController.chatbot(request);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testChatBotForCancellingBooking() throws Exception{
		List<String> bodyList=new ArrayList<String>();
		bodyList.add("cancel");
		request.put("Body", bodyList);
		when(bookingService.fetchBookedSlot(Mockito.anyLong())).thenReturn(new LaundryBooking());
		doNothing().when(bookingService).cancelSlot(Mockito.anyLong());
		String expected = prepareTwilioMsg(MessageConstants.ACK_FOR_CONFIRMED_CANCELLATION_MSG+"\n\n"+MessageConstants.END_OF_MSG);
		String actual=chatController.chatbot(request);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testChatBotForCancellingBookingWhenNoBookingAvailable() throws Exception{
		List<String> bodyList=new ArrayList<String>();
		bodyList.add("cancel");
		request.put("Body", bodyList);
		when(bookingService.fetchBookedSlot(Mockito.anyLong())).thenReturn(null);
		doNothing().when(bookingService).cancelSlot(Mockito.anyLong());
		String expected = prepareTwilioMsg(MessageConstants.NACK_FOR_CANCELLATION_MSG+"\n\n"+MessageConstants.END_OF_MSG);
		String actual=chatController.chatbot(request);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testChatBotForViewingBooking() throws Exception{
		List<String> bodyList=new ArrayList<String>();
		bodyList.add("view");
		request.put("Body", bodyList);
		LaundryBooking testLaundryBooking=new LaundryBooking();
		LaundrySlot testSlot=new LaundrySlot();
		testSlot.setStartTime("18:00");
		testSlot.setEndTime("19:00");
		testLaundryBooking.setBookingDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-02"));
		testLaundryBooking.setSlot(testSlot);
		when(bookingService.fetchBookedSlot(Mockito.anyLong())).thenReturn(testLaundryBooking);
		String expected = prepareTwilioMsg(MessageConstants.ACK_FOR_VIEW_MSG+"18:00-19:00"+"("+"2020-05-02"+")"+"\n\n"+MessageConstants.END_OF_MSG);
		String actual=chatController.chatbot(request);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testChatBotForViewingBookingWhenNoBookingAvailable() throws Exception{
		List<String> bodyList=new ArrayList<String>();
		bodyList.add("view");
		request.put("Body", bodyList);
		when(bookingService.fetchBookedSlot(Mockito.anyLong())).thenReturn(null);
		String expected = prepareTwilioMsg(MessageConstants.NACK_FOR_VIEW_MSG+"\n\n"+MessageConstants.END_OF_MSG);
		String actual=chatController.chatbot(request);
		assertEquals(expected,actual);
	}
}
