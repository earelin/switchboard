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

package uk.co.telegraph.switchboard.domain.model.rules;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.telegraph.switchboard.domain.model.ClientInfo;

@ExtendWith(MockitoExtension.class)
class AndOperatorTest {

  private AndOperator andOperator;

  @Mock
  private Rule firstRule;

  @Mock
  private Rule secondRule;

  @Mock
  private ClientInfo clientInfo;

  @BeforeEach
  void setUp() {
    andOperator = new AndOperator(firstRule, secondRule);
  }

  @Test
  void should_set_rules_in_constructor() {
    Set<Rule> rules = Set.of(firstRule, secondRule);
    when(firstRule.isEnabledForClient(clientInfo))
        .thenReturn(true);
    when(secondRule.isEnabledForClient(clientInfo))
        .thenReturn(true);

    andOperator = new AndOperator(rules);

    assertThat(andOperator.isEnabledForClient(clientInfo))
        .isTrue();
  }

  @Test
  void should_be_true_if_both_are_true() {
    when(firstRule.isEnabledForClient(clientInfo))
        .thenReturn(true);
    when(secondRule.isEnabledForClient(clientInfo))
        .thenReturn(true);

    assertThat(andOperator.isEnabledForClient(clientInfo))
        .isTrue();
  }

  @Test
  void should_be_false_if_one_is_false() {
    when(firstRule.isEnabledForClient(clientInfo))
        .thenReturn(false);
    when(secondRule.isEnabledForClient(clientInfo))
        .thenReturn(true);

    assertThat(andOperator.isEnabledForClient(clientInfo))
        .isFalse();
  }

}
