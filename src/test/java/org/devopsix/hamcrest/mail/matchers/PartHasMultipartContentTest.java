package org.devopsix.hamcrest.mail.matchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

public class PartHasMultipartContentTest extends MatcherTest {
    
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void shouldNotMatchWhenContentCannotBeExtracted() throws Exception {
        Part part = mock(Part.class);
        when(part.getContent()).thenThrow(new MessagingException("error deocding header"));
        PartHasMultipartContent matcher = new PartHasMultipartContent((Matcher)anything());
        assertThat(part, not(matcher));
    }
    
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void shouldNotMatchWhenContentIsNull() throws Exception {
        Part part = mock(Part.class);
        when(part.getContent()).thenReturn(null);
        PartHasMultipartContent matcher = new PartHasMultipartContent((Matcher)anything());
        assertThat(part, not(matcher));
    }
    
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void shouldMatchWhenContentIsPresent() throws Exception {
        Part part = mock(Part.class);
        when(part.getContent()).thenReturn(mock(Multipart.class));
        PartHasMultipartContent matcher = new PartHasMultipartContent((Matcher)anything());
        assertThat(part, matcher);
    }
}
