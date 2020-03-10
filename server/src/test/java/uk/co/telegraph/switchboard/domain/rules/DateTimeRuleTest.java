/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.telegraph.switchboard.domain.rules;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.telegraph.switchboard.domain.ClientInfo;

@ExtendWith(MockitoExtension.class)
class DateTimeRuleTest {

  private static final ZonedDateTime DATE_TIME = ZonedDateTime.now();

  private DateTimeRule dateTimeRule;

  @Mock
  private ClientInfo clientInfo;

  @BeforeEach
  void setUp() {
    dateTimeRule = new DateTimeRule();
    dateTimeRule.setDateTimeEnabledAfter(DATE_TIME);
  }

  @Test
  void should_set_and_get_date_time_that_is_enabled_after() {
    assertThat(dateTimeRule.getDateTimeEnabledAfter())
        .isEqualTo(DATE_TIME);
  }

  @Test
  void should_be_enabled_when_client_request_is_after_date_time_field_value() {
    when(clientInfo.getTime())
        .thenReturn(DATE_TIME.plusDays(2));

    assertThat(dateTimeRule.isEnabledForClient(clientInfo))
        .isTrue();
  }

  @Test
  void should_not_be_enabled_when_client_request_is_before_date_time_field_value() {
    when(clientInfo.getTime())
        .thenReturn(DATE_TIME.minusDays(2));

    assertThat(dateTimeRule.isEnabledForClient(clientInfo))
        .isFalse();
  }

}
