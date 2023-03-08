package org.devopsix.hamcrest.mail.matchers;

import org.junit.jupiter.api.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.time.OffsetDateTime;

import static java.time.OffsetDateTime.now;
import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;
import static org.devopsix.hamcrest.mail.MessageCreator.newMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageHasDateTest {
    
    @Test
    public void shouldNotMatchWhenHeaderCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Date"))).thenThrow(new MessagingException("error decoding header"));
        MessageHasDate matcher = new MessageHasDate(any(OffsetDateTime.class));
        assertThat(message, not(matcher));
    }

    @Test
    public void shouldNotMatchWhenHeaderIsPresentTwice() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Date"))).thenReturn(new String[] {now().format(RFC_1123_DATE_TIME), now().format(RFC_1123_DATE_TIME)});
        MessageHasDate matcher = new MessageHasDate(any(OffsetDateTime.class));
        assertThat(message, not(matcher));
    }

    @Test
    public void shouldNotMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Date"))).thenReturn(null);
        MessageHasDate matcher = new MessageHasDate(any(OffsetDateTime.class));
        assertThat(message, not(matcher));
    }

    @Test
    public void shouldNotMatchWhenHeaderValueIsMalformed() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Date"))).thenReturn(new String[] {"foobar"});
        MessageHasDate matcher = new MessageHasDate(any(OffsetDateTime.class));
        assertThat(message, not(matcher));
    }
    
    @Test
    public void shouldMatchWhenHeaderIsPresent() {
        Message message = newMessage().date(now()).create();
        MessageHasDate matcher = new MessageHasDate(any(OffsetDateTime.class));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Date"))).thenReturn(null);
        MessageHasDate matcher = new MessageHasDate(nullValue(OffsetDateTime.class));
        assertThat(message, matcher);
    }
}
