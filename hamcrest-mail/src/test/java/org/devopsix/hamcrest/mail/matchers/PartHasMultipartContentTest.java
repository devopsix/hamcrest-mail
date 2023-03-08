package org.devopsix.hamcrest.mail.matchers;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;

import static org.devopsix.hamcrest.mail.MailMatchers.hasHeader;
import static org.devopsix.hamcrest.mail.MailMatchers.hasPart;
import static org.devopsix.hamcrest.mail.MessageCreator.newMessage;
import static org.devopsix.hamcrest.mail.MultipartCreator.newMultipart;
import static org.devopsix.hamcrest.mail.PartCreator.newPart;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PartHasMultipartContentTest {
    
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
    public void recursiveShouldNotMatchTextMessage() {
        Message message = newMessage().text("Lorem ipsum").create();
        PartHasMultipartContent matcher = new PartHasMultipartContent(true, (Matcher)anything());
        assertThat(message, not(matcher));
    }
    
    @Test
    public void recursiveShouldMatchMultipartMessage() {
        Message message = newMessage().multipart(newMultipart()
            .subtype("mixed")
            .bodyParts(
                newPart().header("X-Header", "Foo").content(new byte[] {1,2,3}).create(),
                newPart().header("X-Header", "Bar").content(new byte[] {1,2,3}).create())
            .create()
        ).create();
        PartHasMultipartContent matcher = new PartHasMultipartContent(true, hasPart(hasHeader("X-Header", "Foo")));
        assertThat(message, matcher);
        matcher = new PartHasMultipartContent(true, hasPart(hasHeader("X-Header", "Bar")));
        assertThat(message, matcher);
        matcher = new PartHasMultipartContent(true, hasPart(hasHeader("X-Header", "Baz")));
        assertThat(message, not(matcher));
    }
    
    @Test
    public void recursiveShouldMatchMultipartPartOfMultipartMessage() {
        Message message = newMessage().multipart(newMultipart()
            .subtype("mixed")
            .bodyParts(
                newPart().header("X-Header", "Foo").content(new byte[] {1,2,3}).create(),
                newPart().header("X-Header", "Bar").content(new byte[] {1,2,3}).create())
            .create()
        ).create();
        PartHasMultipartContent matcher = new PartHasMultipartContent(true, hasPart(hasHeader("X-Header", "Foo")));
        assertThat(message, matcher);
        matcher = new PartHasMultipartContent(true, hasPart(hasHeader("X-Header", "Bar")));
        assertThat(message, matcher);
        matcher = new PartHasMultipartContent(true, hasPart(hasHeader("X-Header", "Baz")));
        assertThat(message, not(matcher));
    }
}
