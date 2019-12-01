package devopsix.hamcrest.email.matchers;

import static java.lang.String.format;

import java.util.logging.Logger;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public abstract class MatcherTest {
    
    private static final Logger LOGGER = Logger.getGlobal();
    
    protected void logMismatchDescription(Matcher<?> matcher, Object item) {
        Description description = new StringDescription();
        matcher.describeTo(description);
        Description mismatchDescription = new StringDescription();
        matcher.describeMismatch(item, mismatchDescription);
        LOGGER.info(format("Expected: %s; But was: %s",
            description.toString(), mismatchDescription.toString()));
    }
}
