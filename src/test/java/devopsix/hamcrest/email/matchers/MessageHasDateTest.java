package devopsix.hamcrest.email.matchers;

import static java.time.OffsetDateTime.now;
import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;

public class MessageHasDateTest extends MatcherTest {
    
    @Test
    public void shouldNotMatchWhenHeaderCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Date"))).thenThrow(new MessagingException("error deocding header"));
        MessageHasDate matcher = new MessageHasDate(any(OffsetDateTime.class));
        assertThat(message, not(matcher));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldNotMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Date"))).thenReturn(null);
        MessageHasDate matcher = new MessageHasDate(any(OffsetDateTime.class));
        assertThat(message, not(matcher));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldNotMatchWhenHeaderIsPresentTwice() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Date"))).thenReturn(new String[] {now().format(RFC_1123_DATE_TIME), now().format(RFC_1123_DATE_TIME)});
        MessageHasDate matcher = new MessageHasDate(any(OffsetDateTime.class));
        assertThat(message, not(matcher));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldNotMatchWhenHeaderValueIsMalformed() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Date"))).thenReturn(new String[] {"foobar"});
        MessageHasDate matcher = new MessageHasDate(any(OffsetDateTime.class));
        assertThat(message, not(matcher));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldMatchWhenHeaderIsPresent() throws Exception {
        Message message = messageWithDate();
        MessageHasDate matcher = new MessageHasDate(any(OffsetDateTime.class));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Date"))).thenReturn(null);
        MessageHasDate matcher = new MessageHasDate(nullValue(OffsetDateTime.class));
        assertThat(message, matcher);
    }
    
    private Message messageWithDate() throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
}
