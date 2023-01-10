package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.matchers.HeaderNames.FROM;

import javax.mail.Message;

import org.hamcrest.Matcher;

/**
 * <p>A matcher for the "From" header.</p>
 * 
 * @see Message
 * 
 * @author devopsix
 *
 */
public class MessageHasFrom extends AbstractStringHeaderMatcher<Message> {
    
    public MessageHasFrom(Matcher<String> matcher) {
        super(FROM, matcher);
    }
}
