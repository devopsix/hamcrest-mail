package org.devopsix.hamcrest.mail.matchers;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.MimeUtility;

final class HeaderUtils {

    private HeaderUtils() {}
    
    static String decodeHeader(String rawvalue) {
        try {
            return MimeUtility.decodeText(MimeUtility.unfold(rawvalue));
        } catch (UnsupportedEncodingException ex) {
            return rawvalue;
        }
    }
}
