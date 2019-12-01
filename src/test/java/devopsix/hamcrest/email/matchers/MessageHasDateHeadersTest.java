package devopsix.hamcrest.email.matchers;

import static java.time.OffsetDateTime.now;
import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterableOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

public class MessageHasDateHeadersTest extends MatcherTest {
    
    @Test
    public void shouldNotMatchWhenHeaderCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Resent-Date"))).thenThrow(new MessagingException("error deocding header"));
        MessageHasDateHeaders matcher = new MessageHasDateHeaders("Resent-Date", emptyIterableOf(OffsetDateTime.class));
        assertThat(matcher.matches(message), is(false));
        logMismatchDescription(matcher, message);
    }
    
    @Test
    public void shouldMatchWhenNoHeaderIsPresent() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Resent-Date"))).thenReturn(null);
        MessageHasDateHeaders matcher = new MessageHasDateHeaders("Resent-Date", emptyIterableOf(OffsetDateTime.class));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void shouldMatchWhenOneHeaderIsPresent() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Resent-Date"))).thenReturn(new String[] {now().format(RFC_1123_DATE_TIME)});
        MessageHasDateHeaders matcher = new MessageHasDateHeaders("Resent-Date", (Matcher)iterableWithSize(1));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void shouldMatchWhenTwoHeadersArePresent() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Resent-Date"))).thenReturn(new String[] {now().format(RFC_1123_DATE_TIME), now().format(RFC_1123_DATE_TIME)});
        MessageHasDateHeaders matcher = new MessageHasDateHeaders("Resent-Date", (Matcher)iterableWithSize(2));
        assertThat(matcher.matches(message), is(true));
    }
    
    @Test
    public void shouldNotMatchWhenHeaderValueIsMalformed() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Resent-Date"))).thenReturn(new String[] {"foobar"});
        MessageHasDateHeaders matcher = new MessageHasDateHeaders("Resent-Date", emptyIterableOf(OffsetDateTime.class));
        assertThat(matcher.matches(message), is(false));
        logMismatchDescription(matcher, message);
    }
}
