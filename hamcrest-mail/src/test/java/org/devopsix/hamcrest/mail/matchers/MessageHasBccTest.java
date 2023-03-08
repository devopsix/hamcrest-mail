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

public class MessageHasBccTest {
    
    @Test
    public void shouldNotMatchWhenHeaderCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Bcc"))).thenThrow(new MessagingException("error decoding header"));
        MessageHasBcc matcher = new MessageHasBcc(any(String.class));
        assertThat(message, not(matcher));
    }

    @Test
    public void shouldNotMatchWhenHeaderIsPresentTwice() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Bcc"))).thenReturn(new String[] {"anna@example.com", "bob@example.com"});
        MessageHasBcc matcher = new MessageHasBcc(any(String.class));
        assertThat(message, not(matcher));
    }

    @Test
    public void shouldNotMatchWhenHeaderIsMissing() {
        Message message = newMessage().create();
        MessageHasBcc matcher = new MessageHasBcc(any(String.class));
        assertThat(message, not(matcher));
    }

    @Test
    public void shouldMatchWhenHeaderIsPresent() {
        Message message = newMessage().bcc("anna@example.com").create();
        MessageHasBcc matcher = new MessageHasBcc(any(String.class));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchWhenHeaderIsMissing() {
        Message message = newMessage().create();
        MessageHasBcc matcher = new MessageHasBcc(nullValue(String.class));
        assertThat(message, matcher);
    }
}
