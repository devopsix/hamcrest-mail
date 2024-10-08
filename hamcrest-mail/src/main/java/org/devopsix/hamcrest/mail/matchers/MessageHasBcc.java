package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.matchers.HeaderNames.BCC;

import javax.mail.Message;
import org.hamcrest.Matcher;

/**
 * <p>A matcher for the "Bcc" header.</p>
 *
 * @author devopsix
 * @see Message
 */
public class MessageHasBcc extends AbstractStringHeaderMatcher<Message> {

  /**
   * <p>Creates a new instance.</p>
   *
   * @param matcher Bcc matcher
   */
  public MessageHasBcc(Matcher<String> matcher) {
    super(BCC, matcher);
  }
}
