package devopsix.hamcrest.email.matchers;

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

public class MessageHasSubjectTest extends MatcherTest {
    
    @Test
    public void shouldNotMatchWhenHeaderCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Subject"))).thenThrow(new MessagingException("error deocding header"));
        MessageHasSubject matcher = new MessageHasSubject(any(String.class));
        assertThat(matcher.matches(message), is(false));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldNotMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Subject"))).thenReturn(null);
        MessageHasSubject matcher = new MessageHasSubject(any(String.class));
        assertThat(matcher.matches(message), is(false));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldNotMatchWhenHeaderIsPresentTwice() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Subject"))).thenReturn(new String[] {"foo", "bar"});
        MessageHasSubject matcher = new MessageHasSubject(any(String.class));
        assertThat(matcher.matches(message), is(false));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldMatchWhenHeaderIsPresent() throws Exception {
        Message message = messageWithSubject("Foo Bar", "UTF-8");
        MessageHasSubject matcher = new MessageHasSubject(any(String.class));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void shouldMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Subject"))).thenReturn(null);
        MessageHasSubject matcher = new MessageHasSubject(nullValue(String.class));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void shouldMatchWhenHeaderWithGermanUmlautsInUtf8IsPresent() throws Exception {
        Message message = messageWithSubject("ÄÖÜäüöß", "UTF-8");
        MessageHasSubject matcher = new MessageHasSubject(equalTo("ÄÖÜäüöß"));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void shouldMatchWhenHeaderWithGermanUmlautsInIso88591IsPresent() throws Exception {
        Message message = messageWithSubject("ÄÖÜäüöß", "ISO-8859-1");
        MessageHasSubject matcher = new MessageHasSubject(equalTo("ÄÖÜäüöß"));
        assertThat(matcher.matches(message), is(true));
    }
    
    private Message messageWithSubject(String subject, String charset) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.setSubject(subject, charset);
        message.saveChanges();
        return message;
    }
}
