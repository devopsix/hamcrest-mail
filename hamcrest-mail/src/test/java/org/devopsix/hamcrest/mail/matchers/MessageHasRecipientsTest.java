package org.devopsix.hamcrest.mail.matchers;

import static javax.mail.Message.RecipientType.BCC;
import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterableOf;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import org.junit.jupiter.api.Test;

public class MessageHasRecipientsTest extends MatcherTest {
    
    @Test
    public void shouldNotMatchWhenRecipientsCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getAllRecipients()).thenThrow(new MessagingException("error decoding recipients"));
        MessageHasRecipients matcher = new MessageHasRecipients(emptyIterableOf(Address.class));
        assertThat(message, not(matcher));
    }
    
    @Test
    public void shouldNotMatchWhenRecipientsOfCertainTypeCannotBeExtracted() throws Exception {
        Message message = mock(Message.class);
        when(message.getRecipients(any(RecipientType.class))).thenThrow(new MessagingException("error decoding recipients"));
        MessageHasRecipients matcher = new MessageHasRecipients(TO, emptyIterableOf(Address.class));
        assertThat(message, not(matcher));
    }
    
    @Test
    public void shouldMatchWhenRecipientsAreNull() throws Exception {
        Message message = mock(Message.class);
        when(message.getAllRecipients()).thenReturn(null);
        MessageHasRecipients matcher = new MessageHasRecipients(emptyIterableOf(Address.class));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchWhenRecipientsOfCertainTypeAreNull() throws Exception {
        Message message = mock(Message.class);
        when(message.getRecipients(any(RecipientType.class))).thenReturn(null);
        MessageHasRecipients matcher = new MessageHasRecipients(CC, emptyIterableOf(Address.class));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchWhenRecipientsAreEmptyArray() throws Exception {
        Message message = mock(Message.class);
        when(message.getAllRecipients()).thenReturn(new Address[0]);
        MessageHasRecipients matcher = new MessageHasRecipients(emptyIterableOf(Address.class));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchWhenRecipientsOfCertainTypeAreEmptyArray() throws Exception {
        Message message = mock(Message.class);
        when(message.getRecipients(any(RecipientType.class))).thenReturn(new Address[0]);
        MessageHasRecipients matcher = new MessageHasRecipients(CC, emptyIterableOf(Address.class));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchRecipients() throws Exception {
        Message message = mock(Message.class);
        when(message.getAllRecipients()).thenReturn(new Address[] {new InternetAddress("joe.average@example.com")});
        MessageHasRecipients matcher = new MessageHasRecipients(iterableWithSize(1));
        assertThat(message, matcher);
    }
    
    @Test
    public void shouldMatchRecipientsOfCertainType() throws Exception {
        Message message = mock(Message.class);
        when(message.getRecipients(any(RecipientType.class))).thenReturn(new Address[] {new InternetAddress("joe.average@example.com")});
        MessageHasRecipients matcher = new MessageHasRecipients(BCC, iterableWithSize(1));
        assertThat(message, matcher);
    }
}
