package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.matchers.HeaderNames.FROM;

import javax.mail.Message;
import org.hamcrest.Matcher;

/**
 * <p>A matcher for the "From" header.</p>
 *
 * @author devopsix
 * @see Message
 */
public class MessageHasFrom extends AbstractStringHeaderMatcher<Message> {

  /**
   * <p>Creates a new instance.</p>
   *
   * @param matcher From matcher
   */
  public MessageHasFrom(Matcher<String> matcher) {
    super(FROM, matcher);
  }
}
