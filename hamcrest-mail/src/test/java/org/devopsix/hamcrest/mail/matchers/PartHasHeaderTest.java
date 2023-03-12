package org.devopsix.hamcrest.mail.matchers;

import static javax.mail.internet.MimeUtility.encodeText;
import static org.devopsix.hamcrest.mail.MessageCreator.newMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.mail.Message;
import javax.mail.MessagingException;
import org.junit.jupiter.api.Test;

public class PartHasHeaderTest {

  @Test
  public void shouldNotMatchWhenHeaderCannotBeExtracted() throws Exception {
    Message message = mock(Message.class);
    when(message.getHeader(eq("Message-ID"))).thenThrow(
        new MessagingException("error decoding header"));
    PartHasHeader matcher = new PartHasHeader("Message-ID", any(String.class));
    assertThat(message, not(matcher));
  }

  @Test
  public void shouldNotMatchWhenHeaderIsMissing() throws Exception {
    Message message = mock(Message.class);
    when(message.getHeader(eq("Message-ID"))).thenReturn(null);
    PartHasHeader matcher = new PartHasHeader("Message-ID", any(String.class));
    assertThat(message, not(matcher));
  }

  @Test
  public void shouldNotMatchWhenHeaderIsPresentTwice() throws Exception {
    Message message = mock(Message.class);
    when(message.getHeader(eq("Message-ID"))).thenReturn(
        new String[] {"abc123@example.com", "def456@example.com"});
    PartHasHeader matcher = new PartHasHeader("Message-ID", any(String.class));
    assertThat(message, not(matcher));
  }

  @Test
  public void shouldMatchWhenHeaderIsPresent() {
    Message message = messageWithHeader("Message-ID", "abc123@example.com");
    PartHasHeader matcher = new PartHasHeader("Message-ID", any(String.class));
    assertThat(message, matcher);
  }

  @Test
  public void shouldMatchWhenHeaderIsMissing() throws Exception {
    Message message = mock(Message.class);
    when(message.getHeader(eq("Message-ID"))).thenReturn(null);
    PartHasHeader matcher = new PartHasHeader("Message-ID", nullValue(String.class));
    assertThat(message, matcher);
  }

  @Test
  public void shouldMatchWhenHeaderWithGermanUmlautsInUtf8IsPresent() throws Exception {
    Message message = messageWithHeader("Some-Header", encodeText("ÄÖÜäüöß", "UTF-8", null));
    PartHasHeader matcher = new PartHasHeader("Some-Header", equalTo("ÄÖÜäüöß"));
    assertThat(message, matcher);
  }

  @Test
  public void shouldMatchWhenHeaderWithGermanUmlautsInIso88591IsPresent() throws Exception {
    Message message = messageWithHeader("Some-Header", encodeText("ÄÖÜäüöß", "ISO-8859-1", null));
    PartHasHeader matcher = new PartHasHeader("Some-Header", equalTo("ÄÖÜäüöß"));
    assertThat(message, matcher);
  }

  private Message messageWithHeader(String name, String value) {
    return newMessage()
        .header(name, value)
        .create();
  }
}
