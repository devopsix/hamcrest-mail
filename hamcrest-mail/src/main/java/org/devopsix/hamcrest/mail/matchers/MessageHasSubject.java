package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.matchers.HeaderNames.SUBJECT;

import javax.mail.Message;
import org.hamcrest.Matcher;

/**
 * A matcher for the "Subject" header.
 *
 * @author devopsix
 * @see Message
 */
public class MessageHasSubject extends AbstractStringHeaderMatcher<Message> {

  /**
   * Creates a new instance.
   *
   * @param matcher Subject matcher
   */
  public MessageHasSubject(Matcher<String> matcher) {
    super(SUBJECT, matcher);
  }
}