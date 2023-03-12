package org.devopsix.hamcrest.mail.matchers;

import javax.mail.Part;
import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Base class for header matchers.
 *
 * @author devopsix
 * @see Part
 */
class AbstractStringMultiHeaderMatcher<P extends Part>
    extends AbstractMultiHeaderMatcher<P, String> {

  protected AbstractStringMultiHeaderMatcher(String header, Matcher<Iterable<String>> matcher) {
    super(header, matcher);
  }

  @Override
  protected Condition<Iterable<String>> values(P part, Description mismatch) {
    return headerValues(part, mismatch);
  }
}
