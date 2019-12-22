package org.devopsix.hamcrest.mail.matchers;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Part;

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * A matcher for plain text content.
 * 
 * @author devopsix
 *
 */
public class PartHasTextContent extends TypeSafeDiagnosingMatcher<Part> {
    
    final Matcher<String> matcher;

    public PartHasTextContent(Matcher<String> matcher) {
        this.matcher = matcher;
    }

    @Override
    protected boolean matchesSafely(Part part, Description mismatch) {
        return content(part, mismatch).matching(matcher);
    }
    
    private Condition<String> content(Part part, Description mismatch) {
        Object content;
        try {
            content = part.getContent();
        } catch (IOException | MessagingException e) {
            mismatch.appendText(format("failed to extract content: ", e.getMessage()));
            return notMatched();
        }
        if (content instanceof String) {
            return matched((String)content, mismatch);
        } else {
            mismatch.appendText(format("not text content: %s", isNull(content) ? null : content.getClass().getSimpleName()));
            return notMatched();
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has text conent which matches: ");
        matcher.describeTo(description);
    }
}
