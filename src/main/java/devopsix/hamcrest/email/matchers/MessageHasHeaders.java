package devopsix.hamcrest.email.matchers;

import org.hamcrest.Matcher;

/**
 * A matcher for a named header which may be present multiple times.
 * 
 * @author devopsix
 *
 */
public class MessageHasHeaders extends AbstractMessageStringMultiHeaderMatcher {
    
    public MessageHasHeaders(String header, Matcher<Iterable<String>> matcher) {
        super(header, matcher);
    }
}
