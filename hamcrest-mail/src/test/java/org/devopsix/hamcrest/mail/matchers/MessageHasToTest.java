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

public class MessageHasToTest {

  @Test
  public void shouldNotMatchWhenHeaderCannotBeExtracted() throws Exception {
    Message message = mock(Message.class);
    when(message.getHeader(eq("To"))).thenThrow(new MessagingException("error decoding header"));
    MessageHasTo matcher = new MessageHasTo(any(String.class));
    assertThat(message, not(matcher));
  }

  @Test
  public void shouldNotMatchWhenHeaderIsPresentTwice() throws Exception {
    Message message = mock(Message.class);
    when(message.getHeader(eq("To"))).thenReturn(
        new String[] {"anna@example.com", "bob@example.com"});
    MessageHasTo matcher = new MessageHasTo(any(String.class));
    assertThat(message, not(matcher));
  }

  @Test
  public void shouldNotMatchWhenHeaderIsMissing() {
    Message message = newMessage().create();
    MessageHasTo matcher = new MessageHasTo(any(String.class));
    assertThat(message, not(matcher));
  }

  @Test
  public void shouldMatchWhenHeaderIsPresent() {
    Message message = newMessage().to("anna@example.com").create();
    MessageHasTo matcher = new MessageHasTo(any(String.class));
    assertThat(message, matcher);
  }

  @Test
  public void shouldMatchWhenHeaderIsMissing() throws Exception {
    Message message = newMessage().create();
    MessageHasTo matcher = new MessageHasTo(nullValue(String.class));
    assertThat(message, matcher);
  }
}
