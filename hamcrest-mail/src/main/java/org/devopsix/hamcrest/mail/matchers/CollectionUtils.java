package org.devopsix.hamcrest.mail.matchers;

import static java.util.Objects.isNull;

import java.util.Collection;

final class CollectionUtils {

  private CollectionUtils() {
  }

  static boolean isEmpty(Collection<?> collection) {
    return isNull(collection) || collection.isEmpty();
  }
}
