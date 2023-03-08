package examples;

import static java.util.Collections.singletonMap;
import static org.devopsix.hamcrest.mail.MailMatchers.hasBinaryContent;
import static org.devopsix.hamcrest.mail.MailMatchers.hasDate;
import static org.devopsix.hamcrest.mail.MailMatchers.hasFrom;
import static org.devopsix.hamcrest.mail.MailMatchers.hasHeader;
import static org.devopsix.hamcrest.mail.MailMatchers.hasHeaders;
import static org.devopsix.hamcrest.mail.MailMatchers.hasMultipartContent;
import static org.devopsix.hamcrest.mail.MailMatchers.hasMultipartContentRecursive;
import static org.devopsix.hamcrest.mail.MailMatchers.hasPart;
import static org.devopsix.hamcrest.mail.MailMatchers.hasParts;
import static org.devopsix.hamcrest.mail.MailMatchers.hasSubject;
import static org.devopsix.hamcrest.mail.MailMatchers.hasTextContent;
import static org.devopsix.hamcrest.mail.MailMatchers.hasTo;
import static org.devopsix.hamcrest.mail.MailMatchers.hasValidDkimSignature;
import static org.devopsix.hamcrest.mail.MailMatchers.multipartAlternative;
import static org.devopsix.hamcrest.mail.MailMatchers.multipartContentType;
import static org.devopsix.hamcrest.mail.MailMatchers.multipartMixed;
import static org.exparity.hamcrest.date.OffsetDateTimeMatchers.isDay;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class demonstrates how the Hamcrest Mail Matchers can be used for
 * verifying an email loaded from a file.
 * 
 * @author devopsix
 *
 */
public class MessageFileExampleTest {
    
    private Message message;

    @BeforeEach
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
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void messageShouldHaveReceivedHeaders() {
        assertThat(message, hasHeaders("Received", iterableWithSize(3)));
        assertThat(message, hasHeaders("Received", (Matcher)everyItem(anyOf(startsWith("from "), startsWith("by ")))));
        assertThat(message, hasHeaders("Received", hasItems(containsString("mail-tester.com"), containsString("google.com"))));
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
    public void messageShouldHaveMultipartContent() {
        assertThat(message, not(hasTextContent()));
        assertThat(message, hasMultipartContent());
        assertThat(message, hasMultipartContent(is(multipartMixed())));
        assertThat(message, hasMultipartContent(is(multipartContentType(startsWith("multipart/mixed;")))));
        assertThat(message, hasMultipartContent(hasParts(2)));
    }
    
    @Test
    public void messageShouldHaveAlternativeTextAndHtmlContent() {
        // The message has multipart/mixed content which contains another
        // multipart/alternative part which has a plain text and an HTML part.
        assertThat(message, hasMultipartContent(hasPart(hasMultipartContent(allOf(
            multipartAlternative(),
            hasParts(2),
            hasPart(allOf(
                hasHeader("Content-Type", startsWith("text/plain;")),
                hasTextContent(containsString("Lorem ipsum"))
            )),
            hasPart(allOf(
                hasHeader("Content-Type", startsWith("text/html;")),
                hasTextContent(containsString("Lorem ipsum"))
            ))
        )))));
        // Match presence of multipart/alternative part independent of actual
        // message structure.
        assertThat(message, hasMultipartContentRecursive(allOf(
            multipartAlternative(),
            hasParts(2),
            hasPart(allOf(
                hasHeader("Content-Type", startsWith("text/plain;")),
                hasTextContent(containsString("Lorem ipsum"))
            )),
            hasPart(allOf(
                hasHeader("Content-Type", startsWith("text/html;")),
                hasTextContent(containsString("Lorem ipsum"))
            ))
        )));
    }
    
    @Test
    public void messageShouldHaveImageAttachment() {
        // The message has multipart/mixed content which contains
        // the image attachment.
        assertThat(message, hasMultipartContent(hasPart(allOf(
            hasHeader("Content-Type", startsWith("image/jpeg;")),
            hasHeader("Content-Disposition", containsString("lena.jpg")),
            hasBinaryContent(arrayWithSize(67683))
        ))));
    }
}
