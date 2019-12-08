package devopsix.hamcrest.email.matchers;

import javax.mail.Part;

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

class AbstractStringHeaderMatcher<P extends Part> extends AbstractHeaderMatcher<P,String> {

    protected AbstractStringHeaderMatcher(String header, Matcher<String> matcher) {
        super(header, matcher);
    }

    @Override
    protected Condition<String> value(P part, Description mismatch) {
        return headerValue(part, mismatch);
    }
}
