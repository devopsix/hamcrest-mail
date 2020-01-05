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
 * <p>A matcher for the {@code address} property of {@code javax.mail.internet.InternetAddress} objects.</p>
 * 
 * @see InternetAddress#getAddress()
 * @see Address
 * 
 * @author devopsix
 *
 */
public class AddressHasAddress extends TypeSafeDiagnosingMatcher<Address> {
    
    private final Matcher<String> matcher;
    
    public AddressHasAddress(Matcher<String> matcher) {
        this.matcher = matcher;
    }
    
    @Override
    public boolean matchesSafely(Address address, Description mismatch) {
        return internetAddress(address, mismatch).and(extractAddress()).matching(matcher);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has an address which matches: ");
        matcher.describeTo(description);
    }
    
    protected Condition<InternetAddress> internetAddress(Address address, Description mismatch) {
        if (address instanceof InternetAddress) {
            return matched((InternetAddress)address, mismatch);
        } else {
            mismatch.appendText("is not an InternetAddress");
            return notMatched();
        }
    }
    
    private Step<InternetAddress, String> extractAddress() {
        return new Step<InternetAddress, String>() {
            @Override
            public Condition<String> apply(InternetAddress address, Description mismatch) {
                if (isNull(address)) {
                    mismatch.appendText("null");
                    return notMatched();
                } else {
                    return matched(address.getAddress(), mismatch);
                }
            }
        };
    }
}
