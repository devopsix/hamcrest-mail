package devopsix.hamcrest.email.matchers;

import static devopsix.hamcrest.email.util.HeaderNames.REPLY_TO;

import org.hamcrest.Matcher;

/**
 * A matcher for the "Reply-To" header.
 * 
 * @author devopsix
 *
 */
public class MessageHasReplyTo extends AbstractMessageStringHeaderMatcher {
    
    public MessageHasReplyTo(Matcher<String> matcher) {
        super(REPLY_TO, matcher);
    }
}
