package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.matchers.HeaderNames.REPLY_TO;

import javax.mail.Message;
import org.hamcrest.Matcher;

/**
 * A matcher for the "Reply-To" header.
 *
 * @author devopsix
 * @see Message
 */
public class MessageHasReplyTo extends AbstractStringHeaderMatcher<Message> {

  /**
   * Creates a new instance.
   *
   * @param matcher Reply-To matcher
   */
  public MessageHasReplyTo(Matcher<String> matcher) {
    super(REPLY_TO, matcher);
  }
}
