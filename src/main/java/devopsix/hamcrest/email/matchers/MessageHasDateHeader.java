package devopsix.hamcrest.email.matchers;

import java.time.OffsetDateTime;

import org.hamcrest.Matcher;

/**
 * A matcher for a named date header.
 * 
 * @author devopsix
 *
 */
public class MessageHasDateHeader extends AbstractMessageDateHeaderMatcher {
    
    public MessageHasDateHeader(String header, Matcher<OffsetDateTime> matcher) {
        super(header, matcher);
    }
}
