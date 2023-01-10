package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.matchers.HeaderNames.CC;

import javax.mail.Message;

import org.hamcrest.Matcher;

/**
 * <p>A matcher for the "Cc" header.</p>
 * 
 * @see Message
 * 
 * @author devopsix
 *
 */
public class MessageHasCc extends AbstractStringHeaderMatcher<Message> {
    
    public MessageHasCc(Matcher<String> matcher) {
        super(CC, matcher);
    }
}
