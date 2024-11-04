package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.matchers.HeaderNames.SENDER;

import javax.mail.Message;
import org.hamcrest.Matcher;

/**
 * A matcher for the "Sender" header.
 *
 * @author devopsix
 * @see Message
 */
public class MessageHasSender extends AbstractStringHeaderMatcher<Message> {

  /**
   * Creates a new instance.
   *
   * @param matcher Sender matcher
   */
  public MessageHasSender(Matcher<String> matcher) {
    super(SENDER, matcher);
  }
}
