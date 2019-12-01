package devopsix.hamcrest.email.matchers;

import static devopsix.hamcrest.email.util.ArrayUtils.isEmpty;
import static devopsix.hamcrest.email.util.HeaderUtils.decodeHeader;
import static java.lang.String.format;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * A base class for header matchers.
 * 
 * @author devopsix
 *
 */
abstract class AbstractMessageHeaderMatcher<T>  extends TypeSafeDiagnosingMatcher<Message> {
    
    private final String header;
    private final Matcher<T> matcher;
    
    protected AbstractMessageHeaderMatcher(String header, Matcher<T> matcher) {
        this.header = header;
        this.matcher = matcher;
    }
    
    @Override
    public boolean matchesSafely(Message message, Description mismatch) {
        return value(message, mismatch).matching(matcher);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(format("has a %s header which matches: ", header));
        matcher.describeTo(description);
    }
    
    protected abstract Condition<T> value(Message message, Description mismatch);
    
    protected Condition<String> headerValue(Message message, Description mismatch) {
        String[] values = null;
        try {
            values = message.getHeader(header);
        } catch (MessagingException e) {
            mismatch.appendText(format("failed to extract %s header: ", header) + e.getMessage());
            return notMatched();
        }
        if (isEmpty(values)) {
            return matched(null, mismatch);
        }
        if (values.length > 1) {
            mismatch.appendText(format("has more than one %s header", header));
            return notMatched();
        }
        String value = decodeHeader(values[0]);
        return matched(value, mismatch);
    }
}
