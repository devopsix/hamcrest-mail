package org.devopsix.hamcrest.mail.matchers;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * A matcher for multipart content.
 * 
 * @author devopsix
 *
 */
public class PartHasMultipartContent extends TypeSafeDiagnosingMatcher<Part> {
    
    final Matcher<Multipart> matcher;

    public PartHasMultipartContent(Matcher<Multipart> matcher) {
        this.matcher = matcher;
    }

    @Override
    protected boolean matchesSafely(Part message, Description mismatch) {
        return body(message, mismatch).matching(matcher);
    }
    
    private Condition<Multipart> body(Part message, Description mismatch) {
        try {
            Object content = message.getContent();
            if (content instanceof Multipart) {
                return matched((Multipart)content, mismatch);
            } else {
                mismatch.appendText(format("not multipart content: %s", isNull(content) ? null : content.getClass().getSimpleName()));
                return notMatched();
            }
        } catch (IOException | MessagingException e) {
            mismatch.appendText(format("failed to extract content: ", e.getMessage()));
            return notMatched();
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has multipart content");
    }
}
