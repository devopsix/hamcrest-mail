package devopsix.hamcrest.email.matchers;

import static devopsix.hamcrest.email.MessageMatchers.hasValidDkimSignature;
import static java.util.Collections.singletonMap;
import static javax.mail.Message.RecipientType.TO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.james.jdkim.DKIMSigner;
import org.apache.james.jdkim.exceptions.FailException;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import devopsix.hamcrest.email.MessageMatchers;

public class MessageHasValidDkimSignatureTest extends MatcherTest {
    
    @Test
    public void hasValidDkimSignatureShouldMatch() throws Exception {
        MimeMessage message = createMessage();
        String publicKey = "k=rsa; p=" + base64(keyPair.getPublic());
        Map<String, String> publicKeys = singletonMap("foo._domainkey.example.com", publicKey);
        Matcher<Message> matcher = MessageMatchers.hasValidDkimSignature(publicKeys);
        assertThat(message, matcher);
    }
    
    @Test
    public void hasValidDkimSignatureShouldNotMatchWithWrongKey() throws Exception {
        MimeMessage message = createMessage();
        String wrongPublicKey = "k=rsa; p=" + base64(generateKeyPair().getPublic());
        Map<String, String> publicKeys = singletonMap("foo._domainkey.example.com", wrongPublicKey);
        Matcher<Message> matcher = MessageMatchers.hasValidDkimSignature(publicKeys);
        assertThat(message, not(matcher));
    }
    
    @Test
    public void hasValidDkimSignatureShouldNotMatchWithTamperedFromHeader() throws Exception {
        MimeMessage message = createMessage();
        String publicKey = "k=rsa; p=" + base64(keyPair.getPublic());
        Map<String, String> publicKeys = singletonMap("foo._domainkey.example.com", publicKey);
        Matcher<Message> matcher = MessageMatchers.hasValidDkimSignature(publicKeys);
        assertThat(matcher.matches(writeAndReread(message)), is(true));
        message.setFrom("evil@example.com");
        message = writeAndReread(message);
        assertThat(message, not(matcher));
    }
    
    @Test
    public void hasValidDkimSignatureShouldNotMatchWithTamperedToHeader() throws Exception {
        MimeMessage message = createMessage();
        String publicKey = "k=rsa; p=" + base64(keyPair.getPublic());
        Map<String, String> publicKeys = singletonMap("foo._domainkey.example.com", publicKey);
        Matcher<Message> matcher = MessageMatchers.hasValidDkimSignature(publicKeys);
        assertThat(matcher.matches(writeAndReread(message)), is(true));
        message.setRecipient(TO, new InternetAddress("evil@example.com"));
        message = writeAndReread(message);
        assertThat(message, not(matcher));
    }
    
    @Test
    public void hasValidDkimSignatureShouldNotMatchWithTamperedSubjectHeader() throws Exception {
        MimeMessage message = createMessage();
        String publicKey = "k=rsa; p=" + base64(keyPair.getPublic());
        Map<String, String> publicKeys = singletonMap("foo._domainkey.example.com", publicKey);
        Matcher<Message> matcher = MessageMatchers.hasValidDkimSignature(publicKeys);
        assertThat(matcher.matches(writeAndReread(message)), is(true));
        message.setSubject("Evil");
        message = writeAndReread(message);
        assertThat(message, not(matcher));
    }
    
    @Test
    public void shouldValidateGmailMessageSignature() throws Exception {
        // This is the DKIM public key as published by Google for gmail.com at the time
        // the test message was recorded.
        final Map<String, String> gmailPublicKey = singletonMap("20161025._domainkey.gmail.com",
            "k=rsa; p=MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAviPGBk4ZB64UfSqWyAicdR7lodhy"
            + "tae+EYRQVtKDhM+1mXjEqRtP/pDT3sBhazkmA48n2k5NJUyMEoO8nc2r6sUA+/Dom5jRBZp6qDKJOwj"
            + "J5R/OpHamlRG+YRJQqRtqEgSiJWG7h7efGYWmh4URhFM9k9+rmG/CwCgwx7Et+c8OMlngaLl04/bPmf"
            + "pjdEyLWyNimk761CX6KymzYiRDNz1MOJOJ7OzFaS4PFbVLn0m5mf0HVNtBpPwWuCNvaFVflUYxEyblb"
            + "B6h/oWOPGbzoSgtRA47SHV53SwZjIsVpbq4LxUW9IxAEwYzGcSgZ4n5Q8X8TndowsDUzoccPFGhdwIDAQAB");
        MimeMessage message = loadMessage("message-gmail.txt");
        assertThat(message, hasValidDkimSignature(gmailPublicKey));
    }
    
    @Test
    public void shouldValidateOutlookMessageSignature() throws Exception {
        // This is the DKIM public key as published by Microsoft for outlook.com at the time
        // the test message was recorded.
        final Map<String, String> msPublicKey = singletonMap("selector1._domainkey.outlook.com",
            "v=DKIM1; k=rsa; p=MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvWyktrIL8DO/+UGvMbv"
            + "7cPd/Xogpbs7pgVw8y9ldO6AAMmg8+ijENl/c7Fb1MfKM7uG3LMwAr0dVVKyM+mbkoX2k5L7lsROQr0"
            + "Z9gGSpu7xrnZOa58+/pIhd2Xk/DFPpa5+TKbWodbsSZPRN8z0RY5x59jdzSclXlEyN9mEZdmOiKTsOP"
            + "6A7vQxfSya9jg5N81dfNNvP7HnWejMMsKyIMrXptxOhIBuEYH67JDe98QgX14oHvGM2Uz53if/SW8MF"
            + "09rYh9sp4ZsaWLIg6T343JzlbtrsGRGCDJ9JPpxRWZimtz+Up/BlKzT6sCCrBihb/Bi3pZiEBB4Ui/v"
            + "ruL5RCQIDAQAB; n=2048,1452627113,1468351913");
        MimeMessage message = loadMessage("message-outlook.txt");
        assertThat(message, hasValidDkimSignature(msPublicKey));
    }
    
    private MimeMessage createMessage() throws IOException, FailException, MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.setSentDate(new Date());
        message.setFrom("joe.average@example.com");
        message.setRecipient(TO, new InternetAddress("joe.average@example.com"));
        message.setSubject("Message from Joe");
        message.setText("Lorem ipsum");
        signMessage(message);
        return writeAndReread(message);
    }
    
    private static final KeyPair keyPair = generateKeyPair();
    
    private void signMessage(MimeMessage message) throws IOException, FailException, MessagingException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        message.writeTo(buffer);
        DKIMSigner signer = new DKIMSigner("v=1; a=rsa-sha256; d=example.com; s=foo; h=mime-version:from:date:message-id:subject:to; b=; bh=;", keyPair.getPrivate());
        String dkimHeader = signer.sign(new ByteArrayInputStream(buffer.toByteArray()));
        message.setHeader("DKIM-Signature", dkimHeader.substring("DKIM-Signature:".length()).trim());
    }
    
    private MimeMessage writeAndReread(Message message) throws IOException, MessagingException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        message.writeTo(buffer);
        Session session = Session.getDefaultInstance(new Properties());
        return new MimeMessage(session, new ByteArrayInputStream(buffer.toByteArray()));
    }
    
    private String base64(PublicKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    private static final KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
