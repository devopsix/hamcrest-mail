package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.util.HeaderNames.FROM;

import javax.mail.Message;

import org.hamcrest.Matcher;

/**
 * A matcher for the "From" header.
 * 
 * @author devopsix
 *
 */
public class MessageHasFrom extends AbstractStringHeaderMatcher<Message> {
    
    public MessageHasFrom(Matcher<String> matcher) {
        super(FROM, matcher);
    }
}
