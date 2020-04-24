package madhumanti.laundry;


import static spark.Spark.post;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;

public class ReceiveSMS {
	public static final String acc_sid = "ACee972defedc372599aa803c9d6859f50" ;
	public static final String auth_tok = "39e6369bfaae48377ea7d72cec9cef3e" ;
	public static void main(String[] args) {

		//get("/", (req, res) -> "Hello Web");

        post("/sms", (req, res) -> {
            res.type("application/xml");
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
        });
		
	}

}