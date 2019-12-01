package devopsix.hamcrest.email.matchers;

import static devopsix.hamcrest.email.util.HeaderNames.SUBJECT;

import org.hamcrest.Matcher;

/**
 * A matcher for the "Subject" header.
 * 
 * @author devopsix
 *
 */
public class MessageHasSubject extends AbstractMessageStringHeaderMatcher {
    
    public MessageHasSubject(Matcher<String> matcher) {
        super(SUBJECT, matcher);
    }
}