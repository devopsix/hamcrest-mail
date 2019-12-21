package org.devopsix.hamcrest.mail.matchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;

import javax.mail.MessagingException;
import javax.mail.Part;

import org.junit.jupiter.api.Test;

public class PartHasBinaryContentTest extends MatcherTest {
    
    @Test
    public void shouldNotMatchWhenContentCannotBeExtracted() throws Exception {
        Part part = mock(Part.class);
        when(part.getContent()).thenThrow(new MessagingException("error deocding header"));
        PartHasBinaryContent matcher = new PartHasBinaryContent(emptyArray());
        assertThat(part, not(matcher));
    }
    
    @Test
    public void shouldNotMatchWhenContentIsNull() throws Exception {
        Part part = mock(Part.class);
        when(part.getContent()).thenReturn(null);
        PartHasBinaryContent matcher = new PartHasBinaryContent(emptyArray());
        assertThat(part, not(matcher));
    }
    
    @Test
    public void shouldMatchWhenContentIsPresent() throws Exception {
        Part part = mock(Part.class);
        when(part.getContent()).thenReturn(new ByteArrayInputStream(new byte[] {1,2,3}));
        PartHasBinaryContent matcher = new PartHasBinaryContent(arrayWithSize(3));
        assertThat(part, matcher);
    }
}
