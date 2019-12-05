package devopsix.hamcrest.email.matchers;

import javax.mail.Message;

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

class AbstractMessageStringMultiHeaderMatcher extends AbstractMessageMultiHeaderMatcher<String> {

    protected AbstractMessageStringMultiHeaderMatcher(String header, Matcher<Iterable<String>> matcher) {
        super(header, matcher);
    }

    @Override
    protected Condition<Iterable<String>> values(Message message, Description mismatch) {
        return headerValues(message, mismatch);
    }
}
