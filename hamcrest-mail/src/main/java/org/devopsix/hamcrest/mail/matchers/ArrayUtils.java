package org.devopsix.hamcrest.mail.matchers;

import static java.util.Objects.isNull;

final class ArrayUtils {

  private ArrayUtils() {
  }

  static boolean isEmpty(String[] array) {
    return isNull(array) || array.length == 0;
  }

  static Byte[] toObject(byte[] array) {
    if (isNull(array)) {
      return null;
    }
    Byte[] objectArray = new Byte[array.length];
    for (int i = 0; i < array.length; i++) {
      objectArray[i] = array[i];
    }
    return objectArray;
  }
}
