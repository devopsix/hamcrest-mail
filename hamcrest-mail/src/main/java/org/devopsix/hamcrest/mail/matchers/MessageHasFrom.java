package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.matchers.HeaderNames.FROM;

import javax.mail.Message;
import org.hamcrest.Matcher;

/**
 * A matcher for the "From" header.
 *
 * @author devopsix
 * @see Message
 */
public class MessageHasFrom extends AbstractStringHeaderMatcher<Message> {

  /**
   * Creates a new instance.
   *
   * @param matcher From matcher
   */
  public MessageHasFrom(Matcher<String> matcher) {
    super(FROM, matcher);
  }
}
