package devopsix.hamcrest.email.matchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

import org.junit.jupiter.api.Test;

public class MessageHasTextBodyTest extends MatcherTest {
    
    @Test
    public void shouldNotMatchWhenContentCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getContent()).thenThrow(new MessagingException("error deocding header"));
        MessageHasTextBody matcher = new MessageHasTextBody(any(String.class));
        assertThat(message, not(matcher));
    }
    
    @Test
    public void shouldNotMatchWhenContentIsNull() throws Exception {
        Message message = mock(Message.class);
        when(message.getContent()).thenReturn(null);
        MessageHasTextBody matcher = new MessageHasTextBody(any(String.class));
        assertThat(message, not(matcher));
    }
    
    @Test
    public void shouldNotMatchWhenContentIsNotAString() throws Exception {
        Message message = mock(Message.class);
        when(message.getContent()).thenReturn(mock(Multipart.class));
        MessageHasTextBody matcher = new MessageHasTextBody(any(String.class));
        assertThat(message, not(matcher));
    }
    
    @Test
    public void shouldMatchWhenContentIsPresent() throws Exception {
        Message message = mock(Message.class);
        when(message.getContent()).thenReturn("Lorem ipsum");
        MessageHasTextBody matcher = new MessageHasTextBody(equalTo("Lorem ipsum"));
        assertThat(message, matcher);
    }
}
