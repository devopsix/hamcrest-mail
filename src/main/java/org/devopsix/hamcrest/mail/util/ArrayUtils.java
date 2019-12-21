package org.devopsix.hamcrest.mail.util;

import static java.util.Objects.isNull;

public final class ArrayUtils {

    private ArrayUtils() {}
    
    public static final boolean isEmpty(String[] array) {
        return isNull(array) || array.length == 0;
    }
    
    public static final Byte[] toObject(byte[] array) {
        if (isNull(array)) {
            return null;
        }
        Byte[] objectArray = new Byte[array.length];
        for (int i = 0 ; i < array.length ; i++) {
            objectArray[i] = array[i];
        }
        return objectArray;
    }
}
