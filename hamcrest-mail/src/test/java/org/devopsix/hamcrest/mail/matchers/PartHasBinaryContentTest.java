package org.devopsix.hamcrest.mail.matchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.Part;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

public class PartHasBinaryContentTest extends MatcherTest {
    
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void shouldNotMatchWhenContentCannotBeExtracted() throws Exception {
        Part part = mock(Part.class);
        when(part.getDataHandler()).thenThrow(new MessagingException("error decoding header"));
        PartHasBinaryContent matcher = new PartHasBinaryContent((Matcher)anything());
        assertThat(part, not(matcher));
    }
    
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void shouldNotMatchWhenContentIsNull() throws Exception {
        DataHandler dataHandler = mock(DataHandler.class);
        when(dataHandler.getInputStream()).thenReturn(null);
        Part part = mock(Part.class);
        when(part.getDataHandler()).thenReturn(dataHandler);
        PartHasBinaryContent matcher = new PartHasBinaryContent((Matcher)anything());
        assertThat(part, not(matcher));
    }
    
    @Test
    public void shouldMatchWhenContentIsPresent() throws Exception {
        DataHandler dataHandler = mock(DataHandler.class);
        when(dataHandler.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[] {1,2,3}));
        Part part = mock(Part.class);
        when(part.getDataHandler()).thenReturn(dataHandler);
        PartHasBinaryContent matcher = new PartHasBinaryContent(arrayWithSize(3));
        assertThat(part, matcher);
    }
}
