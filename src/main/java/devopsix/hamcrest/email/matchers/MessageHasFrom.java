package devopsix.hamcrest.email.matchers;

import static devopsix.hamcrest.email.util.HeaderNames.FROM;

import org.hamcrest.Matcher;

/**
 * A matcher for the "From" header.
 * 
 * @author devopsix
 *
 */
public class MessageHasFrom extends AbstractMessageStringHeaderMatcher {
    
    public MessageHasFrom(Matcher<String> matcher) {
        super(FROM, matcher);
    }
}
