package devopsix.hamcrest.email.matchers;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;
import static org.hamcrest.Matchers.anything;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * A matcher for multipart bodies.
 * 
 * @author devopsix
 *
 */
public class MessageHasMultipartBody extends TypeSafeDiagnosingMatcher<Message> {
    
    public MessageHasMultipartBody() {
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected boolean matchesSafely(Message message, Description mismatch) {
        return body(message, mismatch).matching((Matcher)anything());
    }
    
    private Condition<Multipart> body(Message message, Description mismatch) {
        Object content;
        try {
            content = message.getContent();
        } catch (IOException | MessagingException e) {
            mismatch.appendText(format("failed to extract body: ", e.getMessage()));
            return notMatched();
        }
        if (content instanceof Multipart) {
            return matched((Multipart)content, mismatch);
        } else {
            mismatch.appendText(format("not a multipart body: %s", isNull(content) ? null : content.getClass().getSimpleName()));
            return notMatched();
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has multipart body");
    }
}
