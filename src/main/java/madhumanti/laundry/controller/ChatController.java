package madhumanti.laundry.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    @Produces(MediaType.APPLICATION_JSON)
    public String chatbot(String incoming_msg) {
		//ReceiveSMS s=new ReceiveSMS();
		Body body = new Body
                .Builder("The Robots are coming! Head for the hills!")
                .build();
        Message sms = new Message
                .Builder()
                .body(body)
                .build();
        MessagingResponse twiml = new MessagingResponse
                .Builder()
                .message(sms)
                .build();
        return twiml.toXml();
    }
}
