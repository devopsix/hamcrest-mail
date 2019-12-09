package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.util.HeaderNames.REPLY_TO;

import javax.mail.Message;

import org.hamcrest.Matcher;

/**
 * A matcher for the "Reply-To" header.
 * 
 * @author devopsix
 *
 */
public class MessageHasReplyTo extends AbstractStringHeaderMatcher<Message> {
    
    public MessageHasReplyTo(Matcher<String> matcher) {
        super(REPLY_TO, matcher);
    }
}
