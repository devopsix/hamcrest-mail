package devopsix.hamcrest.email.matchers;

import static java.lang.String.format;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

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
    
    protected MimeMessage loadMessage(String filename) throws IOException, MessagingException {
        try (InputStream messageStream = getClass().getClassLoader().getResourceAsStream(filename)) {
            Session session = Session.getDefaultInstance(new Properties());
            return new MimeMessage(session, messageStream);
        }
    }
}
