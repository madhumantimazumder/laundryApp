package madhumanti.laundry;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class ConnectionTwilio {
    public static final String ACCOUNT_SID =
            "ACee972defedc372599aa803c9d6859f50";
    public static final String AUTH_TOKEN =
            "39e6369bfaae48377ea7d72cec9cef3e";

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber("whatsapp:+919433591033"), // to
                        new PhoneNumber("whatsapp:+14155238886"), // from
                        "Where's Wallace?")
                .create();

        System.out.println(message.getSid());
    }
}
