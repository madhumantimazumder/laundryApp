package com.iiitb.laundry.utils;

public final class MessageConstants {
	
	private static final String FIRST_ITEM="Reply \"Book\" to book a laundry slot\n";
	private static final String SECOND_ITEM="Reply \"View\" to view booked slot\n";
	private static final String THIRD_ITEM="Reply \"Cancel\" to cancel your booked slot\n";
	
	private static final String FAIL_BOOKING_MSG="*Failed to book the slot*\n\n";
	public static final String HELLO_MSG="Hello";
	public static final String WELCOME_MSG="Welcome to IIITB-Laundry service";
	public static final String INFO_MSG="*What service would you like to avail today?*";
	public static final String MENU_MSG=FIRST_ITEM+SECOND_ITEM+THIRD_ITEM;
	public static final String AVAILABLE_SLOTS_HEAD_MSG="Available Laundry Slots for ";
	public static final String AVAILABLE_SLOTS_TAIL_MSG="Choose any slot from the above to book.\nReply with the corresponding slot no for booking";
	public static final String NO_SLOTS_AVAILABLE_MSG="No laundry slots available for current booking date.\nSorry for the inconvenience caused.\n\n";
	public static final String END_OF_MSG="Reply \"Menu\" to go back to menu";
	public static final String ACK_FOR_CONFIRMED_BOOKING_MSG="Booking done successfully for slot ";
	public static final String NACK_FOR_WRONG_BOOKING_INPUT_MSG=FAIL_BOOKING_MSG+"Incorrect input format for slot no.Please try again with correct slot no";
	public static final String NACK_FOR_ALREADY_BOOKED_MSG=FAIL_BOOKING_MSG+"This slot is not available anymore. Sorry for the inconvenience caused.";
	public static final String NACK_FOR_NO_MORE_BOOKING_FOR_TODAY_MSG=FAIL_BOOKING_MSG+"You already have a booking. Only 1 booking per person is allowed";
	public static final String ACK_FOR_CONFIRMED_CANCELLATION_MSG="Booking has been cancelled successfully";
	public static final String NACK_FOR_CANCELLATION_MSG="No current booking to be cancelled.";
	public static final String ACK_FOR_VIEW_MSG="You have a booking for ";
	public static final String NACK_FOR_VIEW_MSG="You have no booking as of now.";
}