package madhumanti.laundry.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;

import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;

@Path("/api")
public class ChatController {
	@GET  @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "working...";
    }
	
	@POST 
    @Path("/bot")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_XML)
    public String chatbot(MultivaluedMap<String,String> request) {
	    String incoming_message =request.get("Body").get(0).toString();
	    System.out.println("request "+incoming_message);
		//request=request.split("&")[4];
		//String incoming_message=request.split("=")[1];
		Body body = new Body
                .Builder(incoming_message)
                .build();
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
