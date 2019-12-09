package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.util.HeaderNames.DATE;

import java.time.OffsetDateTime;

import javax.mail.Message;

import org.hamcrest.Matcher;

/**
 * A matcher for the "Date" header which accepts {@code OffsetDateTime} matchers for the value.
 * 
 * @author devopsix
 *
 */
public class MessageHasDate extends AbstractDateHeaderMatcher<Message> {
    
    public MessageHasDate(Matcher<OffsetDateTime> matcher) {
        super(DATE, matcher);
    }
}
