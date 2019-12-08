package devopsix.hamcrest.email.matchers;

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
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;

public class MessageHasFromTest extends MatcherTest {
    
    @Test
    public void shouldNotMatchWhenHeaderCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("From"))).thenThrow(new MessagingException("error deocding header"));
        MessageHasFrom matcher = new MessageHasFrom(any(String.class));
        assertThat(matcher.matches(message), is(false));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldNotMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("From"))).thenReturn(null);
        MessageHasFrom matcher = new MessageHasFrom(any(String.class));
        assertThat(matcher.matches(message), is(false));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldNotMatchWhenHeaderIsPresentTwice() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("From"))).thenReturn(new String[] {"anna@example.com", "bob@example.com"});
        MessageHasFrom matcher = new MessageHasFrom(any(String.class));
        assertThat(matcher.matches(message), is(false));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldMatchWhenHeaderIsPresent() throws Exception {
        Message message = messageWithfrom("anna@example.com");
        MessageHasFrom matcher = new MessageHasFrom(any(String.class));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("From"))).thenReturn(null);
        MessageHasFrom matcher = new MessageHasFrom(nullValue(String.class));
        assertThat(message, matcher);
    }
    
    private Message messageWithfrom(String from) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.setFrom(from);
        message.saveChanges();
        return message;
    }
}
