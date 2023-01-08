package org.devopsix.hamcrest.mail.matchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

public class MultipartHasPartsTest {
    
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void shouldNotMatchWhenPartsCannotBeExtracted() throws Exception {
        Multipart multipart = mock(Multipart.class);
        when(multipart.getCount()).thenReturn(1);
        when(multipart.getBodyPart(anyInt())).thenThrow(new MessagingException("error decoding part"));
        MultipartHasParts matcher = new MultipartHasParts((Matcher)anything());
        assertThat(multipart, not(matcher));
    }
    
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void shouldMatchWhenPartsArePresent() throws Exception {
        Multipart multipart = mock(Multipart.class);
        when(multipart.getCount()).thenReturn(2);
        when(multipart.getBodyPart(anyInt())).thenReturn(mock(BodyPart.class));
        MultipartHasParts matcher = new MultipartHasParts((Matcher)anything());
        assertThat(multipart, matcher);
    }
}
