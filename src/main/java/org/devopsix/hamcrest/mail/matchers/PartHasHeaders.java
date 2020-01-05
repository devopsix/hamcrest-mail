package org.devopsix.hamcrest.mail.matchers;

import javax.mail.Part;

import org.hamcrest.Matcher;

/**
 * <p>A matcher for a named header which may be present multiple times.</p>
 * 
 * @author devopsix
 *
 */
public class PartHasHeaders extends AbstractStringMultiHeaderMatcher<Part> {
    
    public PartHasHeaders(String header, Matcher<Iterable<String>> matcher) {
        super(header, matcher);
    }
}
