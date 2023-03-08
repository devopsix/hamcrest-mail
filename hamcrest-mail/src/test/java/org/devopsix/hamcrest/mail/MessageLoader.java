package org.devopsix.hamcrest.mail;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class MessageLoader {

    private MessageLoader() {}

    public static MimeMessage loadMessage(String filename)  {
        try {
            try (InputStream messageStream = MessageLoader.class.getClassLoader().getResourceAsStream(filename)) {
                Session session = Session.getDefaultInstance(new Properties());
                return new MimeMessage(session, messageStream);
            }
        } catch (IOException|MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
