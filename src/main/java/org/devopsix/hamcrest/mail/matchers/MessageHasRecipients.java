package org.devopsix.hamcrest.mail.matchers;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * <p>A matcher for recipients (To, Cc, Bcc) of a message.</p>
 * 
 * @see Message
 * 
 * @author devopsix
 *
 */
public class MessageHasRecipients extends TypeSafeDiagnosingMatcher<Message> {
    
    private final RecipientType type;
    private final Matcher<Iterable<Address>> matcher;
    
    public MessageHasRecipients(Matcher<Iterable<Address>> matcher) {
        this.type = null;
        this.matcher = matcher;
    }
    
    public MessageHasRecipients(RecipientType type, Matcher<Iterable<Address>> matcher) {
        this.type = type;
        this.matcher = matcher;
    }
    
    @Override
    public boolean matchesSafely(Message message, Description mismatch) {
        return recipients(message, mismatch).matching(matcher);
    }

    @Override
    public void describeTo(Description description) {
        if (allRecipientTypes()) {
            description.appendText("has recipients which match: ");
        } else {
            description.appendText(format("has %s recipients which match: ", type));
        }
        matcher.describeTo(description);
    }
    
    protected Condition<Iterable<Address>> recipients(Message message, Description mismatch) {
        Address[] recipients = null;
        try {
            if (allRecipientTypes()) {
                recipients = message.getAllRecipients();
            } else {
                recipients = message.getRecipients(type);
            }
        } catch (MessagingException e) {
            mismatch.appendText(format("failed to extract recipients: %s", e.getMessage()));
            return notMatched();
        }
        return matched(isNull(recipients) ? emptyList() : asList(recipients), mismatch);
    }
    
    private boolean allRecipientTypes() {
        return isNull(type);
    }
}
