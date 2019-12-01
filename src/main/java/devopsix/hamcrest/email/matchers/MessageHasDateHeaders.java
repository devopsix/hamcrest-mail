package devopsix.hamcrest.email.matchers;

import java.time.OffsetDateTime;

import org.hamcrest.Matcher;

/**
 * A matcher for named date headers.
 * 
 * @author devopsix
 *
 */
public class MessageHasDateHeaders extends AbstractMessageDateMultiHeaderMatcher {
    
    public MessageHasDateHeaders(String header, Matcher<Iterable<OffsetDateTime>> matcher) {
        super(header, matcher);
    }
}
