package org.devopsix.hamcrest.mail.matchers;

import java.time.OffsetDateTime;
import javax.mail.Part;
import org.hamcrest.Matcher;

/**
 * <p>A matcher for a named date header.</p>
 *
 * @author devopsix
 */
public class PartHasDateHeader extends AbstractDateHeaderMatcher<Part> {

  /**
   * <p>Creates a new instance.</p>
   *
   * @param header Header name
   * @param matcher Header value matcher
   */
  public PartHasDateHeader(String header, Matcher<OffsetDateTime> matcher) {
    super(header, matcher);
  }
}
