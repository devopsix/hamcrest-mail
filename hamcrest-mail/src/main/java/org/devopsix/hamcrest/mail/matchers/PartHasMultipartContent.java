package org.devopsix.hamcrest.mail.matchers;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;
import static org.hamcrest.Matchers.hasItem;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * <p>A matcher for multipart content.</p>
 *
 * @author devopsix
 */
public class PartHasMultipartContent extends TypeSafeDiagnosingMatcher<Part> {

  private boolean recursive;

  private final Matcher<Multipart> matcher;

  /**
   * <p>Creates a new instance.</p>
   *
   * @param recursive Whether to traverse parts recursively
   * @param matcher Multipart content matcher
   */
  public PartHasMultipartContent(boolean recursive, Matcher<Multipart> matcher) {
    this.recursive = recursive;
    this.matcher = matcher;
  }

  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  protected boolean matchesSafely(Part part, Description mismatch) {
    if (recursive) {
      return multiparts(part, mismatch).matching((Matcher) hasItem(matcher));
    } else {
      return multipart(part, mismatch).matching(matcher);
    }
  }

  private Condition<Multipart> multipart(Part part, Description mismatch) {
    try {
      Object content = part.getContent();
      if (content instanceof Multipart) {
        return matched((Multipart) content, mismatch);
      } else {
        mismatch.appendText(format("not multipart content: %s",
            isNull(content) ? null : content.getClass().getSimpleName()));
        return notMatched();
      }
    } catch (IOException | MessagingException e) {
      mismatch.appendText(format("failed to extract content: %s", e.getMessage()));
      return notMatched();
    }
  }

  private Condition<Iterable<Multipart>> multiparts(Part part, Description mismatch) {
    try {
      return matched(multiparts(part), mismatch);
    } catch (IOException | MessagingException e) {
      mismatch.appendText(format("failed to extract content: %s", e.getMessage()));
      return notMatched();
    }
  }

  private Collection<Multipart> multiparts(Part part) throws MessagingException, IOException {
    LinkedList<Multipart> multiparts = new LinkedList<>();
    Object content = part.getContent();
    if (content instanceof Multipart) {
      Multipart multipart = (Multipart) content;
      multiparts.add(multipart);
      for (int i = 0; i < multipart.getCount(); i++) {
        multiparts.addAll(multiparts(multipart.getBodyPart(i)));
      }
    }
    return multiparts;
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("has multipart content");
  }
}
