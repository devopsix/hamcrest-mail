package devopsix.hamcrest.email.util;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.MimeUtility;

public final class HeaderUtils {

    private HeaderUtils() {}
    
    public static String decodeHeader(String rawvalue) {
        try {
            return MimeUtility.decodeText(MimeUtility.unfold(rawvalue));
        } catch (UnsupportedEncodingException ex) {
            return rawvalue;
        }
    }
}
