package org.devopsix.hamcrest.mail.matchers;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import javax.mail.*;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import static org.devopsix.hamcrest.mail.MailMatchers.hasHeader;
import static org.devopsix.hamcrest.mail.MailMatchers.hasPart;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PartHasMultipartContentTest extends MatcherTest {
    
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void shouldNotMatchWhenContentCannotBeExtracted() throws Exception {
        Part part = mock(Part.class);
        when(part.getContent()).thenThrow(new MessagingException("error decoding content"));
        PartHasMultipartContent matcher = new PartHasMultipartContent(false, (Matcher)anything());
        assertThat(part, not(matcher));
    }
    
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void shouldNotMatchWhenContentIsNull() throws Exception {
        Part part = mock(Part.class);
        when(part.getContent()).thenReturn(null);
        PartHasMultipartContent matcher = new PartHasMultipartContent(false, (Matcher)anything());
        assertThat(part, not(matcher));
    }
    
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void shouldMatchWhenContentIsPresent() throws Exception {
        Part part = mock(Part.class);
        when(part.getContent()).thenReturn(mock(Multipart.class));
        PartHasMultipartContent matcher = new PartHasMultipartContent(false, (Matcher)anything());
        assertThat(part, matcher);
    }
    
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void recursiveShouldNotMatchTextMessage() throws Exception {
        Message message = createTextMessage();
        PartHasMultipartContent matcher = new PartHasMultipartContent(true, (Matcher)anything());
        assertThat(message, not(matcher));
    }
    
    @Test
    public void recursiveShouldMatchMultipartMessage() throws Exception {
        BodyPart part1 = createPartWithHeader("X-Header", "Foo");
        BodyPart part2 = createPartWithHeader("X-Header", "Bar");
        Message message = createMultipartMessage(part1, part2);
        PartHasMultipartContent matcher = new PartHasMultipartContent(true, hasPart(hasHeader("X-Header", "Foo")));
        assertThat(message, matcher);
        matcher = new PartHasMultipartContent(true, hasPart(hasHeader("X-Header", "Bar")));
        assertThat(message, matcher);
        matcher = new PartHasMultipartContent(true, hasPart(hasHeader("X-Header", "Baz")));
        assertThat(message, not(matcher));
    }
    
    @Test
    public void recursiveShouldMatchMultipartPartOfMultipartMessage() throws Exception {
        BodyPart part1 = createPartWithHeader("X-Header", "Foo");
        BodyPart part2 = createPartWithHeader("X-Header", "Bar");
        BodyPart multipart = createMultipart(part1, part2);
        Message message = createMultipartMessage(multipart);
        PartHasMultipartContent matcher = new PartHasMultipartContent(true, hasPart(hasHeader("X-Header", "Foo")));
        assertThat(message, matcher);
        matcher = new PartHasMultipartContent(true, hasPart(hasHeader("X-Header", "Bar")));
        assertThat(message, matcher);
        matcher = new PartHasMultipartContent(true, hasPart(hasHeader("X-Header", "Baz")));
        assertThat(message, not(matcher));
    }
    
    private Message createTextMessage() throws IOException, MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.setText("Lorem ipsum");
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        message.writeTo(buffer);
        return new MimeMessage(session, new ByteArrayInputStream(buffer.toByteArray()));
    }
    
    private Message createMultipartMessage(BodyPart... parts) throws IOException, MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.setContent(new MimeMultipart("mixed", parts));
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        message.writeTo(buffer);
        return new MimeMessage(session, new ByteArrayInputStream(buffer.toByteArray()));
    }
    
    private MimeBodyPart createPartWithHeader(String name, String value) throws MessagingException {
        InternetHeaders headers = new InternetHeaders();
        headers.addHeader("Content-Type", "application/octet-stream");
        headers.addHeader(name, value);
        return new MimeBodyPart(headers, new byte[] {1,2,3});
    }
    
    private MimeBodyPart createMultipart(BodyPart... parts) throws MessagingException {
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(new MimeMultipart("mixed", parts));
        return mimeBodyPart;
    }
}
