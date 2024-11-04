package org.devopsix.hamcrest.mail.matchers;

import javax.mail.Part;
import org.hamcrest.Matcher;

/**
 * A matcher for a named header.
 *
 * @author devopsix
 */
public class PartHasHeader extends AbstractStringHeaderMatcher<Part> {

  /**
   * Creates a new instance.
   *
   * @param header Header name
   * @param matcher Header value matcher
   */
  public PartHasHeader(String header, Matcher<String> matcher) {
    super(header, matcher);
  }
}
