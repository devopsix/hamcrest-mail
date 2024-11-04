package org.devopsix.hamcrest.mail.matchers;

import static java.lang.String.format;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;

import java.util.LinkedList;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * A matcher for multipart parts.
 *
 * @author devopsix
 */
public class MultipartHasParts extends TypeSafeDiagnosingMatcher<Multipart> {

  final Matcher<Iterable<Part>> matcher;

  /**
   * Creates a new instance.
   *
   * @param matcher Parts matcher
   */
  public MultipartHasParts(Matcher<Iterable<Part>> matcher) {
    this.matcher = matcher;
  }

  @Override
  protected boolean matchesSafely(Multipart multipart, Description mismatch) {
    return parts(multipart, mismatch).matching(matcher);
  }

  private Condition<Iterable<Part>> parts(Multipart multipart, Description mismatch) {
    try {
      LinkedList<Part> parts = new LinkedList<>();
      for (int i = 0; i < multipart.getCount(); i++) {
        parts.add(multipart.getBodyPart(i));
      }
      return matched(parts, mismatch);
    } catch (MessagingException e) {
      mismatch.appendText(format("failed to extract parts: %s", e.getMessage()));
      return notMatched();
    }
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("has parts which match: ");
    matcher.describeTo(description);
  }
}
