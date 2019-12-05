package examples;

import static devopsix.hamcrest.email.MessageMatchers.hasDate;
import static devopsix.hamcrest.email.MessageMatchers.hasFrom;
import static devopsix.hamcrest.email.MessageMatchers.hasHeader;
import static devopsix.hamcrest.email.MessageMatchers.hasHeaders;
import static devopsix.hamcrest.email.MessageMatchers.hasSubject;
import static devopsix.hamcrest.email.MessageMatchers.hasTo;
import static org.exparity.hamcrest.date.OffsetDateTimeMatchers.isDay;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

/**
 * This class demonstrates how the Hamcrest Mail Matchers can be used for
 * verifying an email loaded from a file.
 * 
 * @author devopsix
 *
 */
public class MessageFileExampleTest {
    
    private Message message;

    @Before
    public void loadMessage() throws IOException, MessagingException {
        try (InputStream messageStream = getClass().getClassLoader().getResourceAsStream("message.eml")) {
            Session session = Session.getDefaultInstance(new Properties());
            message = new MimeMessage(session, messageStream);
        }
    }
    
    @Test
    public void messageShouldHaveFrom() {
        assertThat(message, hasFrom("devopsix <devopsix@gmail.com>"));
        assertThat(message, hasFrom(containsString("devopsix")));
    }
    
    @Test
    public void messageShouldHaveDate() {
        assertThat(message, hasDate(isDay(LocalDate.of(2019, 12, 5))));
    }
    
    @Test
    public void messageShouldHaveMessageId() {
        assertThat(message, hasHeader("Message-Id", endsWith("@mail.gmail.com>")));
    }
    
    @Test
    public void messageShouldHaveSubject() {
        assertThat(message, hasSubject("Lörem Ipsüm"));
    }
    
    @Test
    public void messageShouldHaveTo() {
        assertThat(message, hasTo("devopsix@outlook.com"));
        assertThat(message, hasTo(containsString("@outlook.com")));
    }
    
    @Test
    public void messageShouldHaveReturnPath() {
        assertThat(message, hasHeader("Return-Path", "devopsix@gmail.com"));
        assertThat(message, hasHeader("Return-Path", containsString("@gmail.com")));
    }
    
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void messageShouldHaveReceivedHeaders() {
        assertThat(message, hasHeaders("Received", (Matcher)iterableWithSize(4)));
        assertThat(message, hasHeaders("Received", (Matcher)everyItem(anyOf(startsWith("from "), startsWith("by ")))));
        assertThat(message, hasHeaders("Received", (Matcher)hasItems(containsString("outlook.com"), containsString("google.com"))));
    }
    
    @Test
    public void messageShouldHaveDkimSignature() {
        assertThat(message, hasHeader("DKIM-Signature", not(emptyOrNullString())));
    }
}
