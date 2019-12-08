package devopsix.hamcrest.email.matchers;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * A matcher for plain text bodies.
 * 
 * @author devopsix
 *
 */
public class MessageHasTextBody extends TypeSafeDiagnosingMatcher<Message> {
    
    final Matcher<String> bodyMatcher;

    public MessageHasTextBody(Matcher<String> bodyMatcher) {
        this.bodyMatcher = bodyMatcher;
    }

    @Override
    protected boolean matchesSafely(Message message, Description mismatch) {
        return body(message, mismatch).matching(bodyMatcher);
    }
    
    private Condition<String> body(Message message, Description mismatch) {
        Object content;
        try {
            content = message.getContent();
        } catch (IOException | MessagingException e) {
            mismatch.appendText(format("failed to extract body: ", e.getMessage()));
            return notMatched();
        }
        if (isNull(content)) {
            return matched(null, mismatch);
        } else if (content instanceof String) {
            return matched((String)content, mismatch);
        } else {
            mismatch.appendText(format("not a text body: %s", content.getClass().getSimpleName()));
            return notMatched();
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has a body which matches: ");
        bodyMatcher.describeTo(description);
    }
}
