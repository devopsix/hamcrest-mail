package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.util.HeaderNames.BCC;

import javax.mail.Message;

import org.hamcrest.Matcher;

/**
 * A matcher for the "Bcc" header.
 * 
 * @author devopsix
 *
 */
public class MessageHasBcc extends AbstractStringHeaderMatcher<Message> {
    
    public MessageHasBcc(Matcher<String> matcher) {
        super(BCC, matcher);
    }
}
