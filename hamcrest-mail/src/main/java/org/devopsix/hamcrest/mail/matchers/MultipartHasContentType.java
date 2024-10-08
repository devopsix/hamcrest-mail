package org.devopsix.hamcrest.mail.matchers;

import static java.util.Objects.nonNull;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;

import javax.mail.Multipart;
import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * <p>A matcher for multipart content type.</p>
 *
 * @author devopsix
 */
public class MultipartHasContentType extends TypeSafeDiagnosingMatcher<Multipart> {

  final Matcher<String> matcher;

  /**
   * <p>Creates a new instance.</p>
   *
   * @param matcher Content type matcher
   */
  public MultipartHasContentType(Matcher<String> matcher) {
    this.matcher = matcher;
  }

  @Override
  protected boolean matchesSafely(Multipart multipart, Description mismatch) {
    return contentType(multipart, mismatch).matching(matcher);
  }

  private Condition<String> contentType(Multipart multipart, Description mismatch) {
    String contentType = multipart.getContentType();
    if (nonNull(contentType)) {
      return matched(contentType, mismatch);
    } else {
      mismatch.appendText("null");
      return notMatched();
    }
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("has content type which matches: ");
    matcher.describeTo(description);
  }
}
