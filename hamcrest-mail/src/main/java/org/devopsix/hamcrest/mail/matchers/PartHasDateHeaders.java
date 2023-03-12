package org.devopsix.hamcrest.mail.matchers;

import java.time.OffsetDateTime;
import javax.mail.Part;
import org.hamcrest.Matcher;

/**
 * <p>A matcher for a named date header which may be present multiple times.</p>
 *
 * @author devopsix
 */
public class PartHasDateHeaders extends AbstractDateMultiHeaderMatcher<Part> {

  public PartHasDateHeaders(String header, Matcher<Iterable<OffsetDateTime>> matcher) {
    super(header, matcher);
  }
}
