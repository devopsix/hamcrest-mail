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

public class MessageHasCcTest {
    
    @Test
    public void shouldNotMatchWhenHeaderCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Cc"))).thenThrow(new MessagingException("error decoding header"));
        MessageHasCc matcher = new MessageHasCc(any(String.class));
        assertThat(message, not(matcher));
    }

    @Test
    public void shouldNotMatchWhenHeaderIsPresentTwice() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Cc"))).thenReturn(new String[] {"anna@example.com", "bob@example.com"});
        MessageHasCc matcher = new MessageHasCc(any(String.class));
        assertThat(message, not(matcher));
    }

    @Test
    public void shouldNotMatchWhenHeaderIsMissing() {
        Message message = newMessage().create();
        MessageHasCc matcher = new MessageHasCc(any(String.class));
        assertThat(message, not(matcher));
    }

    @Test
    public void shouldMatchWhenHeaderIsPresent() {
        Message message = newMessage().cc("anna@example.com").create();
        MessageHasCc matcher = new MessageHasCc(any(String.class));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchWhenHeaderIsMissing() {
        Message message = newMessage().create();
        MessageHasCc matcher = new MessageHasCc(nullValue(String.class));
        assertThat(message, matcher);
    }
}
