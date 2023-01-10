package org.devopsix.hamcrest.mail.matchers;

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import javax.mail.MessagingException;
import javax.mail.Part;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.devopsix.hamcrest.mail.matchers.ArrayUtils.isEmpty;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;

/**
 * A base class for header matchers.
 * 
 * @see Part
 * 
 * @author devopsix
 *
 */
abstract class AbstractMultiHeaderMatcher<P extends Part, T> extends TypeSafeDiagnosingMatcher<P> {
    
    private final String header;
    private final Matcher<Iterable<T>> matcher;
    
    protected AbstractMultiHeaderMatcher(String header, Matcher<Iterable<T>> matcher) {
        this.header = header;
        this.matcher = matcher;
    }
    
    @Override
    public boolean matchesSafely(P part, Description mismatch) {
        return values(part, mismatch).matching(matcher);
    }
    
    @Override
    public void describeTo(Description description) {
        description.appendText(format("has %s headers which match: ", header));
        matcher.describeTo(description);
    }
    
    protected abstract Condition<Iterable<T>> values(P part, Description mismatch);
    
    protected Condition<Iterable<String>> headerValues(P part, Description mismatch) {
        String[] values;
        try {
            values = part.getHeader(header);
        } catch (MessagingException e) {
            mismatch.appendText(format("failed to extract %s headers: %s", header, e.getMessage()));
            return notMatched();
        }
        if (isEmpty(values)) {
            return matched(emptyList(), mismatch);
        }
        Iterable<String> decodedValues = stream(values).map(HeaderUtils::decodeHeader).collect(toList());
        return matched(decodedValues, mismatch);
    }
}
