package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.util.HeaderNames.CC;

import javax.mail.Message;

import org.hamcrest.Matcher;

/**
 * A matcher for the "Cc" header.
 * 
 * @author devopsix
 *
 */
public class MessageHasCc extends AbstractStringHeaderMatcher<Message> {
    
    public MessageHasCc(Matcher<String> matcher) {
        super(CC, matcher);
    }
}
