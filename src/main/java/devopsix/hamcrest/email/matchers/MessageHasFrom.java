package devopsix.hamcrest.email.matchers;

import static devopsix.hamcrest.email.util.HeaderNames.FROM;

import javax.mail.Message;

import org.hamcrest.Matcher;

/**
 * A matcher for the "From" header.
 * 
 * @author devopsix
 *
 */
public class MessageHasFrom extends AbstractStringHeaderMatcher<Message> {
    
    public MessageHasFrom(Matcher<String> matcher) {
        super(FROM, matcher);
    }
}
