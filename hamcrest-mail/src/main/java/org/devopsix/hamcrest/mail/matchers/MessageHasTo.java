package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.matchers.HeaderNames.TO;

import javax.mail.Message;
import org.hamcrest.Matcher;

/**
 * A matcher for the "To" header.
 *
 * @author devopsix
 * @see Message
 */
public class MessageHasTo extends AbstractStringHeaderMatcher<Message> {

  /**
   * Creates a new instance.
   *
   * @param matcher To matcher
   */
  public MessageHasTo(Matcher<String> matcher) {
    super(TO, matcher);
  }
}
