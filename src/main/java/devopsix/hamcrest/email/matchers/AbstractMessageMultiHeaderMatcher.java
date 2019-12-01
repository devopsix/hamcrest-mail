package devopsix.hamcrest.email.matchers;

import static devopsix.hamcrest.email.util.ArrayUtils.isEmpty;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import devopsix.hamcrest.email.util.HeaderUtils;

/**
 * A base class for header matchers.
 * 
 * @author devopsix
 *
 */
abstract class AbstractMessageMultiHeaderMatcher<T> extends TypeSafeDiagnosingMatcher<Message> {
    
    private final String header;
    private final Matcher<Iterable<T>> matcher;
    
    protected AbstractMessageMultiHeaderMatcher(String header, Matcher<Iterable<T>> matcher) {
        this.header = header;
        this.matcher = matcher;
    }
    
    @Override
    public boolean matchesSafely(Message message, Description mismatch) {
        return values(message, mismatch).matching(matcher);
    }
    
    @Override
    public void describeTo(Description description) {
        description.appendText(format("has %s headers which match: ", header));
        matcher.describeTo(description);
    }
    
    protected abstract Condition<Iterable<T>> values(Message message, Description mismatch);
    
    protected Condition<Iterable<String>> headerValues(Message message, Description mismatch) {
        String[] values = null;
        try {
            values = message.getHeader(header);
        } catch (MessagingException e) {
            mismatch.appendText(format("failed to extract %s headers: ", header) + e.getMessage());
            return notMatched();
        }
        if (isEmpty(values)) {
            return matched(emptyList(), mismatch);
        }
        Iterable<String> decodedValues = asList(values).stream().map(HeaderUtils::decodeHeader).collect(toList());
        return matched(decodedValues, mismatch);
    }
}
