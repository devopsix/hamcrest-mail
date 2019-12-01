package devopsix.hamcrest.email.matchers;

import static devopsix.hamcrest.email.util.HeaderNames.CC;

import org.hamcrest.Matcher;

/**
 * A matcher for the "Cc" header.
 * 
 * @author devopsix
 *
 */
public class MessageHasCc extends AbstractMessageStringHeaderMatcher {
    
    public MessageHasCc(Matcher<String> matcher) {
        super(CC, matcher);
    }
}
