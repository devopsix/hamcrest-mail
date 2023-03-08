package org.devopsix.hamcrest.mail.matchers;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import javax.mail.Multipart;

import static org.devopsix.hamcrest.mail.MultipartCreator.newMultipart;
import static org.devopsix.hamcrest.mail.PartCreator.newPart;
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
        Multipart multipart = newMultipart().subtype("mixed").bodyParts(
            newPart().content(new byte[] {1,2,3}).create(),
            newPart().content(new byte[] {1,2,3}).create()
        ).create();
        MultipartHasContentType matcher = new MultipartHasContentType(startsWith("multipart/mixed"));
        assertThat(multipart, matcher);
    }
}
