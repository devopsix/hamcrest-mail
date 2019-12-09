package org.devopsix.hamcrest.mail.matchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.devopsix.hamcrest.mail.matchers.MessageHasReplyTo;
import org.junit.jupiter.api.Test;

public class MessageHasReplyToTest extends MatcherTest {
    
    @Test
    public void shouldNotMatchWhenHeaderCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Reply-To"))).thenThrow(new MessagingException("error deocding header"));
        MessageHasReplyTo matcher = new MessageHasReplyTo(any(String.class));
        assertThat(message, not(matcher));
    }
    
    @Test
    public void shouldNotMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Reply-To"))).thenReturn(null);
        MessageHasReplyTo matcher = new MessageHasReplyTo(any(String.class));
        assertThat(message, not(matcher));
    }
    
    @Test
    public void shouldNotMatchWhenHeaderIsPresentTwice() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Reply-To"))).thenReturn(new String[] {"anna@example.com", "bob@example.com"});
        MessageHasReplyTo matcher = new MessageHasReplyTo(any(String.class));
        assertThat(message, not(matcher));
    }
    
    @Test
    public void shouldMatchWhenHeaderIsPresent() throws Exception {
        Message message = messageWithReplyTo("anna@example.com");
        MessageHasReplyTo matcher = new MessageHasReplyTo(any(String.class));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Reply-To"))).thenReturn(null);
        MessageHasReplyTo matcher = new MessageHasReplyTo(nullValue(String.class));
        assertThat(message, matcher);
    }
    
    private Message messageWithReplyTo(String replyTo) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.setReplyTo(new Address[] {new InternetAddress(replyTo)});
        message.saveChanges();
        return message;
    }
}
