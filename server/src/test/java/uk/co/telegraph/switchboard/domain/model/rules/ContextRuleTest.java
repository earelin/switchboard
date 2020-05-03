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

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.telegraph.switchboard.domain.model.ClientInfo;

@ExtendWith({MockitoExtension.class})
class ContextRuleTest {

  private static final String CONTEXT_NAME = "production";
  private static final String CONTEXT_NAME_ALT = "testing";

  private ContextRule contextRule;

  @Mock
  private ClientInfo clientInfo;

  @BeforeEach
  void setUp() {
    contextRule = new ContextRule(CONTEXT_NAME);
  }

  @Test
  void constructor_should_set_context() {
    contextRule = new ContextRule(CONTEXT_NAME_ALT);

    assertThat(contextRule)
        .hasFieldOrPropertyWithValue("desiredContext", CONTEXT_NAME_ALT);
  }

  @Test
  void should_be_enabled_if_client_info_context_matches() {
    when(clientInfo.getPropertyValue(ClientInfo.CONTEXT_PROPERTY_KEY))
        .thenReturn(Optional.of(CONTEXT_NAME));

    assertThat(contextRule.isEnabledForClient(clientInfo))
        .isTrue();
  }

  @Test
  void should_not_be_enabled_if_client_info_context_does_not_exists() {
    when(clientInfo.getPropertyValue(ClientInfo.CONTEXT_PROPERTY_KEY))
        .thenReturn(Optional.empty());

    assertThat(contextRule.isEnabledForClient(clientInfo))
        .isFalse();
  }

  @Test
  void should_not_be_enabled_if_client_info_context_does_not_match() {
    when(clientInfo.getPropertyValue(ClientInfo.CONTEXT_PROPERTY_KEY))
        .thenReturn(Optional.of(CONTEXT_NAME_ALT));

    assertThat(contextRule.isEnabledForClient(clientInfo))
        .isFalse();
  }

}
