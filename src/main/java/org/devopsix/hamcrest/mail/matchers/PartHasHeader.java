package org.devopsix.hamcrest.mail.matchers;

import javax.mail.Part;

import org.hamcrest.Matcher;

/**
 * <p>A matcher for a named header.</p>
 * 
 * @author devopsix
 *
 */
public class PartHasHeader extends AbstractStringHeaderMatcher<Part> {
    
    public PartHasHeader(String header, Matcher<String> matcher) {
        super(header, matcher);
    }
}
