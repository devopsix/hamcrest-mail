package devopsix.hamcrest.email.matchers;

import static javax.mail.internet.MimeUtility.encodeText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
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

public class MessageHasHeaderTest extends MatcherTest {
    
    @Test
    public void shouldNotMatchWhenHeaderCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Message-ID"))).thenThrow(new MessagingException("error deocding header"));
        MessageHasHeader matcher = new MessageHasHeader("Message-ID", any(String.class));
        assertThat(matcher.matches(message), is(false));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldNotMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Message-ID"))).thenReturn(null);
        MessageHasHeader matcher = new MessageHasHeader("Message-ID", any(String.class));
        assertThat(matcher.matches(message), is(false));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldNotMatchWhenHeaderIsPresentTwice() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Message-ID"))).thenReturn(new String[] {"abc123@example.com", "def456@example.com"});
        MessageHasHeader matcher = new MessageHasHeader("Message-ID", any(String.class));
        assertThat(matcher.matches(message), is(false));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldMatchWhenHeaderIsPresent() throws Exception {
        Message message = messageWithHeader("Message-ID", "abc123@example.com");
        MessageHasHeader matcher = new MessageHasHeader("Message-ID", any(String.class));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void shouldMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Message-ID"))).thenReturn(null);
        MessageHasHeader matcher = new MessageHasHeader("Message-ID", nullValue(String.class));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void shouldMatchWhenHeaderWithGermanUmlautsInUtf8IsPresent() throws Exception {
        Message message = messageWithHeader("Some-Header", encodeText("ÄÖÜäüöß", "UTF-8", null));
        MessageHasHeader matcher = new MessageHasHeader("Some-Header", equalTo("ÄÖÜäüöß"));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void shouldMatchWhenHeaderWithGermanUmlautsInIso88591IsPresent() throws Exception {
        Message message = messageWithHeader("Some-Header", encodeText("ÄÖÜäüöß", "ISO-8859-1", null));
        MessageHasHeader matcher = new MessageHasHeader("Some-Header", equalTo("ÄÖÜäüöß"));
        assertThat(matcher.matches(message), is(true));
    }
    
    private Message messageWithHeader(String name, String value) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.setHeader(name, value);
        message.saveChanges();
        return message;
    }
}
