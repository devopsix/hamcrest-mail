package org.devopsix.hamcrest.mail.matchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

import org.junit.jupiter.api.Test;

public class AddressHasAddressTest {
    
    @Test
    public void shouldNotMatchWhenItsNoInternetAddress() {
        Address address = mock(Address.class);
        AddressHasAddress matcher = new AddressHasAddress(any(String.class));
        assertThat(address, not(matcher));
    }
    
    @Test
    public void shouldNotMatchNull() {
        Address address = null;
        AddressHasAddress matcher = new AddressHasAddress(any(String.class));
        assertThat(address, not(matcher));
    }
    
    @Test
    public void shouldMatchInternetAddress() throws Exception {
        Address address = new InternetAddress("anna@example.com");
        AddressHasAddress matcher = new AddressHasAddress(is("anna@example.com"));
        assertThat(address, matcher);
    }
    
    @Test
    public void shouldMatchInternetAddressWithAngleBrackets() throws Exception {
        Address address = new InternetAddress("<anna@example.com>");
        AddressHasAddress matcher = new AddressHasAddress(is("anna@example.com"));
        assertThat(address, matcher);
    }
    
    @Test
    public void shouldMatchInternetAddressWithAngleBracketsAndPhrase() throws Exception {
        Address address = new InternetAddress("Anna <anna@example.com>");
        AddressHasAddress matcher = new AddressHasAddress(is("anna@example.com"));
        assertThat(address, matcher);
    }
    
    @Test
    public void shouldMatchInternetAddressWithAngleBracketsAndPhraseInDoubleQuotes() throws Exception {
        Address address = new InternetAddress("\"Anna\" <anna@example.com>");
        AddressHasAddress matcher = new AddressHasAddress(is("anna@example.com"));
        assertThat(address, matcher);
    }

}
