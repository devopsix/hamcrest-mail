package org.devopsix.hamcrest.mail.matchers;

import org.junit.jupiter.api.Test;

import javax.mail.Message;
import javax.mail.MessagingException;

import static org.devopsix.hamcrest.mail.MessageCreator.newMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageHasSenderTest {
    
    @Test
    public void shouldNotMatchWhenHeaderCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Sender"))).thenThrow(new MessagingException("error decoding header"));
        MessageHasSender matcher = new MessageHasSender(any(String.class));
        assertThat(message, not(matcher));
    }

    @Test
    public void shouldNotMatchWhenHeaderIsPresentTwice() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Sender"))).thenReturn(new String[] {"anna@example.com", "bob@example.com"});
        MessageHasSender matcher = new MessageHasSender(any(String.class));
        assertThat(message, not(matcher));
    }

    @Test
    public void shouldNotMatchWhenHeaderIsMissing() {
        Message message = newMessage().create();
        MessageHasSender matcher = new MessageHasSender(any(String.class));
        assertThat(message, not(matcher));
    }

    @Test
    public void shouldMatchWhenHeaderIsPresent() {
        Message message = newMessage().sender("anna@example.com").create();
        MessageHasSender matcher = new MessageHasSender(any(String.class));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchWhenHeaderIsMissing() {
        Message message = newMessage().create();
        MessageHasSender matcher = new MessageHasSender(nullValue(String.class));
        assertThat(message, matcher);
    }
}
