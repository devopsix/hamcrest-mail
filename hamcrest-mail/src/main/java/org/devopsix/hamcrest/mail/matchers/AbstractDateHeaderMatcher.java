package org.devopsix.hamcrest.mail.matchers;

import static java.lang.String.format;
import static java.time.OffsetDateTime.parse;
import static java.util.Objects.isNull;
import static org.devopsix.hamcrest.mail.matchers.MailDateTimeFormatter.MAIL_DATE_TIME;
import static org.devopsix.hamcrest.mail.matchers.MailDateTimeFormatter.trimTrailingZoneText;
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
abstract class AbstractDateHeaderMatcher<P extends Part>
    extends AbstractHeaderMatcher<P, OffsetDateTime> {

  protected AbstractDateHeaderMatcher(String header, Matcher<OffsetDateTime> matcher) {
    super(header, matcher);
  }

  @Override
  protected Condition<OffsetDateTime> value(P part, Description mismatch) {
    return headerValue(part, mismatch).and(parseDateTime());
  }

  private Step<String, OffsetDateTime> parseDateTime() {
    return (value, mismatch) -> {
      if (isNull(value)) {
        return matched(null, mismatch);
      }
      try {
        value = trimTrailingZoneText(value);
        return matched(parse(value, MAIL_DATE_TIME), mismatch);
      } catch (Exception e) {
        mismatch.appendText(format("failed to parse date header value: %s", e.getMessage()));
        return notMatched();
      }
    };
  }
}
