package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.matchers.HeaderNames.CC;

import javax.mail.Message;
import org.hamcrest.Matcher;

/**
 * <p>A matcher for the "Cc" header.</p>
 *
 * @author devopsix
 * @see Message
 */
public class MessageHasCc extends AbstractStringHeaderMatcher<Message> {

  /**
   * <p>Creates a new instance.</p>
   *
   * @param matcher Cc matcher
   */
  public MessageHasCc(Matcher<String> matcher) {
    super(CC, matcher);
  }
}
