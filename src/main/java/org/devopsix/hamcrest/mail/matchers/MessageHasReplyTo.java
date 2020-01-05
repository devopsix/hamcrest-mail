package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.util.HeaderNames.REPLY_TO;

import javax.mail.Message;

import org.hamcrest.Matcher;

/**
 * <p>A matcher for the "Reply-To" header.</p>
 * 
 * @see Message
 * 
 * @author devopsix
 *
 */
public class MessageHasReplyTo extends AbstractStringHeaderMatcher<Message> {
    
    public MessageHasReplyTo(Matcher<String> matcher) {
        super(REPLY_TO, matcher);
    }
}
