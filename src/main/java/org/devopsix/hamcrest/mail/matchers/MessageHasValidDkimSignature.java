package org.devopsix.hamcrest.mail.matchers;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.james.jdkim.DKIMVerifier;
import org.apache.james.jdkim.api.PublicKeyRecordRetriever;
import org.apache.james.jdkim.api.SignatureRecord;
import org.apache.james.jdkim.exceptions.FailException;
import org.apache.james.jdkim.exceptions.PermFailException;
import org.apache.james.jdkim.exceptions.TempFailException;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * <p>A matcher that verifies DKIM signatures.</p>
 * 
 * @see Message
 * 
 * @author devopsix
 *
 */
public class MessageHasValidDkimSignature extends TypeSafeDiagnosingMatcher<Message> {
    
    private final DKIMVerifier dkimVerifier;

    public MessageHasValidDkimSignature(Map<String, String> publicKeys) {
        this.dkimVerifier = new DKIMVerifier(new RecordRetriever(publicKeys));
    }
    
    @Override
    protected boolean matchesSafely(Message message, Description mismatch) {
        try {
            List<SignatureRecord> signatures = dkimVerifier.verify(extractInputStream(message));
            return nonNull(signatures) && !signatures.isEmpty();
        } catch (IOException | FailException | MessagingException e) {
            mismatch.appendText(e.getMessage());
            return false;
        }
    }
    
    private InputStream extractInputStream(Message message) throws IOException, MessagingException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        message.writeTo(buffer);
        return new ByteArrayInputStream(buffer.toByteArray());
    }
    
    @Override
    public void describeTo(Description description) {
        description.appendText("message with a valid DKIM signature");
    }
    
    private static class RecordRetriever implements PublicKeyRecordRetriever {
        
        private final Map<String, String> publicKeys;

        public RecordRetriever(Map<String, String> publicKeys) {
            this.publicKeys = publicKeys;
        }

        @Override
        public List<String> getRecords(CharSequence methodAndOption, CharSequence selector, CharSequence token)
                throws TempFailException, PermFailException {
            assertMethodAndOptionAreSupported(methodAndOption);
            String lookupName = buildLookupName(selector, token);
            String record = publicKeys.get(lookupName);
            if (isNull(record)) {
                return emptyList();
            } else {
                return singletonList(record);
            }
        }
        
        private void assertMethodAndOptionAreSupported(CharSequence methodAndOption) {
            if (Objects.equals(methodAndOption, "dns/txt")) {
                return;
            }
            throw new RuntimeException(format("Unsupported method and option: %s", methodAndOption));
        }
        
        private String buildLookupName(CharSequence selector, CharSequence token) {
            return format("%s._domainkey.%s", selector, token);
        }
    }
}
