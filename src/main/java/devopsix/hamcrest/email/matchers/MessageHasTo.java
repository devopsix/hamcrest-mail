package devopsix.hamcrest.email.matchers;

import static devopsix.hamcrest.email.util.HeaderNames.TO;

import org.hamcrest.Matcher;

/**
 * A matcher for the "To" header.
 * 
 * @author devopsix
 *
 */
public class MessageHasTo extends AbstractMessageStringHeaderMatcher {
    
    public MessageHasTo(Matcher<String> matcher) {
        super(TO, matcher);
    }
}
