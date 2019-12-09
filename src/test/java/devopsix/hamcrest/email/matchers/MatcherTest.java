package devopsix.hamcrest.email.matchers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public abstract class MatcherTest {
    
    protected MimeMessage loadMessage(String filename) throws IOException, MessagingException {
        try (InputStream messageStream = getClass().getClassLoader().getResourceAsStream(filename)) {
            Session session = Session.getDefaultInstance(new Properties());
            return new MimeMessage(session, messageStream);
        }
    }
}
