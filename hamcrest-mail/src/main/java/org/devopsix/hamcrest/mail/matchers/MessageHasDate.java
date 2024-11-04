package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.matchers.HeaderNames.DATE;

import java.time.OffsetDateTime;
import javax.mail.Message;
import org.hamcrest.Matcher;

/**
 * A matcher for the "Date" header.
 *
 * @author devopsix
 * @see OffsetDateTime
 * @see Message
 */
public class MessageHasDate extends AbstractDateHeaderMatcher<Message> {

  /**
   * Creates a new instance.
   *
   * @param matcher Date matcher
   */
  public MessageHasDate(Matcher<OffsetDateTime> matcher) {
    super(DATE, matcher);
  }
}
