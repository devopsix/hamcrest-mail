package org.devopsix.hamcrest.mail.util;

import static java.util.Objects.isNull;

public final class ArrayUtils {

    private ArrayUtils() {}
    
    public static final boolean isEmpty(String[] array) {
        return isNull(array) || array.length == 0;
    }
}
