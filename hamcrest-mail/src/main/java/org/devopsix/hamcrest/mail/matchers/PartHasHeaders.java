package org.devopsix.hamcrest.mail.matchers;

import javax.mail.Part;
import org.hamcrest.Matcher;

/**
 * A matcher for a named header which may be present multiple times.
 *
 * @author devopsix
 */
public class PartHasHeaders extends AbstractStringMultiHeaderMatcher<Part> {

  /**
   * Creates a new instance.
   *
   * @param header Header name
   * @param matcher Header value matcher
   */
  public PartHasHeaders(String header, Matcher<Iterable<String>> matcher) {
    super(header, matcher);
  }
}
