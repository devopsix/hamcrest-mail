package org.devopsix.hamcrest.mail;

import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.Collections.singletonMap;
import static javax.mail.Message.RecipientType.BCC;
import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.notNullValue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.james.jdkim.DKIMSigner;
import org.apache.james.jdkim.exceptions.FailException;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

public class MessageMatchersTest {
    
    private static final OffsetDateTime SENT_DATE = now().truncatedTo(SECONDS);
    private static final OffsetDateTime RESENT_DATE = now().plusMinutes(9);
    private static final OffsetDateTime OTHER_DATE1 = now().plusMinutes(17);
    private static final OffsetDateTime OTHER_DATE2 = now().plusMinutes(28);
    
    @Test
    public void hasDateOffsetDateTimeWithMatcherShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Message> matcher = MessageMatchers.hasDate(any(OffsetDateTime.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasDateOffsetDateTimeWithValueShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Message> matcher = MessageMatchers.hasDate(SENT_DATE);
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasFromWithMatcherShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Message> matcher = MessageMatchers.hasFrom(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasFromWithValueShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Message> matcher = MessageMatchers.hasFrom("joe.average@example.com");
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasSenderWithMatcherShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Message> matcher = MessageMatchers.hasSender(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasSenderWithValueShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Message> matcher = MessageMatchers.hasSender("joe.average@example.com");
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasReplyToWithMatcherShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Message> matcher = MessageMatchers.hasReplyTo(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasReplyToWithValueShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Message> matcher = MessageMatchers.hasReplyTo("joe.average@example.com");
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasToWithMatcherShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Message> matcher = MessageMatchers.hasTo(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasToWithValueShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Message> matcher = MessageMatchers.hasTo("joe.average@example.com");
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasCcWithMatcherShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Message> matcher = MessageMatchers.hasCc(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasCcWithValueShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Message> matcher = MessageMatchers.hasCc("joe.average@example.com");
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasBccWithMatcherShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Message> matcher = MessageMatchers.hasBcc(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasRecipientsShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Message> matcher = MessageMatchers.hasRecipients(iterableWithSize(3));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasRecipientsOfTypeShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Message> matcher = MessageMatchers.hasRecipients(TO, iterableWithSize(1));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasAddressShouldReturnMatcher() throws Exception {
        Address address = new InternetAddress("anna@example.com");
        Matcher<Address> matcher = MessageMatchers.hasAddress(is("anna@example.com"));
        assertThat(matcher, is(notNullValue()));
        assertThat(address, matcher);
    }
    
    @Test
    public void hasPersonalShouldReturnMatcher() throws Exception {
        Address address = new InternetAddress("Anna <anna@example.com>");
        Matcher<Address> matcher = MessageMatchers.hasPersonal(is("Anna"));
        assertThat(matcher, is(notNullValue()));
        assertThat(address, matcher);
    }
    
    @Test
    public void hasSubjectWithMatcherShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Message> matcher = MessageMatchers.hasSubject(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasSubjectWithValueShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Message> matcher = MessageMatchers.hasSubject("Message from Joe");
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasHeaderWithMatcherShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Part> matcher = MessageMatchers.hasHeader("Message-ID", any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasHeaderWithValueShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Part> matcher = MessageMatchers.hasHeader("Message-ID", any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasHeadersWithMatcherShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Part> matcher = MessageMatchers.hasHeaders("Received", hasItems(any(String.class), any(String.class)));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasDateHeaderWithMatcherShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Part> matcher = MessageMatchers.hasDateHeader("Resent-Date", any(OffsetDateTime.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasDateHeadersWithMatcherShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Part> matcher = MessageMatchers.hasDateHeaders("Other-Date", hasItems(any(OffsetDateTime.class), any(OffsetDateTime.class)));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasValidDkimSignatureShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        Map<String, String> publicKeys = singletonMap("foo._domainkey.example.com", "k=rsa; p=" + publicKey);
        Matcher<Message> matcher = MessageMatchers.hasValidDkimSignature(publicKeys);
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasTextBodyShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Message> matcher = MessageMatchers.hasTextBody();
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasTextBodyWithMatcherShouldReturnMatcher() throws Exception {
        Message message = createTextMessage();
        Matcher<Message> matcher = MessageMatchers.hasTextBody(equalTo("Lorem ipsum"));
        assertThat(matcher, is(notNullValue()));
        assertThat(message, matcher);
    }
    
    @Test
    public void hasMultipartBodyShouldReturnMatcher() throws Exception {
        Message message = createMultipartMessage();
        Matcher<Message> matcher = MessageMatchers.hasMultipartBody();
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
    
    private Message createTextMessage() throws IOException, FailException, MessagingException {
        Message message = createMessage();
        message.setText("Lorem ipsum");
        signMessage(message);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        message.writeTo(buffer);
        return new MimeMessage(message.getSession(), new ByteArrayInputStream(buffer.toByteArray()));
    }
    
    private Message createMultipartMessage() throws IOException, FailException, MessagingException {
        Message message = createMessage();
        InternetHeaders headers = new InternetHeaders();
        headers.addHeader("Content-Type", "application/octet-stream");
        message.setContent(new MimeMultipart(new MimeBodyPart(headers, new byte[] {1,2,3})));
        signMessage(message);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        message.writeTo(buffer);
        return new MimeMessage(message.getSession(), new ByteArrayInputStream(buffer.toByteArray()));
    }
    
    private MimeMessage createMessage() throws IOException, FailException, MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.setSentDate(new Date(SENT_DATE.toInstant().toEpochMilli()));
        message.setFrom("joe.average@example.com");
        message.setSender(new InternetAddress("joe.average@example.com"));
        message.setReplyTo(new Address[] {new InternetAddress("joe.average@example.com")});
        message.setRecipient(TO, new InternetAddress("joe.average@example.com"));
        message.setRecipient(CC, new InternetAddress("joe.average@example.com"));
        message.setRecipient(BCC, new InternetAddress("joe.average@example.com"));
        message.setHeader("Resent-Date", RESENT_DATE.format(RFC_1123_DATE_TIME));
        message.addHeader("Other-Date", OTHER_DATE1.format(RFC_1123_DATE_TIME));
        message.addHeader("Other-Date", OTHER_DATE2.format(RFC_1123_DATE_TIME));
        message.addHeader("Received", "from foo by bar");
        message.setSubject("Message from Joe");
        return message;
    }
    
    private void signMessage(Message message) throws IOException, FailException, MessagingException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        message.writeTo(buffer);
        DKIMSigner signer = new DKIMSigner("v=1; a=rsa-sha256; d=example.com; s=foo; h=mime-version:from:date:message-id:subject:to; b=; bh=;", keyPair.getPrivate());
        String dkimHeader = signer.sign(new ByteArrayInputStream(buffer.toByteArray()));
        message.setHeader("DKIM-Signature", dkimHeader.substring("DKIM-Signature:".length()).trim());
    }
    
    private static OffsetDateTime now() {
        return OffsetDateTime.now().truncatedTo(SECONDS);
    }
}
