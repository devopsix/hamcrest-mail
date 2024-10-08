package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.matchers.HeaderNames.SUBJECT;

import javax.mail.Message;
import org.hamcrest.Matcher;

/**
 * <p>A matcher for the "Subject" header.</p>
 *
 * @author devopsix
 * @see Message
 */
public class MessageHasSubject extends AbstractStringHeaderMatcher<Message> {

  /**
   * <p>Creates a new instance.</p>
   *
   * @param matcher Subject matcher
   */
  public MessageHasSubject(Matcher<String> matcher) {
    super(SUBJECT, matcher);
  }
}