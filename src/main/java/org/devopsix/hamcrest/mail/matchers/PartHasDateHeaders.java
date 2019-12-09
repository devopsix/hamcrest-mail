package org.devopsix.hamcrest.mail.matchers;

import java.time.OffsetDateTime;

import javax.mail.Part;

import org.hamcrest.Matcher;

/**
 * A matcher for named date headers.
 * 
 * @author devopsix
 *
 */
public class PartHasDateHeaders extends AbstractDateMultiHeaderMatcher<Part> {
    
    public PartHasDateHeaders(String header, Matcher<Iterable<OffsetDateTime>> matcher) {
        super(header, matcher);
    }
}
