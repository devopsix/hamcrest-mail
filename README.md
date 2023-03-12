[![Build Status](https://img.shields.io/github/actions/workflow/status/devopsix/hamcrest-mail/build.yml)](https://github.com/devopsix/hamcrest-mail/actions?query=workflow%3ABuild)
[![Maven Central](https://img.shields.io/maven-central/v/org.devopsix/hamcrest-mail.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22org.devopsix%22%20AND%20a:%22hamcrest-mail%22)
[![License](https://img.shields.io/github/license/devopsix/hamcrest-mail)](LICENSE.txt)

# Hamcrest Mail

Hamcrest Mail is an extension library for the [Java Hamcrest][] matcher library.
It provides matchers for types from the javax.mail and jakarta.mail packages.

The [assertj-mail][] sister project provides a set of AssertJ assertions with similar features.

## Usage
To use Hamcrest Mail in a Maven project add a dependency on `org.devopsix:hamcrest-mail` (for Java EE 8 / javax.mail) or `org.devopsix:hamcrest-mail-jakarta` (for Jakarta EE 9+ / jakarta.mail) to the pom.xml file.

```xml
<!-- Maven coordinates for Java EE 8 / javax.mail -->
<dependency>
    <groupId>org.devopsix</groupId>
    <artifactId>hamcrest-mail</artifactId>
    <version>1.1.2 </version>
    <scope>test</scope>
</dependency>
<!-- Maven coordinates for Jakarta EE 9+ / jakarta.mail -->
<dependency>
    <groupId>org.devopsix</groupId>
    <artifactId>hamcrest-mail-jakarta</artifactId>
    <version>1.1.2 </version>
    <scope>test</scope>
</dependency>
```

The matchers are available as static methods on the `MailMatchers` class.

Here are a few examples:

```java
Message message;
Assert.assertThat(message, MailMatchers.hasFrom("anna@example.com"));

Assert.assertThat(message, MailMatchers.hasTo("bob@example.com"));

Assert.assertThat(message, MailMatchers.hasRecipients(Matchers.iterableWithSize(1)));

Assert.assertThat(message, MailMatchers.hasSubject("Message from Anna"));

Assert.assertThat(message, MailMatchers.hasHeader("Return-Path", Matchers.notNullValue()));

Assert.assertThat(message, MailMatchers.hasDateHeader("Resent-Date", Matchers.isA(OffsetDateTime.class)));

// OffsetDateTimeMatchers is from eXparity/hamcrest-date:
// https://github.com/eXparity/hamcrest-date
Assert.assertThat(message, MailMatchers.hasDate(OffsetDateTimeMatchers.within(1, MINUTES, now())));

// Casting to Matcher is required when a matcher's signature is
// Matcher<Iterable<? extends T>> or Matcher<Iterable<? super T>>
Assert.assertThat(message, MailMatchers.hasHeaders("Received", (Matcher)Matchers.hasItems(
        Matchers.containsString("host1"), Matchers.containsString("host2"))));
```

More example can be found in the [examples](examples/) directory (for Java EE 8 / javax.mail) and in the [examples-jakarta](examples-jakarta/) directory (for Jakarta EE 9+ / jakarta.mail).

## Matchers

* __hasDate__ - Tests the “Date” header against a given date or matcher
* __hasFrom__ - Tests the “From” header against a given string or matcher 
* __hasSender__ - Tests the “Sender” header against a given string or matcher
* __hasReplyTo__ - Tests the “Reply-To” header against a given string or matcher
* __hasTo__ - Tests the “To” header against a given string or matcher
* __hasCc__ - Tests the “Cc” header against a given string or matcher
* __hasBcc__ - Tests the “Bcc” header against a given string or matcher
* __hasRecipients__ - Tests a message's recipients against a given matcher
* __hasAddress__ - Tests an InternetAddress' address against a given matcher
* __hasPersonal__ - Tests an InternetAddress' personal name against a given matcher
* __hasSubject__ - Tests the “Subject” header against a given string or matcher
* __hasHeader__ - Tests a named header against a given string or matcher
* __hasHeaders__ - Tests a named header which may occur multiple times against a given matcher
* __hasDateHeader__ - Tests a named date header against a given date or matcher
* __hasDateHeaders__ - Tests a named date header which may occur multiple times against a given matcher
* __hasValidDkimSignature__ - Tests a message for having a valid [DKIM][] signature
* __hasTextContent__ - Tests a message or a part of a multipart message for having plain text content matching a given matcher
* __hasBinaryContent__ - Tests a message or a part of a multipart message for having binary content matching a given matcher
* __hasMultipartContent__ - Tests a message or a part of a multipart message for having multipart content matching a given matcher
* __hasMultipartContentRecursive__ - Tests a message or a part of a multipart message and recursively all child parts thereof for having at least one part with multipart content matching a given matcher
* __multipartMixed__ - Tests a multipart for having “multipart/mixed” content type
* __multipartAlternative__ - Tests a multipart for having “multipart/alternative” content type
* __multipartRelated__ - Tests a multipart for having “multipart/related” content type
* __multipartContentType__ - Tests a multipart's content type against a given matcher
* __hasPart__ - Tests a multipart for having at least one part matching a given matcher
* __hasParts__ - Tests a multipart for having parts matching a given matcher

[Java Hamcrest]: http://github.com/hamcrest/JavaHamcrest
[DKIM]: https://tools.ietf.org/html/rfc4871
[assertj-mail]: https://github.com/devopsix/assertj-mail
