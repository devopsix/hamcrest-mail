package org.devopsix.hamcrest.mail.matchers;

import static java.lang.String.format;
import static org.devopsix.hamcrest.mail.util.ArrayUtils.isEmpty;
import static org.devopsix.hamcrest.mail.util.HeaderUtils.decodeHeader;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;

import javax.mail.MessagingException;
import javax.mail.Part;

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
abstract class AbstractHeaderMatcher<P extends Part,T>  extends TypeSafeDiagnosingMatcher<P> {
    
    private final String header;
    private final Matcher<T> matcher;
    
    protected AbstractHeaderMatcher(String header, Matcher<T> matcher) {
        this.header = header;
        this.matcher = matcher;
    }
    
    @Override
    public boolean matchesSafely(P part, Description mismatch) {
        return value(part, mismatch).matching(matcher);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(format("has a %s header which matches: ", header));
        matcher.describeTo(description);
    }
    
    protected abstract Condition<T> value(P part, Description mismatch);
    
    protected Condition<String> headerValue(P part, Description mismatch) {
        String[] values = null;
        try {
            values = part.getHeader(header);
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
