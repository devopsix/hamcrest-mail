package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.util.HeaderNames.TO;

import javax.mail.Message;

import org.hamcrest.Matcher;

/**
 * A matcher for the "To" header.
 * 
 * @author devopsix
 *
 */
public class MessageHasTo extends AbstractStringHeaderMatcher<Message> {
    
    public MessageHasTo(Matcher<String> matcher) {
        super(TO, matcher);
    }
}
