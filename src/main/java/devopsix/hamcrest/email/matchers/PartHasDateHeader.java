package devopsix.hamcrest.email.matchers;

import java.time.OffsetDateTime;

import javax.mail.Part;

import org.hamcrest.Matcher;

/**
 * A matcher for a named date header.
 * 
 * @author devopsix
 *
 */
public class PartHasDateHeader extends AbstractDateHeaderMatcher<Part> {
    
    public PartHasDateHeader(String header, Matcher<OffsetDateTime> matcher) {
        super(header, matcher);
    }
}
