/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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
import static org.junit.jupiter.api.Assertions.*;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientInfoTest {

  private static final String APPLICATION_KEY = "56c6d5f1-050e-4914-95c4-40330ef21918";
  private static final ZonedDateTime DATE_TIME = ZonedDateTime.now();
  private static final String CONTEXT_PROPERTY_KEY = "context";
  private static final String CONTEXT_PROPERTY_VALUE = "preprod";

  private ClientInfo clientInfo;

  @BeforeEach
  void setUp() {
    clientInfo = new ClientInfo(APPLICATION_KEY, DATE_TIME);
  }

  @Test
  void should_set_application_and_time_on_construction() {
    assertThat(clientInfo)
        .hasFieldOrPropertyWithValue("application", APPLICATION_KEY)
        .hasFieldOrPropertyWithValue("time", DATE_TIME);
  }

  @Test
  void should_return_application() {
    assertThat(clientInfo.getApplication())
        .isEqualTo(APPLICATION_KEY);
  }

  @Test
  void should_return_time() {
    assertThat(clientInfo.getTime())
        .isEqualTo(DATE_TIME);
  }

  @Test
  void constructed_object_should_not_have_empty_properties() {
    assertThat(clientInfo.getProperties())
        .isEmpty();
  }

  @Test
  void should_set_and_get_a_property() {
    clientInfo.setProperty(CONTEXT_PROPERTY_KEY, CONTEXT_PROPERTY_VALUE);

    assertThat(clientInfo.getPropertyValue(CONTEXT_PROPERTY_KEY))
        .isEqualTo(CONTEXT_PROPERTY_VALUE);
  }

  @Test
  void should_return_true_if_a_property_exists() {
    clientInfo.setProperty(CONTEXT_PROPERTY_KEY, CONTEXT_PROPERTY_VALUE);

    assertThat(clientInfo.doesPropertyExists(CONTEXT_PROPERTY_KEY))
        .isTrue();
  }

  @Test
  void should_return_false_if_a_property_does_not_exists() {
    assertThat(clientInfo.doesPropertyExists(CONTEXT_PROPERTY_KEY))
        .isFalse();
  }

}
