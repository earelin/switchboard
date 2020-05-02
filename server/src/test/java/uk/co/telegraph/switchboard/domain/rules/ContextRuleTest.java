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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import uk.co.telegraph.switchboard.domain.ClientInfo;

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
      .hasFieldOrPropertyWithValue("context", CONTEXT_NAME_ALT);
  }

  @Test
  @Disabled
  void should_be_enabled_if_client_info_context_matches() {

  }

}
