package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.util.HeaderNames.SUBJECT;

import javax.mail.Message;

import org.hamcrest.Matcher;

/**
 * A matcher for the "Subject" header.
 * 
 * @author devopsix
 *
 */
public class MessageHasSubject extends AbstractStringHeaderMatcher<Message> {
    
    public MessageHasSubject(Matcher<String> matcher) {
        super(SUBJECT, matcher);
    }
}