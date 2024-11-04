package org.devopsix.hamcrest.mail.matchers;

import java.time.OffsetDateTime;
import javax.mail.Part;
import org.hamcrest.Matcher;

/**
 * A matcher for a named date header.
 *
 * @author devopsix
 */
public class PartHasDateHeader extends AbstractDateHeaderMatcher<Part> {

  /**
   * Creates a new instance.
   *
   * @param header Header name
   * @param matcher Header value matcher
   */
  public PartHasDateHeader(String header, Matcher<OffsetDateTime> matcher) {
    super(header, matcher);
  }
}
