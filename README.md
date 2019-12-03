# Hamcrest Mail

Hamcrest Mail is an extension library for the [Java Hamcrest][] matcher library.
It provides matchers for types from the javax.mail package.

## Usage
To use Hamcrest Mail in a Maven project add this dependency to the pom.xml file:

    <dependency>
        <groupId>devopsix</groupId>
        <artifactId>hamcrest-mail</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <scope>test</scope>
    </dependency>

The matchers are exposed as static methods on the `MessageMatchers` class.

Here are a few examples:

    Message message;
    Assert.assertThat(message, MessageMatchers.hasFrom("anna@example.com"));

    Message message;
    Assert.assertThat(message, MessageMatchers.hasTo("bob@example.com"));

    Message message;
    Assert.assertThat(message, MessageMatchers.hasSubject("Message from Anna"));

    Message message;
    Assert.assertThat(message, MessageMatchers.hasHeader("Return-Path", Matchers.notNullValue()));

    Message message;
    Assert.assertThat(message, MessageMatchers.hasDateHeader("Resent-Date", Matchers.isA(OffsetDateTime.class)));

    Message message;
    // OffsetDateTimeMatchers is from eXparity/hamcrest-date:
    // https://github.com/eXparity/hamcrest-date
    Assert.assertThat(message, MessageMatchers.hasDate(OffsetDateTimeMatchers.within(1, MINUTES, now())));

    Message message;
    // Casting to Matcher is required when a matcher's signature is
    // Matcher<Iterable<? extends T>> or Matcher<Iterable<? super T>>
    Assert.assertThat(message, MessageMatchers.hasHeaders("Received", (Matcher)Matchers.hasItems(
            Matchers.containsString("host1"), Matchers.containsString("host2"))));

## Matchers

* __hasDate__ - Tests the `Date` header against a given date or matcher
* __hasFrom__ - Tests the `From` header against a given string or matcher 
* __hasSender__ - Tests the `Sender` header against a given string or matcher
* __hasReplyTo__ - Tests the `Reply-To` header against a given string or matcher
* __hasTo__ - Tests the `To` header against a given string or matcher
* __hasCc__ - Tests the `Cc` header against a given string or matcher
* __hasBcc__ - Tests the `Bcc` header against a given string or matcher
* __hasSubject__ - Tests the `Subject` header against a given string or matcher
* __hasHeader__ - Tests a named header against a given string or matcher
* __hasHeaders__ - Tests a named header which may occur multiple times against a given matcher
* __hasDateHeader__ - Tests a named date header against a given date or matcher
* __hasDateHeaders__ - Tests a named date header which may occur multiple times against a given matcher

[Java Hamcrest]: http://github.com/hamcrest/JavaHamcrest
