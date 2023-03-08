package org.devopsix.hamcrest.mail;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.Map;

import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static javax.mail.Message.RecipientType.TO;
import static org.devopsix.hamcrest.mail.MessageCreator.newMessage;
import static org.devopsix.hamcrest.mail.MultipartCreator.newMultipart;
import static org.devopsix.hamcrest.mail.PartCreator.newPart;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MailMatchersTest {
    
    private static final OffsetDateTime SENT_DATE = now().truncatedTo(SECONDS);
    private static final OffsetDateTime RESENT_DATE = now().plusMinutes(9);
    private static final OffsetDateTime OTHER_DATE1 = now().plusMinutes(17);
    private static final OffsetDateTime OTHER_DATE2 = now().plusMinutes(28);
    
    @Test
    public void hasDateWithMatcherShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Message> matcher = MailMatchers.hasDate(any(OffsetDateTime.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasDateWithValueShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Message> matcher = MailMatchers.hasDate(SENT_DATE);
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasFromWithMatcherShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Message> matcher = MailMatchers.hasFrom(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasFromWithValueShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Message> matcher = MailMatchers.hasFrom("joe.average@example.com");
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasSenderWithMatcherShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Message> matcher = MailMatchers.hasSender(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasSenderWithValueShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Message> matcher = MailMatchers.hasSender("joe.average@example.com");
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasReplyToWithMatcherShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Message> matcher = MailMatchers.hasReplyTo(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasReplyToWithValueShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Message> matcher = MailMatchers.hasReplyTo("joe.average@example.com");
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasToWithMatcherShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Message> matcher = MailMatchers.hasTo(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasToWithValueShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Message> matcher = MailMatchers.hasTo("joe.average@example.com");
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasCcWithMatcherShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Message> matcher = MailMatchers.hasCc(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasCcWithValueShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Message> matcher = MailMatchers.hasCc("joe.average@example.com");
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasBccWithMatcherShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Message> matcher = MailMatchers.hasBcc(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }

    @Test
    public void hasBccWithValueShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Message> matcher = MailMatchers.hasBcc("joe.average@example.com");
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }

    @Test
    public void hasRecipientsShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Message> matcher = MailMatchers.hasRecipients(iterableWithSize(3));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasRecipientsOfTypeShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Message> matcher = MailMatchers.hasRecipients(TO, iterableWithSize(1));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasAddressShouldReturnMatcher() throws Exception {
        Address address = new InternetAddress("anna@example.com");
        Matcher<Address> matcher = MailMatchers.hasAddress(is("anna@example.com"));
        assertThat(matcher, is(notNullValue()));
        assertThat(address, matcher);
    }
    
    @Test
    public void hasPersonalShouldReturnMatcher() throws Exception {
        Address address = new InternetAddress("Anna <anna@example.com>");
        Matcher<Address> matcher = MailMatchers.hasPersonal(is("Anna"));
        assertThat(matcher, is(notNullValue()));
        assertThat(address, matcher);
    }
    
    @Test
    public void hasSubjectWithMatcherShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Message> matcher = MailMatchers.hasSubject(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasSubjectWithValueShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Message> matcher = MailMatchers.hasSubject("Message from Joe");
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasHeaderWithMatcherShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Part> matcher = MailMatchers.hasHeader("Message-ID", any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasHeaderWithValueShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Part> matcher = MailMatchers.hasHeader("Message-ID", any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasHeadersWithMatcherShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Part> matcher = MailMatchers.hasHeaders("Received", hasItems(any(String.class), any(String.class)));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasDateHeaderWithMatcherShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Part> matcher = MailMatchers.hasDateHeader("Resent-Date", any(OffsetDateTime.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasDateHeadersWithMatcherShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Part> matcher = MailMatchers.hasDateHeaders("Other-Date", hasItems(any(OffsetDateTime.class), any(OffsetDateTime.class)));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasValidDkimSignatureShouldReturnMatcher() {
        Message message = createTextMessage();
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        Map<String, String> publicKeys = singletonMap("foo._domainkey.example.com", "k=rsa; p=" + publicKey);
        Matcher<Message> matcher = MailMatchers.hasValidDkimSignature(publicKeys);
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasTextContentShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Part> matcher = MailMatchers.hasTextContent();
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasTextContentWithMatcherShouldReturnMatcher() {
        Message message = createTextMessage();
        Matcher<Part> matcher = MailMatchers.hasTextContent(equalTo("Lorem ipsum"));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasBinaryContentWithMatcherShouldReturnMatcher() {
        Part part = newPart().content(new byte[] {1,2,3}).create();
        Matcher<Part> matcher = MailMatchers.hasBinaryContent(not(emptyArray()));
        assertThat(matcher, is(notNullValue()));
        assertThat(part, matcher);
    }
    
    @Test
    public void hasMultipartContentShouldReturnMatcher() {
        Message message = createMultipartMessage();
        Matcher<Part> matcher = MailMatchers.hasMultipartContent();
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void hasMultipartContentWithMatcherShouldReturnMatcher() {
        Message message = createMultipartMessage();
        Matcher<Part> matcher = MailMatchers.hasMultipartContent((Matcher)anything());
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void hasMultipartContentRecursiveShouldReturnMatcher() {
        Message message = createMultipartMessage();
        Matcher<Part> matcher = MailMatchers.hasMultipartContentRecursive((Matcher)anything());
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void multipartMixedShouldReturnMatcher() {
        Multipart message = createMultipartMixed();
        Matcher<Multipart> matcher = MailMatchers.multipartMixed();
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void multipartAlternativeShouldReturnMatcher() {
        Multipart message = createMultipartAlternative();
        Matcher<Multipart> matcher = MailMatchers.multipartAlternative();
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void multipartRelatedShouldReturnMatcher() {
        Multipart message = createMultipartRelated();
        Matcher<Multipart> matcher = MailMatchers.multipartRelated();
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void multipartContentTypeShouldReturnMatcher() {
        Multipart message = createMultipartAlternative();
        Matcher<Multipart> matcher = MailMatchers.multipartContentType(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void hasPartsShouldReturnMatcher() {
        Multipart message = createMultipartMixed();
        Matcher<Multipart> matcher = MailMatchers.hasParts((Matcher)anything());
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void hasPartShouldReturnMatcher() {
        Multipart message = createMultipartMixed();
        Matcher<Multipart> matcher = MailMatchers.hasPart((Matcher)anything());
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasPartsSizeShouldReturnMatcher() {
        Multipart message = createMultipartMixed();
        Matcher<Multipart> matcher = MailMatchers.hasParts(2);
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }

    private static final KeyPair keyPair;
    static {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            keyPair = keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    private Message createTextMessage() {
        return message()
            .text("Lorem ipsum")
            .dkimSignature(keyPair, "example.com", "foo",
                asList("mime-version", "from", "date", "message-id", "subject", "to"))
            .create();
    }
    
    private Message createMultipartMessage() {
        return message()
            .multipart(newMultipart()
                .bodyParts(newPart()
                    .content(new byte[] {1,2,3}).create()
                ).create())
            .dkimSignature(keyPair, "example.com", "foo",
                asList("mime-version", "from", "date", "message-id", "subject", "to"))
            .create();
    }

    private Multipart createMultipartMixed() {
        return newMultipart().subtype("mixed").bodyParts(
            newPart().content(new byte[] {1,2,3}).create(),
            newPart().content(new byte[] {1,2,3}).create()
        ).create();
    }
    
    private Multipart createMultipartAlternative() {
        return newMultipart().subtype("alternative").bodyParts(
            newPart().content(new byte[] {1,2,3}).create(),
            newPart().content(new byte[] {1,2,3}).create()
        ).create();
    }
    
    private Multipart createMultipartRelated() {
        return newMultipart().subtype("related").bodyParts(
            newPart().content(new byte[] {1,2,3}).create(),
            newPart().content(new byte[] {1,2,3}).create()
        ).create();
    }
    
    private MessageCreator message() {
        return newMessage()
            .date(SENT_DATE)
            .from("joe.average@example.com")
            .sender("joe.average@example.com")
            .replyTo("joe.average@example.com")
            .to("joe.average@example.com")
            .cc("joe.average@example.com")
            .bcc("joe.average@example.com")
            .header("Resent-Date", RESENT_DATE)
            .header("Other-Date", OTHER_DATE1)
            .header("Other-Date", OTHER_DATE2)
            .header("Received", "from foo by bar")
            .subject("Message from Joe");
    }

    private static OffsetDateTime now() {
        return OffsetDateTime.now().truncatedTo(SECONDS);
    }
}
