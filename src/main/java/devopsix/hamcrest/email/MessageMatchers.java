package devopsix.hamcrest.email;

import static java.util.Objects.requireNonNull;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;

import java.time.OffsetDateTime;
import java.util.Map;

import javax.mail.Message;
import javax.mail.Part;

import org.hamcrest.Matcher;

import devopsix.hamcrest.email.matchers.MessageHasBcc;
import devopsix.hamcrest.email.matchers.MessageHasCc;
import devopsix.hamcrest.email.matchers.MessageHasDate;
import devopsix.hamcrest.email.matchers.MessageHasFrom;
import devopsix.hamcrest.email.matchers.MessageHasMultipartBody;
import devopsix.hamcrest.email.matchers.MessageHasReplyTo;
import devopsix.hamcrest.email.matchers.MessageHasSender;
import devopsix.hamcrest.email.matchers.MessageHasSubject;
import devopsix.hamcrest.email.matchers.MessageHasTextBody;
import devopsix.hamcrest.email.matchers.MessageHasTo;
import devopsix.hamcrest.email.matchers.MessageHasValidDkimSignature;
import devopsix.hamcrest.email.matchers.PartHasDateHeader;
import devopsix.hamcrest.email.matchers.PartHasDateHeaders;
import devopsix.hamcrest.email.matchers.PartHasHeader;
import devopsix.hamcrest.email.matchers.PartHasHeaders;

public final class MessageMatchers {

    private MessageMatchers() { }
    
    /**
     * Returns a matcher that matches when the given message has exactly one "Date" header
     * with proper date/time format which matches the given matcher.
     * 
     * @param matcher The value matcher
     * @return A matcher for the "Date" header
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Message> hasDate(Matcher<OffsetDateTime> matcher) {
        requireNonNull(matcher);
        return new MessageHasDate(matcher);
    }
    
    /**
     * Returns a matcher that matches when the given message has exactly one "Date" header
     * with the given value.
     * 
     * @param value The expected header value
     * @return A matcher for the "Date" header
     * @throws NullPointerException when {@code value} is {@code null}
     */
    public static Matcher<Message> hasDate(OffsetDateTime value) {
        requireNonNull(value);
        return new MessageHasDate(equalTo(value));
    }
    
    /**
     * Returns a matcher that matches when the given message has exactly one "From" header
     * which matches the given matcher.
     * 
     * @param matcher The value matcher
     * @return A matcher for the "From" header
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Message> hasFrom(Matcher<String> matcher) {
        requireNonNull(matcher);
        return new MessageHasFrom(matcher);
    }
    
    /**
     * Returns a matcher that matches when the given message has exactly one "From" header
     * with the given value.
     * 
     * @param value The expected header value
     * @return A matcher for the "From" header
     * @throws NullPointerException when {@code value} is {@code null}
     */
    public static Matcher<Message> hasFrom(String value) {
        requireNonNull(value);
        return new MessageHasFrom(equalTo(value));
    }
    
    /**
     * Returns a matcher that matches when the given message has exactly one "Sender" header
     * which matches the given matcher.
     * 
     * @param matcher The value matcher
     * @return A matcher for the "Sender" header
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Message> hasSender(Matcher<String> matcher) {
        requireNonNull(matcher);
        return new MessageHasSender(matcher);
    }
    
    /**
     * Returns a matcher that matches when the given message has exactly one "Sender" header
     * with the given value.
     * 
     * @param value The expected header value
     * @return A matcher for the "Sender" header
     * @throws NullPointerException when {@code value} is {@code null}
     */
    public static Matcher<Message> hasSender(String value) {
        requireNonNull(value);
        return new MessageHasSender(equalTo(value));
    }
    
    /**
     * Returns a matcher that matches when the given message has exactly one "Reply-To" header
     * which matches the given matcher.
     * 
     * @param matcher The value matcher
     * @return A matcher for the "Reply-To" header
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Message> hasReplyTo(Matcher<String> matcher) {
        requireNonNull(matcher);
        return new MessageHasReplyTo(matcher);
    }
    
    /**
     * Returns a matcher that matches when the given message has exactly one "Reply-To" header
     * with the given value.
     * 
     * @param value The expected header value
     * @return A matcher for the "Reply-To" header
     * @throws NullPointerException when {@code value} is {@code null}
     */
    public static Matcher<Message> hasReplyTo(String value) {
        requireNonNull(value);
        return new MessageHasReplyTo(equalTo(value));
    }
    
    /**
     * Returns a matcher that matches when the given message has exactly one "To" header
     * which matches the given matcher.
     * 
     * @param matcher The value matcher
     * @return A matcher for the "To" header
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Message> hasTo(Matcher<String> matcher) {
        requireNonNull(matcher);
        return new MessageHasTo(matcher);
    }
    
    /**
     * Returns a matcher that matches when the given message has exactly one "To" header
     * with the given value.
     * 
     * @param value The expected header value
     * @return A matcher for the "To" header
     * @throws NullPointerException when {@code value} is {@code null}
     */
    public static Matcher<Message> hasTo(String value) {
        requireNonNull(value);
        return new MessageHasTo(equalTo(value));
    }
    
    /**
     * Returns a matcher that matches when the given message has exactly one "Cc" header
     * which matches the given matcher.
     * 
     * @param matcher The value matcher
     * @return A matcher for the "Cc" header
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Message> hasCc(Matcher<String> matcher) {
        requireNonNull(matcher);
        return new MessageHasCc(matcher);
    }
    
    /**
     * Returns a matcher that matches when the given message has exactly one "Cc" header
     * with the given value.
     * 
     * @param value The expected header value
     * @return A matcher for the "Cc" header
     * @throws NullPointerException when {@code value} is {@code null}
     */
    public static Matcher<Message> hasCc(String value) {
        requireNonNull(value);
        return new MessageHasCc(equalTo(value));
    }
    
    /**
     * Returns a matcher that matches when the given message has exactly one "Bcc" header
     * which matches the given matcher.
     * 
     * @param matcher The value matcher
     * @return A matcher for the "Bcc" header
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Message> hasBcc(Matcher<String> matcher) {
        requireNonNull(matcher);
        return new MessageHasBcc(matcher);
    }
    
    /**
     * Returns a matcher that matches when the given message has exactly one "Bcc" header
     * with the given value.
     * 
     * @param value The expected header value
     * @return A matcher for the "Bcc" header
     * @throws NullPointerException when {@code value} is {@code null}
     */
    public static Matcher<Message> hasBcc(String value) {
        requireNonNull(value);
        return new MessageHasBcc(equalTo(value));
    }
    
    /**
     * Returns a matcher that matches when the given message has exactly one "Subject" header
     * which matches the given matcher.
     * 
     * @param matcher The value matcher
     * @return A matcher for the "Subject" header
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Message> hasSubject(Matcher<String> matcher) {
        requireNonNull(matcher);
        return new MessageHasSubject(matcher);
    }
    
    /**
     * Returns a matcher that matches when the given message has exactly one "Subject" header
     * with the given value.
     * 
     * @param value The expected header value
     * @return A matcher for the "Subject" header
     * @throws NullPointerException when {@code value} is {@code null}
     */
    public static Matcher<Message> hasSubject(String value) {
        requireNonNull(value);
        return new MessageHasSubject(equalTo(value));
    }
    
    /**
     * Returns a matcher that matches when the given message has exactly one header with the
     * given name which matches the given matcher.
     * 
     * @param header The header name
     * @param matcher The value matcher
     * @return A matcher for the header
     * @throws NullPointerException when {@code header} is {@code null} or {@code matcher} is {@code null}
     */
    public static Matcher<Part> hasHeader(String header, Matcher<String> matcher) {
        requireNonNull(header);
        requireNonNull(matcher);
        return new PartHasHeader(header, matcher);
    }
    
    /**
     * Returns a matcher that matches when the given message has exactly one "Subject" header
     * with the given value.
     * 
     * @param header The header name
     * @param value The expected header value
     * @return A matcher for the header
     * @throws NullPointerException when {@code header} is {@code null} or {@code value} is {@code null}
     */
    public static Matcher<Part> hasHeader(String header, String value) {
        requireNonNull(header);
        requireNonNull(value);
        return new PartHasHeader(header, equalTo(value));
    }
    
    /**
     * Returns a matcher that matches when the given message has headers with the
     * given name which match the given matcher.
     * 
     * @param header The header name
     * @param matcher The value matcher
     * @return A matcher for the headers
     * @throws NullPointerException when {@code header} is {@code null} or {@code matcher} is {@code null}
     */
    public static Matcher<Part> hasHeaders(String header, Matcher<Iterable<String>> matcher) {
        requireNonNull(header);
        requireNonNull(matcher);
        return new PartHasHeaders(header, matcher);
    }
    
    /**
     * Returns a matcher that matches when the given message has exactly one date header with the
     * given name which matches the given matcher.
     * 
     * @param header The header name
     * @param matcher The value matcher
     * @return A matcher for the header
     * @throws NullPointerException when {@code header} is {@code null} or {@code matcher} is {@code null}
     */
    public static Matcher<Part> hasDateHeader(String header, Matcher<OffsetDateTime> matcher) {
        requireNonNull(header);
        requireNonNull(matcher);
        return new PartHasDateHeader(header, matcher);
    }
    
    /**
     * Returns a matcher that matches when the given message has date headers with the
     * given name which match the given matcher.
     * 
     * @param header The header name
     * @param matcher The value matcher
     * @return A matcher for the headers
     * @throws NullPointerException when {@code header} is {@code null} or {@code matcher} is {@code null}
     */
    public static Matcher<Part> hasDateHeaders(String header, Matcher<Iterable<OffsetDateTime>> matcher) {
        requireNonNull(header);
        requireNonNull(matcher);
        return new PartHasDateHeaders(header, matcher);
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has a valid DKIM signature
     * that can be verified using the given public key.</p>
     * 
     * <p>DKIM public keys are distributed as DNS TXT records. As tests should not depend
     * on any real DNS records this method accepts a map of virtual TXT records. The map keys
     * are DNS domain names and the map values are DKIM public keys. The public keys must be
     * represented as described in
     * <a href="https://tools.ietf.org/html/rfc4871#section-3.6">section 3.6. of RFC 4871</a>.</p>
     * 
     * <p>Example: &quot;foo._domainkey.example.com&quot; =&gt; &quot;k=rsa; p=Abcd1234&quot;</p>
     * 
     * @param publicKeys Map with public keys
     * @return A matcher for a message
     * @throws NullPointerException when {@code publicKeys} is {@code null}
     */
    public static Matcher<Message> hasValidDkimSignature(Map<String, String> publicKeys) {
        requireNonNull(publicKeys);
        return new MessageHasValidDkimSignature(publicKeys);
    }

    /**
     * Returns a matcher that matches when the given message has a plain text body
     * ({@code Content-Type: text/plain}) with any content.
     * 
     * @return A matcher for a message
     */
    public static Matcher<Message> hasTextBody() {
        return new MessageHasTextBody(any(String.class));
    }

    /**
     * Returns a matcher that matches when the given message has a plain text body
     * ({@code Content-Type: text/plain}) which matches the given matcher.
     * 
     * @param matcher The value matcher
     * @return A matcher for a message
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Message> hasTextBody(Matcher<String> matcher) {
        requireNonNull(matcher);
        return new MessageHasTextBody(matcher);
    }

    /**
     * Returns a matcher that matches when the given message has a multipart body
     * ({@code Content-Type: multipart/*}) with any content.
     * 
     * @return A matcher for a message
     */
    public static Matcher<Message> hasMultipartBody() {
        return new MessageHasMultipartBody();
    }
}
