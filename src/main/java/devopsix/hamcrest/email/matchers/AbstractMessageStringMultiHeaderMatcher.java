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
        // TODO Auto-generated method stub
        return headerValues(message, mismatch);
    }
}
