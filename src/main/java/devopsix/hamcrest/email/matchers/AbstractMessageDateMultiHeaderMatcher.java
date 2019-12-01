package devopsix.hamcrest.email.matchers;

import static devopsix.hamcrest.email.util.MailDateTimeFormatter.MAIL_DATE_TIME;
import static java.time.OffsetDateTime.parse;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;

import java.time.OffsetDateTime;

import javax.mail.Message;

import org.hamcrest.Condition;
import org.hamcrest.Condition.Step;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

abstract class AbstractMessageDateMultiHeaderMatcher extends AbstractMessageMultiHeaderMatcher<OffsetDateTime> {

    protected AbstractMessageDateMultiHeaderMatcher(String header, Matcher<Iterable<OffsetDateTime>> matcher) {
        super(header, matcher);
    }

    @Override
    protected Condition<Iterable<OffsetDateTime>> values(Message message, Description mismatch) {
        return headerValues(message, mismatch).and(parseDateTime());
    }
    
    private Step<Iterable<String>, Iterable<OffsetDateTime>> parseDateTime() {
        return new Step<Iterable<String>, Iterable<OffsetDateTime>>() {
            @Override
            public Condition<Iterable<OffsetDateTime>> apply(Iterable<String> values, Description mismatch) {
                if (isNull(values)) {
                    return matched(null, mismatch);
                }
                try {
                    Iterable<OffsetDateTime> convertedValues = stream(values.spliterator(), false).map((v) -> parse(v, MAIL_DATE_TIME)).collect(toList());
                    return matched(convertedValues, mismatch);
                } catch (Exception e) {
                    mismatch.appendText("failed to parse date header value: " + e.getMessage());
                    return notMatched();
                }
            }
        };
    }
}
