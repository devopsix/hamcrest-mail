package devopsix.hamcrest.email.matchers;

import javax.mail.Message;

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

class AbstractMessageStringHeaderMatcher extends AbstractMessageHeaderMatcher<String> {

    protected AbstractMessageStringHeaderMatcher(String header, Matcher<String> matcher) {
        super(header, matcher);
    }

    @Override
    protected Condition<String> value(Message message, Description mismatch) {
        return headerValue(message, mismatch);
    }
}
