package org.devopsix.hamcrest.mail.matchers;

import static javax.mail.internet.MimeUtility.encodeText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterableOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

public class PartHasHeadersTest extends MatcherTest {
    
    @Test
    public void shouldNotMatchWhenHeadersCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getHeader(eq("Received"))).thenThrow(new MessagingException("error deocding headers"));
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
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void shouldMatchWhenOneHeaderIsPresent() throws Exception {
        Message message = messageWithHeader("Received", "by foo (MTA) id abc123; Sun, 1 Dec 2019 12:27:39 +0100 (CET)");
        PartHasHeaders matcher = new PartHasHeaders("Received", (Matcher)iterableWithSize(1));
        assertThat(message, matcher);
    }
    
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void shouldMatchWhenTwoHeadersArePresent() throws Exception {
        Message message = messageWithTwoHeaders("Received", "by foo (MTA) id abc123; Sun, 1 Dec 2019 12:27:39 +0100 (CET)",
            "by bar (MTA) id def456; Sun, 1 Dec 2019 12:27:51 +0100 (CET)");
        PartHasHeaders matcher = new PartHasHeaders("Received", (Matcher)iterableWithSize(2));
        assertThat(message, matcher);
    }
    
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void shouldMatchWhenHeadersWithGermanUmlautsInUtf8AndIso88591IsPresent() throws Exception {
        Message message = messageWithTwoHeaders("Some-Header", encodeText("ÄÖÜ", "UTF-8", null), encodeText("äüöß", "ISO-8859-1", null));
        PartHasHeaders matcher = new PartHasHeaders("Some-Header", (Matcher)hasItems(equalTo("ÄÖÜ"), equalTo("äüöß")));
        assertThat(message, matcher);
    }
    
    private Message messageWithHeader(String name, String value) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.setHeader(name, value);
        message.saveChanges();
        return message;
    }
    
    private Message messageWithTwoHeaders(String name, String value1, String value2) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.setHeader(name, value1);
        message.addHeader(name, value2);
        message.saveChanges();
        return message;
    }
}
