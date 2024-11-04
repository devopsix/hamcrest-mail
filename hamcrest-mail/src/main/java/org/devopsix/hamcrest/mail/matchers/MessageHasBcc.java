package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.matchers.HeaderNames.BCC;

import javax.mail.Message;
import org.hamcrest.Matcher;

/**
 * A matcher for the "Bcc" header.
 *
 * @author devopsix
 * @see Message
 */
public class MessageHasBcc extends AbstractStringHeaderMatcher<Message> {

  /**
   * Creates a new instance.
   *
   * @param matcher Bcc matcher
   */
  public MessageHasBcc(Matcher<String> matcher) {
    super(BCC, matcher);
  }
}
