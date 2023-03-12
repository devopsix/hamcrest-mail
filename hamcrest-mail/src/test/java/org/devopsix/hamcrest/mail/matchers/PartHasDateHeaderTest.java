package org.devopsix.hamcrest.mail.matchers;

import static java.time.OffsetDateTime.now;
import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;
import static org.devopsix.hamcrest.mail.MessageCreator.newMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import javax.mail.Message;
import javax.mail.MessagingException;
import org.junit.jupiter.api.Test;

public class PartHasDateHeaderTest {

  @Test
  public void shouldNotMatchWhenHeaderCannotBeExtracted() throws Exception {
    Message message = mock(Message.class);
    when(message.getHeader(eq("X-Custom"))).thenThrow(
        new MessagingException("error decoding header"));
    PartHasDateHeader matcher = new PartHasDateHeader("X-Custom", any(OffsetDateTime.class));
    assertThat(message, not(matcher));
  }

  @Test
  public void shouldNotMatchWhenHeaderIsMissing() throws Exception {
    Message message = newMessage().create();
    PartHasDateHeader matcher = new PartHasDateHeader("X-Custom", any(OffsetDateTime.class));
    assertThat(message, not(matcher));
  }

  @Test
  public void shouldNotMatchWhenHeaderIsPresentTwice() throws Exception {
    Message message = newMessage()
        .header("X-Custom", now().format(RFC_1123_DATE_TIME))
        .header("X-Custom", now().format(RFC_1123_DATE_TIME))
        .create();
    PartHasDateHeader matcher = new PartHasDateHeader("X-Custom", any(OffsetDateTime.class));
    assertThat(message, not(matcher));
  }

  @Test
  public void shouldNotMatchWhenHeaderValueIsMalformed() throws Exception {
    Message message = mock(Message.class);
    when(message.getHeader(eq("X-Custom"))).thenReturn(new String[] {"foobar"});
    PartHasDateHeader matcher = new PartHasDateHeader("X-Custom", any(OffsetDateTime.class));
    assertThat(message, not(matcher));
  }

  @Test
  public void shouldMatchWhenHeaderIsPresent() throws Exception {
    Message message = newMessage()
        .header("X-Custom", now().format(RFC_1123_DATE_TIME))
        .create();
    PartHasDateHeader matcher = new PartHasDateHeader("X-Custom", any(OffsetDateTime.class));
    assertThat(message, matcher);
  }

  @Test
  public void shouldMatchWhenHeaderIsMissing() throws Exception {
    Message message = newMessage()
        .create();
    PartHasDateHeader matcher = new PartHasDateHeader("X-Custom", nullValue(OffsetDateTime.class));
    assertThat(message, matcher);
  }
}
