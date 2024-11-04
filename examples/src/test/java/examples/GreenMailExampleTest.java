package examples;

import static com.icegreen.greenmail.util.ServerSetupTest.SMTP;
import static java.time.OffsetDateTime.now;
import static java.time.OffsetDateTime.parse;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Collections.emptyMap;
import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;
import static org.devopsix.hamcrest.mail.MailMatchers.hasAddress;
import static org.devopsix.hamcrest.mail.MailMatchers.hasBcc;
import static org.devopsix.hamcrest.mail.MailMatchers.hasCc;
import static org.devopsix.hamcrest.mail.MailMatchers.hasDate;
import static org.devopsix.hamcrest.mail.MailMatchers.hasDateHeaders;
import static org.devopsix.hamcrest.mail.MailMatchers.hasFrom;
import static org.devopsix.hamcrest.mail.MailMatchers.hasHeader;
import static org.devopsix.hamcrest.mail.MailMatchers.hasHeaders;
import static org.devopsix.hamcrest.mail.MailMatchers.hasMultipartContent;
import static org.devopsix.hamcrest.mail.MailMatchers.hasPersonal;
import static org.devopsix.hamcrest.mail.MailMatchers.hasRecipients;
import static org.devopsix.hamcrest.mail.MailMatchers.hasReplyTo;
import static org.devopsix.hamcrest.mail.MailMatchers.hasSender;
import static org.devopsix.hamcrest.mail.MailMatchers.hasSubject;
import static org.devopsix.hamcrest.mail.MailMatchers.hasTextContent;
import static org.devopsix.hamcrest.mail.MailMatchers.hasTo;
import static org.devopsix.hamcrest.mail.MailMatchers.hasValidDkimSignature;
import static org.exparity.hamcrest.date.OffsetDateTimeMatchers.sameOrBefore;
import static org.exparity.hamcrest.date.OffsetDateTimeMatchers.within;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.not;

import com.icegreen.greenmail.junit5.GreenMailExtension;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

/**
 * This class demonstrates how the AssertJ Mail assertions can be used together
 * with GreenMail for verifying an email sent by an application.
 *
 * <p><a href="https://greenmail-mail-test.github.io/greenmail/">GreenMail</a> is an email server for testing purposes.</p>
 *
 * @author devopsix
 */
public class GreenMailExampleTest {

  // This extension will fire up an SMTP server listening at 127.0.0.1:3025
  // for every test.
  @RegisterExtension
  static GreenMailExtension greenMail = new GreenMailExtension(SMTP);

  @BeforeEach
  public void sendMessage() throws Exception {
    // This is what your application code would do and what you usually
    // would invoke a method on the test subject for.
    // With GreenMail it does not matter whether your application uses JavaMail
    // or any other library. It would just have to sent a message via SMTP.
    Properties properties = new Properties();
    properties.put("mail.smtp.host", "127.0.0.1");
    properties.put("mail.smtp.port", "3025");
    Session session = Session.getInstance(properties);
    MimeMessage message = new MimeMessage(session);
    message.setFrom("bob@example.com");
    message.setRecipients(TO, "Anna <anna@example.com>");
    message.setSubject("Message from Bob");
    message.setHeader("X-Custom-Header", "Foo");
    message.addHeader("X-Custom-Multi-Header", "Foo");
    message.addHeader("X-Custom-Multi-Header", "Bar");
    message.addHeader("Resent-Date", "Wed, 1 Dec 82 14:49:44 +0100");
    message.addHeader("Resent-Date", "Sun, 1 Dec 2019 14:49:44 +0100");
    message.setText("Lorem ipsum");
    Transport.send(message);
  }

  @Test
  public void messageShouldHaveFrom() {
    MimeMessage message = getReceivedMessage();
    assertThat(message, hasFrom(not(emptyOrNullString())));
    assertThat(message, hasFrom("bob@example.com"));
    assertThat(message, hasFrom(equalTo("bob@example.com")));
    assertThat(message, hasFrom(endsWith("@example.com")));
  }

  @Test
  public void messageShouldHaveDate() {
    MimeMessage message = getReceivedMessage();
    // These OffsetDateTime matchers are from eXparity/hamcrest-date:
    // https://github.com/eXparity/hamcrest-date
    assertThat(message, hasDate(sameOrBefore(now())));
    assertThat(message, hasDate(within(1, MINUTES, now())));
  }

  @Test
  public void messageShouldNotHaveSender() {
    MimeMessage message = getReceivedMessage();
    assertThat(message, hasSender(emptyOrNullString()));
  }

  @Test
  public void messageShouldNotHaveReplyTo() {
    MimeMessage message = getReceivedMessage();
    assertThat(message, hasReplyTo(emptyOrNullString()));
  }

  @Test
  public void messageShouldHaveTo() {
    MimeMessage message = getReceivedMessage();
    assertThat(message, hasTo(not(emptyOrNullString())));
    assertThat(message, hasTo("Anna <anna@example.com>"));
    assertThat(message, hasTo(equalTo("Anna <anna@example.com>")));
    assertThat(message, hasTo(endsWith("@example.com>")));
  }

  @Test
  public void messageShouldNotHaveCc() {
    MimeMessage message = getReceivedMessage();
    assertThat(message, hasCc(emptyOrNullString()));
  }

  @Test
  public void messageShouldNotHaveBcc() {
    MimeMessage message = getReceivedMessage();
    assertThat(message, hasBcc(emptyOrNullString()));
  }

  @Test
  @SuppressWarnings({"unchecked", "rawtypes"})
  public void messageShouldHaveRecipients() {
    MimeMessage message = getReceivedMessage();
    assertThat(message, hasRecipients(iterableWithSize(1)));
    assertThat(message, hasRecipients(TO, iterableWithSize(1)));
    assertThat(message, hasRecipients(CC, (Matcher) emptyIterable()));
    assertThat(message, hasRecipients((Matcher) hasItem(hasAddress(equalTo("anna@example.com")))));
    assertThat(message, hasRecipients(TO, (Matcher) hasItem(
        both(hasAddress(equalTo("anna@example.com"))).and(hasPersonal(equalTo("Anna"))))));
  }

  @Test
  public void messageShouldHaveReceived() {
    MimeMessage message = getReceivedMessage();
    assertThat(message, hasHeaders("Received", iterableWithSize(1)));
  }

  @Test
  public void messageShouldHaveSubject() {
    MimeMessage message = getReceivedMessage();
    assertThat(message, hasSubject("Message from Bob"));
    assertThat(message, hasSubject(not(emptyOrNullString())));
    assertThat(message, hasSubject(equalTo("Message from Bob")));
    assertThat(message, hasSubject(containsString("Bob")));
  }

  @Test
  public void messageShouldHaveCustomHeader() {
    MimeMessage message = getReceivedMessage();
    assertThat(message, hasHeader("X-Custom-Header", "Foo"));
    assertThat(message, hasHeader("X-Custom-Header", not(emptyOrNullString())));
    assertThat(message, hasHeader("X-Custom-Header", equalTo("Foo")));
    assertThat(message, hasHeader("X-Custom-Header", containsString("o")));
  }

  @Test
  @SuppressWarnings({"unchecked", "rawtypes"})
  public void messageShouldHaveMultipleCustomHeaders() {
    MimeMessage message = getReceivedMessage();
    assertThat(message, hasHeaders("X-Custom-Multi-Header", (Matcher) hasItem(equalTo("Foo"))));
    assertThat(message, hasHeaders("X-Custom-Multi-Header", (Matcher) hasItem(equalTo("Bar"))));
    assertThat(message,
        hasHeaders("X-Custom-Multi-Header", hasItems(equalTo("Foo"), equalTo("Bar"))));
    assertThat(message, hasHeaders("X-Custom-Multi-Header", (Matcher) contains("Foo", "Bar")));
  }

  @Test
  @SuppressWarnings({"unchecked", "rawtypes"})
  public void messageShouldHaveMultipleResentDateHeaders() {
    MimeMessage message = getReceivedMessage();
    assertThat(message, hasDateHeaders("Resent-Date",
        (Matcher) hasItem(equalTo(parse("1982-12-01T14:49:44+01:00")))));
    assertThat(message, hasDateHeaders("Resent-Date",
        (Matcher) hasItem(equalTo(parse("2019-12-01T14:49:44+01:00")))));
    assertThat(message, hasDateHeaders("Resent-Date", hasItems(
        equalTo(parse("1982-12-01T14:49:44+01:00")),
        equalTo(parse("2019-12-01T14:49:44+01:00")))));
    assertThat(message, hasDateHeaders("Resent-Date", (Matcher) contains(
        parse("1982-12-01T14:49:44+01:00"),
        parse("2019-12-01T14:49:44+01:00"))));
  }

  @Test
  public void messageShouldNotHaveDkimSignature() {
    MimeMessage message = getReceivedMessage();
    assertThat(message, hasHeader("DKIM-Signature", emptyOrNullString()));
    assertThat(message, not(hasValidDkimSignature(emptyMap())));
  }

  @Test
  public void messageShouldHaveTextBody() {
    MimeMessage message = getReceivedMessage();
    assertThat(message, hasTextContent(containsString("Lorem ipsum")));
    assertThat(message, not(hasMultipartContent()));
  }

  private MimeMessage getReceivedMessage() {
    MimeMessage[] messages = greenMail.getReceivedMessages();
    assertThat(messages, arrayWithSize(1));
    return messages[0];
  }
}
