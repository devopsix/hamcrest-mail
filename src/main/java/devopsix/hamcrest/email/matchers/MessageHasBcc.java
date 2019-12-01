package devopsix.hamcrest.email.matchers;

import static devopsix.hamcrest.email.util.HeaderNames.BCC;

import org.hamcrest.Matcher;

/**
 * A matcher for the "Bcc" header.
 * 
 * @author devopsix
 *
 */
public class MessageHasBcc extends AbstractMessageStringHeaderMatcher {
    
    public MessageHasBcc(Matcher<String> matcher) {
        super(BCC, matcher);
    }
}
