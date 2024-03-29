package org.devopsix.hamcrest.mail.matchers;

import static java.lang.String.format;
import static java.time.OffsetDateTime.parse;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static org.devopsix.hamcrest.mail.matchers.MailDateTimeFormatter.MAIL_DATE_TIME;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;

import java.time.OffsetDateTime;
import javax.mail.Part;
import org.hamcrest.Condition;
import org.hamcrest.Condition.Step;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Base class for date header matchers.
 *
 * @author devopsix
 * @see Part
 * @see OffsetDateTime
 */
abstract class AbstractDateMultiHeaderMatcher<P extends Part>
    extends AbstractMultiHeaderMatcher<P, OffsetDateTime> {

  protected AbstractDateMultiHeaderMatcher(String header,
                                           Matcher<Iterable<OffsetDateTime>> matcher) {
    super(header, matcher);
  }

  @Override
  protected Condition<Iterable<OffsetDateTime>> values(P part, Description mismatch) {
    return headerValues(part, mismatch).and(parseDateTime());
  }

  private Step<Iterable<String>, Iterable<OffsetDateTime>> parseDateTime() {
    return (values, mismatch) -> {
      if (isNull(values)) {
        return matched(null, mismatch);
      }
      try {
        Iterable<OffsetDateTime> convertedValues =
            stream(values.spliterator(), false)
                .map(MailDateTimeFormatter::trimTrailingZoneText)
                .map((v) -> parse(v, MAIL_DATE_TIME))
                .collect(toList());
        return matched(convertedValues, mismatch);
      } catch (Exception e) {
        mismatch.appendText(format("failed to parse date header value: %s", e.getMessage()));
        return notMatched();
      }
    };
  }
}
