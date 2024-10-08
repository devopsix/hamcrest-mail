package org.devopsix.hamcrest.mail.matchers;

import javax.mail.Part;
import org.hamcrest.Matcher;

/**
 * <p>A matcher for a named header.</p>
 *
 * @author devopsix
 */
public class PartHasHeader extends AbstractStringHeaderMatcher<Part> {

  /**
   * <p>Creates a new instance.</p>
   *
   * @param header Header name
   * @param matcher Header value matcher
   */
  public PartHasHeader(String header, Matcher<String> matcher) {
    super(header, matcher);
  }
}
