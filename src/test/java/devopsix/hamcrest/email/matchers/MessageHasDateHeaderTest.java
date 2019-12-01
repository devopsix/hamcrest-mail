package devopsix.hamcrest.email.matchers;

import static java.time.OffsetDateTime.now;
import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;

public class MessageHasDateHeaderTest extends MatcherTest {
    
    @Test
    public void shouldNotMatchWhenHeaderCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Resent-Date"))).thenThrow(new MessagingException("error deocding header"));
        MessageHasDateHeader matcher = new MessageHasDateHeader("Resent-Date", any(OffsetDateTime.class));
        assertThat(matcher.matches(message), is(false));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldNotMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Resent-Date"))).thenReturn(null);
        MessageHasDateHeader matcher = new MessageHasDateHeader("Resent-Date", any(OffsetDateTime.class));
        assertThat(matcher.matches(message), is(false));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldNotMatchWhenHeaderIsPresentTwice() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Resent-Date"))).thenReturn(new String[] {now().format(RFC_1123_DATE_TIME), now().format(RFC_1123_DATE_TIME)});
        MessageHasDateHeader matcher = new MessageHasDateHeader("Resent-Date", any(OffsetDateTime.class));
        assertThat(matcher.matches(message), is(false));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldNotMatchWhenHeaderValueIsMalformed() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Resent-Date"))).thenReturn(new String[] {"foobar"});
        MessageHasDateHeader matcher = new MessageHasDateHeader("Resent-Date", any(OffsetDateTime.class));
        assertThat(matcher.matches(message), is(false));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldMatchWhenHeaderIsPresent() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Resent-Date"))).thenReturn(new String[] {now().format(RFC_1123_DATE_TIME)});
        MessageHasDateHeader matcher = new MessageHasDateHeader("Resent-Date", any(OffsetDateTime.class));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void shouldMatchWhenHeaderIsMissing() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Resent-Date"))).thenReturn(null);
        MessageHasDateHeader matcher = new MessageHasDateHeader("Resent-Date", nullValue(OffsetDateTime.class));
        assertThat(matcher.matches(message), is(true));
    }
}
