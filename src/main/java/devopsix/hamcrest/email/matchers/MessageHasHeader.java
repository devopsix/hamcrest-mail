package devopsix.hamcrest.email.matchers;

import org.hamcrest.Matcher;

/**
 * A matcher for the a named header.
 * 
 * @author devopsix
 *
 */
public class MessageHasHeader extends AbstractMessageStringHeaderMatcher {
    
    public MessageHasHeader(String header, Matcher<String> matcher) {
        super(header, matcher);
    }
}
