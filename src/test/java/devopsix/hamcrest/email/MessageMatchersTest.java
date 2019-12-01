package devopsix.hamcrest.email;

import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessageMatchersTest {
    
    private static final OffsetDateTime SENT_DATE = now().truncatedTo(SECONDS);
    private static final OffsetDateTime RESENT_DATE = now().plusMinutes(9);
    private static final OffsetDateTime OTHER_DATE1 = now().plusMinutes(17);
    private static final OffsetDateTime OTHER_DATE2 = now().plusMinutes(28);
    private static final OffsetDateTime RECEIVED_DATE = now().plusMinutes(36);
    
    private Message message;
    
    @BeforeEach
    public void createMessage() throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.setSentDate(new Date(SENT_DATE.toInstant().toEpochMilli()));
        message.setFrom("joe.average@example.com");
        message.setSender(new InternetAddress("joe.average@example.com"));
        message.setReplyTo(new Address[] {new InternetAddress("joe.average@example.com")});
        message.setRecipient(RecipientType.TO, new InternetAddress("joe.average@example.com"));
        message.setRecipient(RecipientType.CC, new InternetAddress("joe.average@example.com"));
        message.setRecipient(RecipientType.BCC, new InternetAddress("joe.average@example.com"));
        message.setHeader("Resent-Date", RESENT_DATE.format(RFC_1123_DATE_TIME));
        message.addHeader("Other-Date", OTHER_DATE1.format(RFC_1123_DATE_TIME));
        message.addHeader("Other-Date", OTHER_DATE2.format(RFC_1123_DATE_TIME));
        message.addHeader("Received", RECEIVED_DATE.format(RFC_1123_DATE_TIME));
        message.setSubject("Message from Joe");
        message.saveChanges();
        this.message = message;
    }
    
    @Test
    public void hasDateOffsetDateTimeWithMatcherShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasDate(any(OffsetDateTime.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasDateOffsetDateTimeWithValueShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasDate(SENT_DATE);
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasFromWithMatcherShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasFrom(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasFromWithValueShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasFrom("joe.average@example.com");
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasSenderWithMatcherShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasSender(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasSenderWithValueShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasSender("joe.average@example.com");
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasReplyToWithMatcherShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasReplyTo(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasReplyToWithValueShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasReplyTo("joe.average@example.com");
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasToWithMatcherShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasTo(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasToWithValueShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasTo("joe.average@example.com");
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasCcWithMatcherShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasCc(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasCcWithValueShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasCc("joe.average@example.com");
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasBccWithMatcherShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasBcc(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasSubjectWithMatcherShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasSubject(any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasSubjectWithValueShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasSubject("Message from Joe");
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasHeaderWithMatcherShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasHeader("Message-ID", any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasHeaderWithValueShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasHeader("Message-ID", any(String.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasHeadersWithMatcherShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasHeaders("Received", hasItems(any(String.class), any(String.class)));
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasDateHeaderWithMatcherShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasDateHeader("Resent-Date", any(OffsetDateTime.class));
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void hasDateHeadersWithMatcherShouldReturnMatcher() throws Exception {
        Matcher<Message> matcher = MessageMatchers.hasDateHeaders("Other-Date", hasItems(any(OffsetDateTime.class), any(OffsetDateTime.class)));
        assertThat(matcher, is(notNullValue()));
        assertThat(matcher.matches(message), is(true));
    }
    
    private static OffsetDateTime now() {
        return OffsetDateTime.now().truncatedTo(SECONDS);
    }
}
