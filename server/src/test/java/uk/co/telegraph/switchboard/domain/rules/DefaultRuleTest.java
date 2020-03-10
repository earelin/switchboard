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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.telegraph.switchboard.domain.ClientInfo;

@ExtendWith(MockitoExtension.class)
class DefaultRuleTest {

  private DefaultRule defaultRule;

  @Mock
  private ClientInfo clientInfo;

  @BeforeEach
  void setUp() {
    defaultRule = new DefaultRule();
  }

  @Test
  void should_not_be_enabled_in_construction() {
    assertThat(defaultRule.isEnabled())
        .isFalse();
  }

  @Test
  void should_set_and_get_enable_value() {
    defaultRule.setEnabled(true);

    assertThat(defaultRule.isEnabled())
        .isTrue();
  }

  @Test
  void should_report_disabled_if_default_value_is_not_enabled() {
    assertThat(defaultRule.isEnabledForClient(clientInfo))
        .isFalse();
  }

  @Test
  void should_report_enabled_if_default_value_is_enabled() {
    defaultRule.setEnabled(true);

    assertThat(defaultRule.isEnabledForClient(clientInfo))
        .isTrue();
  }

}
