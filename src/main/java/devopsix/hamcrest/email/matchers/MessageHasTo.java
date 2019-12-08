package devopsix.hamcrest.email.matchers;

import static devopsix.hamcrest.email.util.HeaderNames.TO;

import javax.mail.Message;

import org.hamcrest.Matcher;

/**
 * A matcher for the "To" header.
 * 
 * @author devopsix
 *
 */
public class MessageHasTo extends AbstractStringHeaderMatcher<Message> {
    
    public MessageHasTo(Matcher<String> matcher) {
        super(TO, matcher);
    }
}
