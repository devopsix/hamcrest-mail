package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.matchers.HeaderNames.SENDER;

import javax.mail.Message;
import org.hamcrest.Matcher;

/**
 * <p>A matcher for the "Sender" header.</p>
 *
 * @author devopsix
 * @see Message
 */
public class MessageHasSender extends AbstractStringHeaderMatcher<Message> {

  /**
   * <p>Creates a new instance.</p>
   *
   * @param matcher Sender matcher
   */
  public MessageHasSender(Matcher<String> matcher) {
    super(SENDER, matcher);
  }
}
