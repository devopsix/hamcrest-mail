package org.devopsix.hamcrest.mail.matchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import org.junit.jupiter.api.Test;

public class AddressHasPersonalTest {

  @Test
  public void shouldNotMatchWhenItsNoInternetAddress() {
    Address address = mock(Address.class);
    AddressHasPersonal matcher = new AddressHasPersonal(any(String.class));
    assertThat(address, not(matcher));
  }

  @Test
  public void shouldNotMatchNull() {
    Address address = null;
    AddressHasPersonal matcher = new AddressHasPersonal(any(String.class));
    assertThat(address, not(matcher));
  }

  @Test
  public void shouldMatchInternetAddress() throws Exception {
    Address address = new InternetAddress("anna@example.com");
    AddressHasPersonal matcher = new AddressHasPersonal(emptyOrNullString());
    assertThat(address, matcher);
  }

  @Test
  public void shouldMatchInternetAddressWithAngleBrackets() throws Exception {
    Address address = new InternetAddress("<anna@example.com>");
    AddressHasPersonal matcher = new AddressHasPersonal(emptyOrNullString());
    assertThat(address, matcher);
  }

  @Test
  public void shouldMatchInternetAddressWithAngleBracketsAndPhrase() throws Exception {
    Address address = new InternetAddress("Anna <anna@example.com>");
    AddressHasPersonal matcher = new AddressHasPersonal(is("Anna"));
    assertThat(address, matcher);
  }

  @Test
  public void shouldMatchInternetAddressWithAngleBracketsAndPhraseInDoubleQuotes()
      throws Exception {
    Address address = new InternetAddress("\"Anna\" <anna@example.com>");
    AddressHasPersonal matcher = new AddressHasPersonal(is("Anna"));
    assertThat(address, matcher);
  }

}
