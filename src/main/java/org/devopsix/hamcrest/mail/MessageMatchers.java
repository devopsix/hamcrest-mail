package org.devopsix.hamcrest.mail;

import static java.util.Objects.requireNonNull;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.startsWith;

import java.time.OffsetDateTime;
import java.util.Map;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;

import org.devopsix.hamcrest.mail.matchers.AddressHasAddress;
import org.devopsix.hamcrest.mail.matchers.AddressHasPersonal;
import org.devopsix.hamcrest.mail.matchers.MessageHasBcc;
import org.devopsix.hamcrest.mail.matchers.MessageHasCc;
import org.devopsix.hamcrest.mail.matchers.MessageHasDate;
import org.devopsix.hamcrest.mail.matchers.MessageHasFrom;
import org.devopsix.hamcrest.mail.matchers.MessageHasRecipients;
import org.devopsix.hamcrest.mail.matchers.MessageHasReplyTo;
import org.devopsix.hamcrest.mail.matchers.MessageHasSender;
import org.devopsix.hamcrest.mail.matchers.MessageHasSubject;
import org.devopsix.hamcrest.mail.matchers.MessageHasTo;
import org.devopsix.hamcrest.mail.matchers.MessageHasValidDkimSignature;
import org.devopsix.hamcrest.mail.matchers.MultipartHasContentType;
import org.devopsix.hamcrest.mail.matchers.MultipartHasParts;
import org.devopsix.hamcrest.mail.matchers.PartHasBinaryContent;
import org.devopsix.hamcrest.mail.matchers.PartHasDateHeader;
import org.devopsix.hamcrest.mail.matchers.PartHasDateHeaders;
import org.devopsix.hamcrest.mail.matchers.PartHasHeader;
import org.devopsix.hamcrest.mail.matchers.PartHasHeaders;
import org.devopsix.hamcrest.mail.matchers.PartHasMultipartContent;
import org.devopsix.hamcrest.mail.matchers.PartHasTextContent;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

/**
 * This class provides static access to all hamcrest-mail matchers.
 */
public final class MessageMatchers {

    private MessageMatchers() { }
    
    /**
     * <p>Returns a matcher that matches when the given message has exactly one "Date" header
     * and this header matches the given matcher.</p>
     * 
     * @param matcher The value matcher
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Message> hasDate(Matcher<OffsetDateTime> matcher) {
        requireNonNull(matcher);
        return new MessageHasDate(matcher);
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has exactly one "Date" header
     * and this header has the given value.</p>
     * 
     * @param value The expected header value
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code value} is {@code null}
     */
    public static Matcher<Message> hasDate(OffsetDateTime value) {
        requireNonNull(value);
        return new MessageHasDate(equalTo(value));
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has exactly one "From" header
     * and this header matches the given matcher.</p>
     * 
     * @param matcher The value matcher
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Message> hasFrom(Matcher<String> matcher) {
        requireNonNull(matcher);
        return new MessageHasFrom(matcher);
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has exactly one "From" header
     * and this header has the given value.</p>
     * 
     * @param value The expected header value
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code value} is {@code null}
     */
    public static Matcher<Message> hasFrom(String value) {
        requireNonNull(value);
        return new MessageHasFrom(equalTo(value));
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has exactly one "Sender" header
     * and this header matches the given matcher.</p>
     * 
     * @param matcher The value matcher
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Message> hasSender(Matcher<String> matcher) {
        requireNonNull(matcher);
        return new MessageHasSender(matcher);
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has exactly one "Sender" header
     * and this header has the given value.</p>
     * 
     * @param value The expected header value
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code value} is {@code null}
     */
    public static Matcher<Message> hasSender(String value) {
        requireNonNull(value);
        return new MessageHasSender(equalTo(value));
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has exactly one "Reply-To" header
     * and this header matches the given matcher.</p>
     * 
     * @param matcher The value matcher
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Message> hasReplyTo(Matcher<String> matcher) {
        requireNonNull(matcher);
        return new MessageHasReplyTo(matcher);
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has exactly one "Reply-To" header
     * and this header has the given value.</p>
     * 
     * @param value The expected header value
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code value} is {@code null}
     */
    public static Matcher<Message> hasReplyTo(String value) {
        requireNonNull(value);
        return new MessageHasReplyTo(equalTo(value));
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has exactly one "To" header
     * and this header matches the given matcher.</p>
     * 
     * @param matcher The value matcher
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Message> hasTo(Matcher<String> matcher) {
        requireNonNull(matcher);
        return new MessageHasTo(matcher);
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has exactly one "To" header
     * and this header has the given value.</p>
     * 
     * @param value The expected header value
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code value} is {@code null}
     */
    public static Matcher<Message> hasTo(String value) {
        requireNonNull(value);
        return new MessageHasTo(equalTo(value));
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has exactly one "Cc" header
     * and this header matches the given matcher.</p>
     * 
     * @param matcher The value matcher
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Message> hasCc(Matcher<String> matcher) {
        requireNonNull(matcher);
        return new MessageHasCc(matcher);
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has exactly one "Cc" header
     * and this header has the given value.</p>
     * 
     * @param value The expected header value
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code value} is {@code null}
     */
    public static Matcher<Message> hasCc(String value) {
        requireNonNull(value);
        return new MessageHasCc(equalTo(value));
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has exactly one "Bcc" header
     * and this header matches the given matcher.</p>
     * 
     * @param matcher The value matcher
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Message> hasBcc(Matcher<String> matcher) {
        requireNonNull(matcher);
        return new MessageHasBcc(matcher);
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has exactly one "Bcc" header
     * and this header has the given value.</p>
     * 
     * @param value The expected header value
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code value} is {@code null}
     */
    public static Matcher<Message> hasBcc(String value) {
        requireNonNull(value);
        return new MessageHasBcc(equalTo(value));
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has a list of recipients
     * (To, Cc, Bcc) which matches the given matcher.</p>
     * 
     * @param matcher A matcher for the list of recipients
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Message> hasRecipients(Matcher<Iterable<Address>> matcher) {
        requireNonNull(matcher);
        return new MessageHasRecipients(matcher);
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has a list of recipients
     * of the given type which matches the given matcher.</p>
     * 
     * @param type Type of recipients
     * @param matcher A matcher for the recipients
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Message> hasRecipients(RecipientType type, Matcher<Iterable<Address>> matcher) {
        requireNonNull(matcher);
        return new MessageHasRecipients(type, matcher);
    }
    
    /**
     * <p>Returns a matcher that matches when the given address is an {@code InternetAddress}
     * with an {@code address} property that matches the given matcher.</p>
     * 
     * @see InternetAddress#getAddress()
     * 
     * @param matcher A matcher for the address part
     * @return A matcher for an Address
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Address> hasAddress(Matcher<String> matcher) {
        requireNonNull(matcher);
        return new AddressHasAddress(matcher);
    }
    
    /**
     * <p>Returns a matcher that matchers when the given address is an {@code InternetAddress}
     * with an {@code personal} property that matches the given matcher.</p>
     * 
     * @see InternetAddress#getPersonal()
     * 
     * @param matcher A matcher for the personal part
     * @return A matcher for an Address
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Address> hasPersonal(Matcher<String> matcher) {
        requireNonNull(matcher);
        return new AddressHasPersonal(matcher);
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has exactly one "Subject" header
     * and this header matches the given matcher.</p>
     * 
     * @param matcher The value matcher
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Message> hasSubject(Matcher<String> matcher) {
        requireNonNull(matcher);
        return new MessageHasSubject(matcher);
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has exactly one "Subject" header
     * and this header has the given value.</p>
     * 
     * @param value The expected header value
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code value} is {@code null}
     */
    public static Matcher<Message> hasSubject(String value) {
        requireNonNull(value);
        return new MessageHasSubject(equalTo(value));
    }
    
    /**
     * <p>Returns a matcher that matches when the given message part has exactly one header with the
     * given name and this header matches the given matcher.</p>
     * 
     * @param header The header name
     * @param matcher The value matcher
     * @return A matcher for a {@link Part}
     * @throws NullPointerException when {@code header} is {@code null} or {@code matcher} is {@code null}
     */
    public static Matcher<Part> hasHeader(String header, Matcher<String> matcher) {
        requireNonNull(header);
        requireNonNull(matcher);
        return new PartHasHeader(header, matcher);
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has exactly one "Subject" header
     * and this header has the given value.</p>
     * 
     * @param header The header name
     * @param value The expected header value
     * @return A matcher for a {@link Part}
     * @throws NullPointerException when {@code header} is {@code null} or {@code value} is {@code null}
     */
    public static Matcher<Part> hasHeader(String header, String value) {
        requireNonNull(header);
        requireNonNull(value);
        return new PartHasHeader(header, equalTo(value));
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has headers with the
     * given name which match the given matcher.</p>
     * 
     * @param header The header name
     * @param matcher The value matcher
     * @return A matcher for a {@link Part}
     * @throws NullPointerException when {@code header} is {@code null} or {@code matcher} is {@code null}
     */
    public static Matcher<Part> hasHeaders(String header, Matcher<Iterable<String>> matcher) {
        requireNonNull(header);
        requireNonNull(matcher);
        return new PartHasHeaders(header, matcher);
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has exactly one date header with the
     * given name and this header matches the given matcher.</p>
     * 
     * @param header The header name
     * @param matcher The value matcher
     * @return A matcher for a {@link Part}
     * @throws NullPointerException when {@code header} is {@code null} or {@code matcher} is {@code null}
     */
    public static Matcher<Part> hasDateHeader(String header, Matcher<OffsetDateTime> matcher) {
        requireNonNull(header);
        requireNonNull(matcher);
        return new PartHasDateHeader(header, matcher);
    }
    
    /**
     * <p>Returns a matcher that matches when the given message has date headers with the
     * given name which match the given matcher.</p>
     * 
     * @param header The header name
     * @param matcher The value matcher
     * @return A matcher for a {@link Part}
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
     * are fully qualified DNS domain names (without trailing dot) and the map values are
     * DKIM public keys. The public keys must be represented as described in
     * <a href="https://tools.ietf.org/html/rfc4871#section-3.6">section 3.6. of RFC 4871</a>.</p>
     * 
     * <p>Example: &quot;foo._domainkey.example.com&quot; =&gt; &quot;k=rsa; p=Abcd1234&quot;</p>
     * 
     * @param publicKeys Map with public keys
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code publicKeys} is {@code null}
     */
    public static Matcher<Message> hasValidDkimSignature(Map<String, String> publicKeys) {
        requireNonNull(publicKeys);
        return new MessageHasValidDkimSignature(publicKeys);
    }

    /**
     * <p>Returns a matcher that matches when the given part has plain text content
     * ({@code Content-Type: text/plain}) with any content.</p>
     * 
     * @return A matcher for a {@link Part}
     */
    public static Matcher<Part> hasTextContent() {
        return new PartHasTextContent(any(String.class));
    }

    /**
     * <p>Returns a matcher that matches when the given part has plain text content
     * ({@code Content-Type: text/plain}) which matches the given matcher.</p>
     * 
     * @param matcher The content matcher
     * @return A matcher for a {@link Part}
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Part> hasTextContent(Matcher<String> matcher) {
        requireNonNull(matcher);
        return new PartHasTextContent(matcher);
    }

    /**
     * <p>Returns a matcher that matches when the given part has binary content
     * with any content.</p>
     * 
     * @return A matcher for a {@link Part}
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Matcher<Part> hasBinaryContent() {
        return new PartHasBinaryContent((Matcher)anything());
    }

    /**
     * <p>Returns a matcher that matches when the given part has binary content
     * which matches the given matcher.</p>
     * 
     * @param matcher The content matcher
     * @return A matcher for a {@link Part}
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Part> hasBinaryContent(Matcher<Byte[]> matcher) {
        requireNonNull(matcher);
        return new PartHasBinaryContent(matcher);
    }

    /**
     * <p>Returns a matcher that matches when the given part has multipart content
     * ({@code Content-Type: multipart/*}).</p>
     * 
     * @return A matcher for a {@link Part}
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Matcher<Part> hasMultipartContent() {
        return new PartHasMultipartContent(false, (Matcher)anything());
    }

    /**
     * <p>Returns a matcher that matches when the given part has multipart content
     * ({@code Content-Type: multipart/*}) which matches the given matcher.</p>
     * 
     * @param matcher The content matcher
     * @return A matcher for a {@link Part}
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Part> hasMultipartContent(Matcher<Multipart> matcher) {
        requireNonNull(matcher);
        return new PartHasMultipartContent(false, matcher);
    }

    /**
     * <p>Returns a matcher that matches when the given part or any child part has multipart content
     * ({@code Content-Type: multipart/*}) which matches the given matcher.<p>
     * 
     * @param matcher The multipart matcher
     * @return A matcher for a {@link Message}
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Part> hasMultipartContentRecursive(Matcher<Multipart> matcher) {
        requireNonNull(matcher);
        return new PartHasMultipartContent(true, matcher);
    }
    
    /**
     * <p>Returns a matcher that matches when the given multipart has 
     * {@code multipart/mixed} content type.</p>
     * 
     * @return A matcher for a {@link Multipart}
     */
    public static Matcher<Multipart> multipartMixed() {
        return new MultipartHasContentType(startsWith("multipart/mixed;"));
    }
    
    /**
     * <p>Returns a matcher that matches when the given multipart has 
     * {@code multipart/alternative} content type.</p>
     * 
     * @return A matcher for a {@link Multipart}
     */
    public static Matcher<Multipart> multipartAlternative() {
        return new MultipartHasContentType(startsWith("multipart/alternative;"));
    }
    
    /**
     * <p>Returns a matcher that matches when the given multipart has 
     * {@code multipart/related} content type.</p>
     * 
     * @return A matcher for a {@link Multipart}
     */
    public static Matcher<Multipart> multipartRelated() {
        return new MultipartHasContentType(startsWith("multipart/related;"));
    }
    
    /**
     * <p>Returns a matcher that matches when the given multipart has a content type
     * which matches the given matcher.</p>
     * 
     * <p>Mind that the content type string contains line breaks and the boundary,
     * e.g. {@code multipart/alternative; \r\n\tboundary="----=_Part_17_872522004.1577017605979"}.</p>
     * 
     * @param matcher The content matcher
     * @return A matcher for a {@link Multipart}
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Multipart> multipartContentType(Matcher<String> matcher) {
        requireNonNull(matcher);
        return new MultipartHasContentType(matcher);
    }

    /**
     * <p>Returns a matcher that matches when the given multipart's parts
     * match the given matcher.</p>
     * 
     * <p>Only immediate child parts of the given multipart are considered.</p>
     * 
     * <p>For testing all parts at any depth see {@link #hasMultipartContentRecursive(Matcher)}.</p>
     * 
     * @param matcher The parts matcher
     * @return A matcher for a {@link Multipart}
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    public static Matcher<Multipart> hasParts(Matcher<Iterable<Part>> matcher) {
        requireNonNull(matcher);
        return new MultipartHasParts(matcher);
    }

    /**
     * <p>Returns a matcher that matches when the given multipart has at least one part
     * which matches the given matcher.</p<
     * 
     * <p>Only immediate child parts of the given multipart are considered.</p>
     * 
     * @param matcher The part matcher
     * @return A matcher for a {@link Multipart}
     * @throws NullPointerException when {@code matcher} is {@code null}
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Matcher<Multipart> hasPart(Matcher<Part> matcher) {
        requireNonNull(matcher);
        return new MultipartHasParts((Matcher)hasItem(matcher));
    }

    /**
     * <p>Returns a matcher that matches when the given multipart has the given number of parts.</p>
     * 
     * @param size The number of parts
     * @return A matcher for a {@link Multipart}
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Matcher<Multipart> hasParts(int size) {
        return new MultipartHasParts((Matcher)Matchers.hasSize(size));
    }
}
