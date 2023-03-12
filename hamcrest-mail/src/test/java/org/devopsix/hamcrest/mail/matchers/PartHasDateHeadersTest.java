package org.devopsix.hamcrest.mail.matchers;

import static java.time.OffsetDateTime.now;
import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;
import static org.devopsix.hamcrest.mail.MessageCreator.newMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterableOf;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import javax.mail.Message;
import javax.mail.MessagingException;
import org.junit.jupiter.api.Test;

public class PartHasDateHeadersTest {

  @Test
  public void shouldNotMatchWhenHeaderCannotBeExtracted() throws Exception {
    Message message = mock(Message.class);
    when(message.getHeader(eq("X-Custom"))).thenThrow(
        new MessagingException("error decoding header"));
    PartHasDateHeaders matcher =
        new PartHasDateHeaders("X-Custom", emptyIterableOf(OffsetDateTime.class));
    assertThat(message, not(matcher));
  }

  @Test
  public void shouldMatchWhenNoHeaderIsPresent() {
    Message message = newMessage().create();
    PartHasDateHeaders matcher =
        new PartHasDateHeaders("X-Custom", emptyIterableOf(OffsetDateTime.class));
    assertThat(message, matcher);
  }

  @Test
  public void shouldMatchWhenOneHeaderIsPresent() {
    Message message = newMessage()
        .header("X-Custom", now().format(RFC_1123_DATE_TIME))
        .create();
    PartHasDateHeaders matcher = new PartHasDateHeaders("X-Custom", iterableWithSize(1));
    assertThat(message, matcher);
  }

  @Test
  public void shouldMatchWhenTwoHeadersArePresent() {
    Message message = newMessage()
        .header("X-Custom", now().format(RFC_1123_DATE_TIME))
        .header("X-Custom", now().format(RFC_1123_DATE_TIME))
        .create();
    PartHasDateHeaders matcher = new PartHasDateHeaders("X-Custom", iterableWithSize(2));
    assertThat(message, matcher);
  }

  @Test
  public void shouldNotMatchWhenHeaderValueIsMalformed() throws Exception {
    Message message = mock(Message.class);
    when(message.getHeader(eq("X-Custom"))).thenReturn(new String[] {"foobar"});
    PartHasDateHeaders matcher =
        new PartHasDateHeaders("X-Custom", emptyIterableOf(OffsetDateTime.class));
    assertThat(message, not(matcher));
  }
}
