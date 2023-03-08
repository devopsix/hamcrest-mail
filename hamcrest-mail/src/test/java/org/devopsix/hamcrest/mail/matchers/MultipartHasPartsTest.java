package org.devopsix.hamcrest.mail.matchers;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import javax.mail.Multipart;

import static org.devopsix.hamcrest.mail.MultipartCreator.newMultipart;
import static org.devopsix.hamcrest.mail.PartCreator.newPart;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    public void shouldMatchWhenPartsArePresent() {
        Multipart multipart = newMultipart()
            .subtype("mixed")
            .bodyParts(
                newPart().header("X-Header", "Foo").content(new byte[] {1,2,3}).create(),
                newPart().header("X-Header", "Bar").content(new byte[] {1,2,3}).create())
            .create();
        MultipartHasParts matcher = new MultipartHasParts((Matcher)anything());
        assertThat(multipart, matcher);
    }
}
