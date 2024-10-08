package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.matchers.HeaderNames.REPLY_TO;

import javax.mail.Message;
import org.hamcrest.Matcher;

/**
 * <p>A matcher for the "Reply-To" header.</p>
 *
 * @author devopsix
 * @see Message
 */
public class MessageHasReplyTo extends AbstractStringHeaderMatcher<Message> {

  /**
   * <p>Creates a new instance.</p>
   *
   * @param matcher Reply-To matcher
   */
  public MessageHasReplyTo(Matcher<String> matcher) {
    super(REPLY_TO, matcher);
  }
}
