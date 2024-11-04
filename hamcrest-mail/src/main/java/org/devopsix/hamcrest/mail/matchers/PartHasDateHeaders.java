package org.devopsix.hamcrest.mail.matchers;

import java.time.OffsetDateTime;
import javax.mail.Part;
import org.hamcrest.Matcher;

/**
 * A matcher for a named date header which may be present multiple times.
 *
 * @author devopsix
 */
public class PartHasDateHeaders extends AbstractDateMultiHeaderMatcher<Part> {

  /**
   * Creates a new instance.
   *
   * @param header Header name
   * @param matcher Date matcher
   */
  public PartHasDateHeaders(String header, Matcher<Iterable<OffsetDateTime>> matcher) {
    super(header, matcher);
  }
}
