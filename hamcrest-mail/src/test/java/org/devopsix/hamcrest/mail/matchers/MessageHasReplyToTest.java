package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.MessageCreator.newMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.mail.Message;
import javax.mail.MessagingException;
import org.junit.jupiter.api.Test;

public class MessageHasReplyToTest {

  @Test
  public void shouldNotMatchWhenHeaderCannotBeExtracted() throws Exception {
    Message message = mock(Message.class);
    when(message.getHeader(eq("Reply-To"))).thenThrow(
        new MessagingException("error decoding header"));
    MessageHasReplyTo matcher = new MessageHasReplyTo(any(String.class));
    assertThat(message, not(matcher));
  }

  @Test
  public void shouldNotMatchWhenHeaderIsPresentTwice() throws Exception {
    Message message = mock(Message.class);
    when(message.getHeader(eq("Reply-To"))).thenReturn(
        new String[] {"anna@example.com", "bob@example.com"});
    MessageHasReplyTo matcher = new MessageHasReplyTo(any(String.class));
    assertThat(message, not(matcher));
  }

  @Test
  public void shouldNotMatchWhenHeaderIsMissing() {
    Message message = newMessage().create();
    MessageHasReplyTo matcher = new MessageHasReplyTo(any(String.class));
    assertThat(message, not(matcher));
  }

  @Test
  public void shouldMatchWhenHeaderIsPresent() {
    Message message = newMessage().replyTo("anna@example.com").create();
    MessageHasReplyTo matcher = new MessageHasReplyTo(any(String.class));
    assertThat(message, matcher);
  }

  @Test
  public void shouldMatchWhenHeaderIsMissing() {
    Message message = newMessage().create();
    MessageHasReplyTo matcher = new MessageHasReplyTo(nullValue(String.class));
    assertThat(message, matcher);
  }
}
