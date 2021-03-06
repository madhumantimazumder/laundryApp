package com.iiitb.laundry.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.iiitb.laundry.service.BookingDataService;
import org.json.simple.parser.JSONParser;

import java.lang.reflect.Array;
import java.util.List;

@Path("/api/bookings")
public class BookingDataController {

    BookingDataService bookingDataService=new BookingDataService();

    @GET  @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "working...";
    }

    @POST
    @Path("/getdata")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getBookingData(String dateJson) throws Exception {
        JSONObject jObj = (JSONObject)new JSONParser().parse(dateJson);
        String startDate = (String) jObj.get("startDate");
        String endDate = (String) jObj.get("endDate");
        String bookingData= bookingDataService.fetchBookingData(startDate,endDate);
        return bookingData;
    }
}
