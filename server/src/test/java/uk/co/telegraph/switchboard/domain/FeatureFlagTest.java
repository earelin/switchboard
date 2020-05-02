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

package uk.co.telegraph.switchboard.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.telegraph.switchboard.domain.rules.DefaultRule;
import uk.co.telegraph.switchboard.domain.rules.Rule;

@ExtendWith(MockitoExtension.class)
class FeatureFlagTest {

  public static final String FEATURE_FLAG_ID = "menu-dropdown-v2";

  private FeatureFlag featureFlag;

  @Mock
  private Rule rule;

  @Mock
  private ClientInfo clientInfo;

  @BeforeEach
  void setUp() {
    featureFlag = new FeatureFlag(FEATURE_FLAG_ID, rule);
  }

  @Test
  void constructor_should_set_id_and_rule() {
    assertThat(featureFlag)
        .hasFieldOrPropertyWithValue("id", FEATURE_FLAG_ID)
        .hasFieldOrPropertyWithValue("rule", rule);
  }

  @Test
  void id_based_constructor_should_set_default_disabled_rule() {
    featureFlag = new FeatureFlag(FEATURE_FLAG_ID);

    assertThat(featureFlag.getRule())
        .isInstanceOf(DefaultRule.class)
        .hasFieldOrPropertyWithValue("enabled", false);
  }

  @Test
  void should_report_enabled_if_rule_report_enabled() {
    when(rule.isEnabledForClient(clientInfo))
        .thenReturn(true);

    assertThat(featureFlag.isEnabledForClient(clientInfo))
        .isTrue();
  }

  @Test
  void should_report_disabled_if_rule_report_disabled() {
    when(rule.isEnabledForClient(clientInfo))
        .thenReturn(false);

    assertThat(featureFlag.isEnabledForClient(clientInfo))
        .isFalse();
  }

  @Test
  void should_return_string_representation() {
    assertThat(featureFlag.toString())
        .startsWith("FeatureFlag");
  }
}
