package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.MultipartCreator.newMultipart;
import static org.devopsix.hamcrest.mail.PartCreator.newPart;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import org.junit.jupiter.api.Test;

public class PartHasTextContentTest {

  @Test
  public void shouldNotMatchWhenContentCannotBeExtracted() throws Exception {
    Part part = mock(Part.class);
    when(part.getContent()).thenThrow(new MessagingException("error decoding content"));
    PartHasTextContent matcher = new PartHasTextContent(any(String.class));
    assertThat(part, not(matcher));
  }

  @Test
  public void shouldNotMatchWhenContentIsNull() throws Exception {
    Part part = mock(Part.class);
    when(part.getContent()).thenReturn(null);
    PartHasTextContent matcher = new PartHasTextContent(any(String.class));
    assertThat(part, not(matcher));
  }

  @Test
  public void shouldNotMatchWhenContentIsNotString() throws Exception {
    Part part = newPart().multipart(newMultipart().create()).create();
    when(part.getContent()).thenReturn(mock(Multipart.class));
    PartHasTextContent matcher = new PartHasTextContent(any(String.class));
    assertThat(part, not(matcher));
  }

  @Test
  public void shouldMatchWhenContentIsPresent() {
    Part part = newPart().text("Lorem ipsum").create();
    PartHasTextContent matcher = new PartHasTextContent(equalTo("Lorem ipsum"));
    assertThat(part, matcher);
  }
}
