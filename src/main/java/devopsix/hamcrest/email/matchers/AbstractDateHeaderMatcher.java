package devopsix.hamcrest.email.matchers;

import static devopsix.hamcrest.email.util.MailDateTimeFormatter.MAIL_DATE_TIME;
import static java.time.OffsetDateTime.parse;
import static java.util.Objects.isNull;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;

import java.time.OffsetDateTime;

import javax.mail.Part;

import org.hamcrest.Condition;
import org.hamcrest.Condition.Step;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

abstract class AbstractDateHeaderMatcher<P extends Part> extends AbstractHeaderMatcher<P,OffsetDateTime> {

    protected AbstractDateHeaderMatcher(String header, Matcher<OffsetDateTime> matcher) {
        super(header, matcher);
    }

    @Override
    protected Condition<OffsetDateTime> value(P part, Description mismatch) {
        return headerValue(part, mismatch).and(parseDateTime());
    }
    
    private Step<String, OffsetDateTime> parseDateTime() {
        return new Step<String, OffsetDateTime>() {
            @Override
            public Condition<OffsetDateTime> apply(String value, Description mismatch) {
                if (isNull(value)) {
                    return matched(null, mismatch);
                }
                try {
                    return matched(parse(value, MAIL_DATE_TIME), mismatch);
                } catch (Exception e) {
                    mismatch.appendText("failed to parse date header value: " + e.getMessage());
                    return notMatched();
                }
            }
        };
    }
}
