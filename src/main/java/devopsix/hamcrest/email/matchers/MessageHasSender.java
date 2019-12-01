package devopsix.hamcrest.email.matchers;

import static devopsix.hamcrest.email.util.HeaderNames.SENDER;

import org.hamcrest.Matcher;

/**
 * A matcher for the "Sender" header.
 * 
 * @author devopsix
 *
 */
public class MessageHasSender extends AbstractMessageStringHeaderMatcher {
    
    public MessageHasSender(Matcher<String> matcher) {
        super(SENDER, matcher);
    }
}
