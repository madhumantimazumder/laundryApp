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
        String messageToSpend  = request.get("Body").get(0).toString().toLowerCase();
        Body body;
        if ("hello".equals(messageToSpend))
        {
            body = new Body
                    .Builder("Say \"Book\" to book a slot\nSay \"Cancel\" to cancel your booked slot\n"
                    +"Say \"View\" to view your details")
                    .build();

        }
        else if("book".equals(messageToSpend) )
        {
            body = new Body
                    .Builder("Available slots are:\n")
                    .build();
        }
        else if("view".equals(messageToSpend) )
        {
            body = new Body
                    .Builder("Your details are given below:\n")
                    .build();
        }
        else if("cancel".equals(messageToSpend) )
        {
            body = new Body
                    .Builder("Your slot booking is cancelled.")
                    .build();
        }
        else if("exit".equals(messageToSpend) )
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
