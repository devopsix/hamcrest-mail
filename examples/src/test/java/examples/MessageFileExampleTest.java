package examples;

import static java.util.Collections.singletonMap;
import static org.devopsix.hamcrest.mail.MessageMatchers.hasDate;
import static org.devopsix.hamcrest.mail.MessageMatchers.hasFrom;
import static org.devopsix.hamcrest.mail.MessageMatchers.hasHeader;
import static org.devopsix.hamcrest.mail.MessageMatchers.hasHeaders;
import static org.devopsix.hamcrest.mail.MessageMatchers.hasMultipartBody;
import static org.devopsix.hamcrest.mail.MessageMatchers.hasSubject;
import static org.devopsix.hamcrest.mail.MessageMatchers.hasTo;
import static org.devopsix.hamcrest.mail.MessageMatchers.hasValidDkimSignature;
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
import java.util.Map;
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
        try (InputStream messageStream = getClass().getClassLoader().getResourceAsStream("message.txt")) {
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
        assertThat(message, hasDate(isDay(LocalDate.of(2019, 12, 8))));
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
        assertThat(message, hasTo("test-fbll2@mail-tester.com"));
        assertThat(message, hasTo(containsString("@mail-tester.com")));
    }
    
    @Test
    public void messageShouldHaveReturnPath() {
        assertThat(message, hasHeader("Return-Path", emptyOrNullString()));
    }
    
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void messageShouldHaveReceivedHeaders() {
        assertThat(message, hasHeaders("Received", (Matcher)iterableWithSize(3)));
        assertThat(message, hasHeaders("Received", (Matcher)everyItem(anyOf(startsWith("from "), startsWith("by ")))));
        assertThat(message, hasHeaders("Received", (Matcher)hasItems(containsString("mail-tester.com"), containsString("google.com"))));
    }
    
    @Test
    public void messageShouldHaveValidDkimSignature() {
        assertThat(message, hasHeader("DKIM-Signature", not(emptyOrNullString())));
        // This is the DKIM public key as published by Google for gmail.com at the time
        // the test message was recorded.
        // In your tests, replace with the key that that was actually used for signing your message.
        final Map<String, String> gmailPublicKey = singletonMap("20161025._domainkey.gmail.com",
            "k=rsa; p=MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAviPGBk4ZB64UfSqWyAicdR7lodhy"
            + "tae+EYRQVtKDhM+1mXjEqRtP/pDT3sBhazkmA48n2k5NJUyMEoO8nc2r6sUA+/Dom5jRBZp6qDKJOwj"
            + "J5R/OpHamlRG+YRJQqRtqEgSiJWG7h7efGYWmh4URhFM9k9+rmG/CwCgwx7Et+c8OMlngaLl04/bPmf"
            + "pjdEyLWyNimk761CX6KymzYiRDNz1MOJOJ7OzFaS4PFbVLn0m5mf0HVNtBpPwWuCNvaFVflUYxEyblb"
            + "B6h/oWOPGbzoSgtRA47SHV53SwZjIsVpbq4LxUW9IxAEwYzGcSgZ4n5Q8X8TndowsDUzoccPFGhdwIDAQAB");
        assertThat(message, hasValidDkimSignature(gmailPublicKey));
    }
    
    @Test
    public void messageShouldHaveMultipartBody() {
        assertThat(message, hasMultipartBody());
    }
}
