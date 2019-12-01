package devopsix.hamcrest.email.matchers;

import static devopsix.hamcrest.email.util.HeaderNames.DATE;

import java.time.OffsetDateTime;

import org.hamcrest.Matcher;

/**
 * A matcher for the "Date" header which accepts {@code OffsetDateTime} matchers for the value.
 * 
 * @author devopsix
 *
 */
public class MessageHasDate extends AbstractMessageDateHeaderMatcher {
    
    public MessageHasDate(Matcher<OffsetDateTime> matcher) {
        super(DATE, matcher);
    }
}
