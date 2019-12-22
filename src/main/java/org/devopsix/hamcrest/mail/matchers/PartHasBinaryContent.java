package org.devopsix.hamcrest.mail.matchers;

import static java.lang.String.format;
import static org.devopsix.hamcrest.mail.util.ArrayUtils.toObject;
import static org.devopsix.hamcrest.mail.util.IOUtils.toByteArray;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import javax.mail.MessagingException;
import javax.mail.Part;

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class PartHasBinaryContent extends TypeSafeDiagnosingMatcher<Part> {
    
    final Matcher<Byte[]> contentMatcher;
    
    public PartHasBinaryContent(Matcher<Byte[]> contentMatcher) {
        this.contentMatcher = contentMatcher;
    }

    @Override
    protected boolean matchesSafely(Part part, Description mismatch) {
        return content(part, mismatch).matching(contentMatcher);
    }
    
    private Condition<Byte[]> content(Part part, Description mismatch) {
        try {
            InputStream data = part.getDataHandler().getInputStream();
            if (Objects.isNull(data)) {
                mismatch.appendText("null");
                return notMatched();
            } else {
                return matched(toObject(toByteArray(data)), mismatch);
            }
        } catch (IOException | MessagingException e) {
            mismatch.appendText(format("failed to extract content: ", e.getMessage()));
            return notMatched();
        }
    }
    
    @Override
    public void describeTo(Description description) {
        description.appendText("has content which matches: ");
        contentMatcher.describeTo(description);
    }
}
