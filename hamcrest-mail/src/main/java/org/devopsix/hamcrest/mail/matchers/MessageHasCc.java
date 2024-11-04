package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.matchers.HeaderNames.CC;

import javax.mail.Message;
import org.hamcrest.Matcher;

/**
 * A matcher for the "Cc" header.
 *
 * @author devopsix
 * @see Message
 */
public class MessageHasCc extends AbstractStringHeaderMatcher<Message> {

  /**
   * Creates a new instance.
   *
   * @param matcher Cc matcher
   */
  public MessageHasCc(Matcher<String> matcher) {
    super(CC, matcher);
  }
}
