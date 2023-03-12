package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.matchers.HeaderNames.DATE;

import java.time.OffsetDateTime;
import javax.mail.Message;
import org.hamcrest.Matcher;

/**
 * <p>A matcher for the "Date" header.</p>
 *
 * @author devopsix
 * @see OffsetDateTime
 * @see Message
 */
public class MessageHasDate extends AbstractDateHeaderMatcher<Message> {

  public MessageHasDate(Matcher<OffsetDateTime> matcher) {
    super(DATE, matcher);
  }
}
