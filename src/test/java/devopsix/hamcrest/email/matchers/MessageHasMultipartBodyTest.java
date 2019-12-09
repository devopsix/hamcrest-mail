package devopsix.hamcrest.email.matchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

import org.junit.jupiter.api.Test;

public class MessageHasMultipartBodyTest extends MatcherTest {
    
    @Test
    public void shouldNotMatchWhenContentCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getContent()).thenThrow(new MessagingException("error deocding header"));
        MessageHasMultipartBody matcher = new MessageHasMultipartBody();
        assertThat(message, not(matcher));
    }
    
    @Test
    public void shouldNotMatchWhenContentIsNull() throws Exception {
        Message message = mock(Message.class);
        when(message.getContent()).thenReturn(null);
        MessageHasMultipartBody matcher = new MessageHasMultipartBody();
        assertThat(message, not(matcher));
    }
    
    @Test
    public void shouldNotMatchWhenContentIsNotAMultipartObject() throws Exception {
        Message message = mock(Message.class);
        when(message.getContent()).thenReturn("foo");
        MessageHasMultipartBody matcher = new MessageHasMultipartBody();
        assertThat(message, not(matcher));
    }
    
    @Test
    public void shouldMatchWhenContentIsPresent() throws Exception {
        Message message = mock(Message.class);
        when(message.getContent()).thenReturn(new MimeMultipart());
        MessageHasMultipartBody matcher = new MessageHasMultipartBody();
        assertThat(message, matcher);
    }
}
