package org.devopsix.hamcrest.mail.matchers;

import static java.util.Objects.isNull;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import org.hamcrest.Condition;
import org.hamcrest.Condition.Step;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * A matcher for the {@code personal} property of {@code javax.mail.internet.InternetAddress}
 * objects.
 *
 * @author devopsix
 * @see InternetAddress#getPersonal()
 * @see Address
 */
public class AddressHasPersonal extends TypeSafeDiagnosingMatcher<Address> {

  private final Matcher<String> matcher;

  /**
   * Creates a new instance.
   *
   * @param matcher Personal matcher
   */
  public AddressHasPersonal(Matcher<String> matcher) {
    this.matcher = matcher;
  }

  @Override
  public boolean matchesSafely(Address address, Description mismatch) {
    return internetAddress(address, mismatch).and(extractPersonal()).matching(matcher);
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("has a personal name which matches: ");
    matcher.describeTo(description);
  }

  private Condition<InternetAddress> internetAddress(Address address, Description mismatch) {
    if (address instanceof InternetAddress) {
      return matched((InternetAddress) address, mismatch);
    } else {
      mismatch.appendText("is not an InternetAddress");
      return notMatched();
    }
  }

  private Step<InternetAddress, String> extractPersonal() {
    return (address, mismatch) -> {
      if (isNull(address)) {
        mismatch.appendText("null");
        return notMatched();
      } else {
        return matched(address.getPersonal(), mismatch);
      }
    };
  }
}
