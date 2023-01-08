package org.devopsix.hamcrest.mail.matchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
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
        when(message.getHeader(eq("Subject"))).thenThrow(new MessagingException("error decoding header"));
        MessageHasSubject matcher = new MessageHasSubject(any(String.class));
        assertThat(message, not(matcher));
    }
    
    @Test
    public void shouldNotMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Subject"))).thenReturn(null);
        MessageHasSubject matcher = new MessageHasSubject(any(String.class));
        assertThat(message, not(matcher));
    }
    
    @Test
    public void shouldNotMatchWhenHeaderIsPresentTwice() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Subject"))).thenReturn(new String[] {"foo", "bar"});
        MessageHasSubject matcher = new MessageHasSubject(any(String.class));
        assertThat(message, not(matcher));
    }
    
    @Test
    public void shouldMatchWhenHeaderIsPresent() throws Exception {
        Message message = messageWithSubject("Foo Bar", "UTF-8");
        MessageHasSubject matcher = new MessageHasSubject(any(String.class));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Subject"))).thenReturn(null);
        MessageHasSubject matcher = new MessageHasSubject(nullValue(String.class));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchWhenHeaderWithGermanUmlautsInUtf8IsPresent() throws Exception {
        Message message = messageWithSubject("ÄÖÜäüöß", "UTF-8");
        MessageHasSubject matcher = new MessageHasSubject(equalTo("ÄÖÜäüöß"));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchWhenHeaderWithGermanUmlautsInIso88591IsPresent() throws Exception {
        Message message = messageWithSubject("ÄÖÜäüöß", "ISO-8859-1");
        MessageHasSubject matcher = new MessageHasSubject(equalTo("ÄÖÜäüöß"));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldValidateGmailMessageLongUnicodeSubject() throws Exception {
        MimeMessage message = loadMessage("message-gmail.txt");
        MessageHasSubject matcher = new MessageHasSubject(equalTo("★★★ A rather long subject with Unicode characters which should not fit into one header line ★★★"));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldValidateOutlookMessageLongUnicodeSubject() throws Exception {
        MimeMessage message = loadMessage("message-outlook.txt");
        MessageHasSubject matcher = new MessageHasSubject(equalTo("★★★ A rather long subject with Unicode characters which should not fit into one header line ★★★"));
        assertThat(message, matcher);
    }
    
    private Message messageWithSubject(String subject, String charset) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.setSubject(subject, charset);
        message.saveChanges();
        return message;
    }
}
