package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.matchers.HeaderNames.TO;

import javax.mail.Message;
import org.hamcrest.Matcher;

/**
 * <p>A matcher for the "To" header.</p>
 *
 * @author devopsix
 * @see Message
 */
public class MessageHasTo extends AbstractStringHeaderMatcher<Message> {

  /**
   * <p>Creates a new instance.</p>
   *
   * @param matcher To matcher
   */
  public MessageHasTo(Matcher<String> matcher) {
    super(TO, matcher);
  }
}
