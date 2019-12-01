package devopsix.hamcrest.email.matchers;

import static javax.mail.Message.RecipientType.BCC;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
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

public class MessageHasBccTest extends MatcherTest {
    
    @Test
    public void shouldNotMatchWhenHeaderCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Bcc"))).thenThrow(new MessagingException("error deocding header"));
        MessageHasBcc matcher = new MessageHasBcc(any(String.class));
        assertThat(matcher.matches(message), is(false));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldNotMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Bcc"))).thenReturn(null);
        MessageHasBcc matcher = new MessageHasBcc(any(String.class));
        assertThat(matcher.matches(message), is(false));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldNotMatchWhenHeaderIsPresentTwice() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Bcc"))).thenReturn(new String[] {"anna@example.com", "bob@example.com"});
        MessageHasBcc matcher = new MessageHasBcc(any(String.class));
        assertThat(matcher.matches(message), is(false));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldMatchWhenHeaderIsPresent() throws Exception {
        Message message = messageWithBcc("anna@example.com");
        MessageHasBcc matcher = new MessageHasBcc(any(String.class));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void shouldMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Bcc"))).thenReturn(null);
        MessageHasBcc matcher = new MessageHasBcc(nullValue(String.class));
        assertThat(matcher.matches(message), is(true));
    }
    
    private Message messageWithBcc(String bcc) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.setRecipient(BCC, new InternetAddress(bcc));
        message.saveChanges();
        return message;
    }
}
