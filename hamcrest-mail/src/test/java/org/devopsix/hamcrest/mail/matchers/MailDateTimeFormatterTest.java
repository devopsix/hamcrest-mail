package org.devopsix.hamcrest.mail.matchers;

import static org.devopsix.hamcrest.mail.matchers.MailDateTimeFormatter.trimTrailingZoneText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;

public class MailDateTimeFormatterTest {

  @Test
  public void shouldReadTwoDigitYear() {
    OffsetDateTime date =
        OffsetDateTime.parse("Wed, 1 Dec 82 14:49:44 +0100", MailDateTimeFormatter.MAIL_DATE_TIME);
    assertThat(date, notNullValue());
    assertThat(date.getYear(), is(1982));
  }

  @Test
  public void shouldReadFourDigitYear() {
    OffsetDateTime date = OffsetDateTime.parse("Sun, 1 Dec 2019 14:49:44 +0100",
        MailDateTimeFormatter.MAIL_DATE_TIME);
    assertThat(date, notNullValue());
    assertThat(date.getYear(), is(2019));
  }

  @Test
  public void shouldAcceptMissingDay() {
    OffsetDateTime date =
        OffsetDateTime.parse("1 Dec 2019 14:49:44 +0100", MailDateTimeFormatter.MAIL_DATE_TIME);
    assertThat(date, notNullValue());
    assertThat(date.getYear(), is(2019));
  }

  @Test
  public void shouldTrimTrailingZoneText() {
    assertThat(trimTrailingZoneText("Sun, 1 Dec 2019 14:49:44 +0100"),
        is("Sun, 1 Dec 2019 14:49:44 +0100"));
    assertThat(trimTrailingZoneText("Sun, 1 Dec 2019 14:49:44 +0100 (CET)"),
        is("Sun, 1 Dec 2019 14:49:44 +0100"));
    assertThat(trimTrailingZoneText("Sun, 14 Jun 2020 16:05:08 +0200 (CEST)"),
        is("Sun, 14 Jun 2020 16:05:08 +0200"));
  }
}
