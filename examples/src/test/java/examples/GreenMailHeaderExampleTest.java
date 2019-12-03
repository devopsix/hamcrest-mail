package examples;

import static com.icegreen.greenmail.util.ServerSetupTest.SMTP;
import static devopsix.hamcrest.email.MessageMatchers.hasBcc;
import static devopsix.hamcrest.email.MessageMatchers.hasCc;
import static devopsix.hamcrest.email.MessageMatchers.hasDate;
import static devopsix.hamcrest.email.MessageMatchers.hasDateHeaders;
import static devopsix.hamcrest.email.MessageMatchers.hasFrom;
import static devopsix.hamcrest.email.MessageMatchers.hasHeader;
import static devopsix.hamcrest.email.MessageMatchers.hasHeaders;
import static devopsix.hamcrest.email.MessageMatchers.hasReplyTo;
import static devopsix.hamcrest.email.MessageMatchers.hasSender;
import static devopsix.hamcrest.email.MessageMatchers.hasSubject;
import static devopsix.hamcrest.email.MessageMatchers.hasTo;
import static java.time.OffsetDateTime.now;
import static java.time.OffsetDateTime.parse;
import static java.time.temporal.ChronoUnit.MINUTES;
import static javax.mail.Message.RecipientType.TO;
import static org.exparity.hamcrest.date.OffsetDateTimeMatchers.sameOrBefore;
import static org.exparity.hamcrest.date.OffsetDateTimeMatchers.within;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.not;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.icegreen.greenmail.junit.GreenMailRule;

/**
 * This class demonstrates how the Hamcrest Mail Matchers can be used together
 * with GreenMail for verifying headers of an email sent by an application.
 * 
 * GreenMail is an email server for testing purposes.
 * 
 * http://www.icegreen.com/greenmail/
 * 
 * @author devopsix
 *
 */
public class GreenMailHeaderExampleTest {

    // This rule will fire up an SMTP server listening at 127.0.0.1:3025
    // for every test.
    @Rule
    public GreenMailRule greenMail = new GreenMailRule(SMTP);
    
    @Before
    public void sendMessage() throws Exception {
        // This is what your application code would do and what you usually
        // would invoke a method on the test subject for.
        // With GreenMail it does not matter whether your application uses JavaMail
        // or any other library. It would just have to sent a message via SMTP.
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "127.0.0.1");
        properties.put("mail.smtp.port", "3025");
        Session session = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(session);
        message.setFrom("bob@example.com");
        message.setRecipients(TO, "anna@example.com");
        message.setSubject("Message from Bob");
        message.setHeader("X-Custom-Header", "Foo");
        message.addHeader("X-Custom-Multi-Header", "Foo");
        message.addHeader("X-Custom-Multi-Header", "Bar");
        message.addHeader("Resent-Date", "Wed, 1 Dec 82 14:49:44 +0100");
        message.addHeader("Resent-Date", "Sun, 1 Dec 2019 14:49:44 +0100");
        message.setText("Lorem ipsum");
        Transport.send(message);
    }
    
    @Test
    public void messageShouldHaveFrom() {
        MimeMessage message = getReceivedMessage();
        assertThat(message, hasFrom(not(emptyOrNullString())));
        assertThat(message, hasFrom("bob@example.com"));
        assertThat(message, hasFrom(equalTo("bob@example.com")));
        assertThat(message, hasFrom(endsWith("@example.com")));
    }
    
    @Test
    public void messageShouldHaveDate() {
        MimeMessage message = getReceivedMessage();
        // These OffsetDateTime matchers are from eXparity/hamcrest-date:
        // https://github.com/eXparity/hamcrest-date
        assertThat(message, hasDate(sameOrBefore(now())));
        assertThat(message, hasDate(within(1, MINUTES, now())));
    }
    
    @Test
    public void messageShouldNotHaveSender() {
        MimeMessage message = getReceivedMessage();
        assertThat(message, hasSender(emptyOrNullString()));
    }
    
    @Test
    public void messageShouldNotHaveReplyTo() {
        MimeMessage message = getReceivedMessage();
        assertThat(message, hasReplyTo(emptyOrNullString()));
    }
    
    @Test
    public void messageShouldHaveTo() {
        MimeMessage message = getReceivedMessage();
        assertThat(message, hasTo(not(emptyOrNullString())));
        assertThat(message, hasTo("anna@example.com"));
        assertThat(message, hasTo(equalTo("anna@example.com")));
        assertThat(message, hasTo(endsWith("@example.com")));
    }
    
    @Test
    public void messageShouldNotHaveCc() {
        MimeMessage message = getReceivedMessage();
        assertThat(message, hasCc(emptyOrNullString()));
    }
    
    @Test
    public void messageShouldNotHaveBcc() {
        MimeMessage message = getReceivedMessage();
        assertThat(message, hasBcc(emptyOrNullString()));
    }
    
    @Test
    public void messageShouldHaveReceived() {
        MimeMessage message = getReceivedMessage();
        assertThat(message, hasHeaders("Received", iterableWithSize(1)));
    }
    
    @Test
    public void messageShouldHaveSubject() {
        MimeMessage message = getReceivedMessage();
        assertThat(message, hasSubject("Message from Bob"));
        assertThat(message, hasSubject(not(emptyOrNullString())));
        assertThat(message, hasSubject(equalTo("Message from Bob")));
        assertThat(message, hasSubject(containsString("Bob")));
    }
    
    @Test
    public void messageShouldHaveCustomHeader() {
        MimeMessage message = getReceivedMessage();
        assertThat(message, hasHeader("X-Custom-Header", "Foo"));
        assertThat(message, hasHeader("X-Custom-Header", not(emptyOrNullString())));
        assertThat(message, hasHeader("X-Custom-Header", equalTo("Foo")));
        assertThat(message, hasHeader("X-Custom-Header", containsString("o")));
    }
    
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void messageShouldHaveMultipleCustomHeaders() {
        MimeMessage message = getReceivedMessage();
        assertThat(message, hasHeaders("X-Custom-Multi-Header", (Matcher)hasItem(equalTo("Foo"))));
        assertThat(message, hasHeaders("X-Custom-Multi-Header", (Matcher)hasItem(equalTo("Bar"))));
        assertThat(message, hasHeaders("X-Custom-Multi-Header", hasItems(equalTo("Foo"), equalTo("Bar"))));
        assertThat(message, hasHeaders("X-Custom-Multi-Header", (Matcher)contains("Foo", "Bar")));
    }
    
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void messageShouldHaveMultipleResentDateHeaders() {
        MimeMessage message = getReceivedMessage();
        assertThat(message, hasDateHeaders("Resent-Date", (Matcher)hasItem(equalTo(parse("1982-12-01T14:49:44+01:00")))));
        assertThat(message, hasDateHeaders("Resent-Date", (Matcher)hasItem(equalTo(parse("2019-12-01T14:49:44+01:00")))));
        assertThat(message, hasDateHeaders("Resent-Date", hasItems(
            equalTo(parse("1982-12-01T14:49:44+01:00")),
            equalTo(parse("2019-12-01T14:49:44+01:00")))));
        assertThat(message, hasDateHeaders("Resent-Date", (Matcher)contains(
            parse("1982-12-01T14:49:44+01:00"),
            parse("2019-12-01T14:49:44+01:00"))));
    }
    
    private MimeMessage getReceivedMessage() {
        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertThat(messages, arrayWithSize(1));
        return messages[0];
    }
}
