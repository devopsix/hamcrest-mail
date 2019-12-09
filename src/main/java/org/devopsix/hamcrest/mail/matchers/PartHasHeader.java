package org.devopsix.hamcrest.mail.matchers;

import javax.mail.Part;

import org.hamcrest.Matcher;

/**
 * A matcher for the a named header.
 * 
 * @author devopsix
 *
 */
public class PartHasHeader extends AbstractStringHeaderMatcher<Part> {
    
    public PartHasHeader(String header, Matcher<String> matcher) {
        super(header, matcher);
    }
}
