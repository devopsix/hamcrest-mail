package org.devopsix.hamcrest.mail.matchers;

import org.junit.jupiter.api.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.devopsix.hamcrest.mail.MessageCreator.newMessage;
import static org.devopsix.hamcrest.mail.MessageLoader.loadMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageHasSubjectTest {
    
    @Test
    public void shouldNotMatchWhenHeaderCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Subject"))).thenThrow(new MessagingException("error decoding header"));
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
    public void shouldNotMatchWhenHeaderIsMissing() {
        Message message = newMessage().create();
        MessageHasSubject matcher = new MessageHasSubject(any(String.class));
        assertThat(message, not(matcher));
    }

    @Test
    public void shouldMatchWhenHeaderIsPresent() {
        Message message = newMessage().subject("Foo Bar").create();
        MessageHasSubject matcher = new MessageHasSubject(any(String.class));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchWhenHeaderIsMissing() {
        Message message = newMessage().create();
        MessageHasSubject matcher = new MessageHasSubject(nullValue(String.class));
        assertThat(message, matcher);
    }

    @Test
    public void shouldValidateGmailMessageLongUnicodeSubject() {
        MimeMessage message = loadMessage("message-gmail.txt");
        MessageHasSubject matcher = new MessageHasSubject(equalTo("★★★ A rather long subject with Unicode characters which should not fit into one header line ★★★"));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldValidateOutlookMessageLongUnicodeSubject() {
        MimeMessage message = loadMessage("message-outlook.txt");
        MessageHasSubject matcher = new MessageHasSubject(equalTo("★★★ A rather long subject with Unicode characters which should not fit into one header line ★★★"));
        assertThat(message, matcher);
    }
}
