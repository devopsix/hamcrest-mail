package devopsix.hamcrest.email.matchers;

import static devopsix.hamcrest.email.util.HeaderNames.SENDER;

import javax.mail.Message;

import org.hamcrest.Matcher;

/**
 * A matcher for the "Sender" header.
 * 
 * @author devopsix
 *
 */
public class MessageHasSender extends AbstractStringHeaderMatcher<Message> {
    
    public MessageHasSender(Matcher<String> matcher) {
        super(SENDER, matcher);
    }
}
