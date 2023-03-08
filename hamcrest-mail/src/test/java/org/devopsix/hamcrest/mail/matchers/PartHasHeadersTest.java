package org.devopsix.hamcrest.mail.matchers;

import org.junit.jupiter.api.Test;

import javax.mail.Message;
import javax.mail.MessagingException;

import static javax.mail.internet.MimeUtility.encodeText;
import static org.devopsix.hamcrest.mail.MessageCreator.newMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PartHasHeadersTest {
    
    @Test
    public void shouldNotMatchWhenHeadersCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Received"))).thenThrow(new MessagingException("error decoding headers"));
        PartHasHeaders matcher = new PartHasHeaders("Received", emptyIterableOf(String.class));
        assertThat(message, not(matcher));
    }
    
    @Test
    public void shouldMatchWhenNoHeaderIsPresent() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Received"))).thenReturn(null);
        PartHasHeaders matcher = new PartHasHeaders("Received", emptyIterableOf(String.class));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchWhenOneHeaderIsPresent() {
        Message message = newMessage()
            .header("Received", "by foo (MTA) id abc123; Sun, 1 Dec 2019 12:27:39 +0100 (CET)")
            .create();
        PartHasHeaders matcher = new PartHasHeaders("Received", iterableWithSize(1));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchWhenTwoHeadersArePresent() {
        Message message = messageWithTwoHeaders("Received", "by foo (MTA) id abc123; Sun, 1 Dec 2019 12:27:39 +0100 (CET)",
            "by bar (MTA) id def456; Sun, 1 Dec 2019 12:27:51 +0100 (CET)");
        PartHasHeaders matcher = new PartHasHeaders("Received", iterableWithSize(2));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchWhenHeadersWithGermanUmlautsInUtf8AndIso88591IsPresent() throws Exception {
        Message message = messageWithTwoHeaders("Some-Header", encodeText("ÄÖÜ", "UTF-8", null), encodeText("äüöß", "ISO-8859-1", null));
        PartHasHeaders matcher = new PartHasHeaders("Some-Header", hasItems(equalTo("ÄÖÜ"), equalTo("äüöß")));
        assertThat(message, matcher);
    }

    private Message messageWithTwoHeaders(String name, String value1, String value2) {
        return newMessage()
            .header(name, value1)
            .header(name, value2)
            .create();
    }
}
