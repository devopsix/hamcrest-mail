package devopsix.hamcrest.email.matchers;

import static javax.mail.Message.RecipientType.CC;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;

public class MessageHasCcTest extends MatcherTest {
    
    @Test
    public void shouldNotMatchWhenHeaderCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Cc"))).thenThrow(new MessagingException("error deocding header"));
        MessageHasCc matcher = new MessageHasCc(any(String.class));
        assertThat(message, not(matcher));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldNotMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Cc"))).thenReturn(null);
        MessageHasCc matcher = new MessageHasCc(any(String.class));
        assertThat(message, not(matcher));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldNotMatchWhenHeaderIsPresentTwice() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Cc"))).thenReturn(new String[] {"anna@example.com", "bob@example.com"});
        MessageHasCc matcher = new MessageHasCc(any(String.class));
        assertThat(message, not(matcher));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldMatchWhenHeaderIsPresent() throws Exception {
        Message message = messageWithCc("anna@example.com");
        MessageHasCc matcher = new MessageHasCc(any(String.class));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Cc"))).thenReturn(null);
        MessageHasCc matcher = new MessageHasCc(nullValue(String.class));
        assertThat(message, matcher);
    }
    
    private Message messageWithCc(String cc) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.setRecipient(CC, new InternetAddress(cc));
        message.saveChanges();
        return message;
    }
}
