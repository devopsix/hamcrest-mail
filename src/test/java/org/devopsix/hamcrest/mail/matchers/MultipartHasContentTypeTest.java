package org.devopsix.hamcrest.mail.matchers;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import javax.mail.Multipart;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MultipartHasContentTypeTest {
    
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void shouldNotMatchWhenContentIsNull() {
        Multipart multipart = mock(Multipart.class);
        when(multipart.getContentType()).thenReturn(null);
        MultipartHasContentType matcher = new MultipartHasContentType((Matcher)anything());
        assertThat(multipart, not(matcher));
    }
    
    @Test
    public void shouldMatchWhenContentIsPresent() {
        Multipart multipart = mock(Multipart.class);
        when(multipart.getContentType()).thenReturn("multipart/mixed; \r\n\\tboundary=\"----=_Part_17_872522004.1577017605979\"");
        MultipartHasContentType matcher = new MultipartHasContentType(startsWith("multipart/mixed"));
        assertThat(multipart, matcher);
    }
}
