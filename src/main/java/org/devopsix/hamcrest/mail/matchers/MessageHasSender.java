package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.util.HeaderNames.SENDER;

import javax.mail.Message;

import org.hamcrest.Matcher;

/**
 * A matcher for the "Sender" header.
 * 
 * @author devopsix
 *
 */
public class MessageHasSender extends AbstractStringHeaderMatcher<Message> {
    
    public MessageHasSender(Matcher<String> matcher) {
        super(SENDER, matcher);
    }
}
