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

package uk.co.telegraph.switchboard.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.entry;

import java.time.ZonedDateTime;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.telegraph.switchboard.domain.model.ClientInfo;

class ClientInfoTest {

  private static final String APPLICATION_ID = "56c6d5f1-050e-4914-95c4-40330ef21918";
  private static final String APPLICATION_ID_ALTERNATIVE = "28259af6-c55b-42aa-b4c6-e6292fd4b86d";
  private static final ZonedDateTime DATE_TIME = ZonedDateTime.now();
  private static final String CONTEXT_PROPERTY_KEY = "context";
  private static final String CONTEXT_PROPERTY_VALUE = "preprod";
  private static final String USER_PROPERTY_KEY = "user";
  private static final String USER_PROPERTY_VALUE = "demo@telegraph.co.uk";

  private ClientInfo clientInfo;

  @BeforeEach
  void setUp() {
    clientInfo =
        new ClientInfo(
            APPLICATION_ID, DATE_TIME, Map.of(CONTEXT_PROPERTY_KEY, CONTEXT_PROPERTY_VALUE));
  }

  @Test
  void should_set_application_and_time_on_construction() {
    assertThat(clientInfo)
        .hasFieldOrPropertyWithValue("application", APPLICATION_ID)
        .hasFieldOrPropertyWithValue("time", DATE_TIME)
        .hasFieldOrPropertyWithValue(
            "properties", Map.of(CONTEXT_PROPERTY_KEY, CONTEXT_PROPERTY_VALUE));
  }

  @Test
  void should_return_application() {
    assertThat(clientInfo.getApplication()).isEqualTo(APPLICATION_ID);
  }

  @Test
  void should_return_time() {
    assertThat(clientInfo.getTime()).isEqualTo(DATE_TIME);
  }

  @Test
  void should_return_properties() {
    assertThat(clientInfo.getProperties())
        .contains(entry(CONTEXT_PROPERTY_KEY, CONTEXT_PROPERTY_VALUE));
  }

  @Test
  void should_return_a_read_only_copy_of_properties() {
    Map<String, String> properties = clientInfo.getProperties();
    assertThatThrownBy(() -> properties.put(USER_PROPERTY_KEY, USER_PROPERTY_VALUE))
        .isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  void should_set_and_get_a_property() {
    assertThat(clientInfo.getPropertyValue(CONTEXT_PROPERTY_KEY))
        .get()
        .isEqualTo(CONTEXT_PROPERTY_VALUE);
  }

  @Test
  void should_return_empty_optional_if_a_key_does_not_exists() {
    assertThat(clientInfo.getPropertyValue(USER_PROPERTY_KEY)).isNotPresent();
  }

  @Test
  void should_return_true_if_a_property_exists() {
    assertThat(clientInfo.doesPropertyExists(CONTEXT_PROPERTY_KEY)).isTrue();
  }

  @Test
  void should_return_false_if_a_property_does_not_exists() {
    assertThat(clientInfo.doesPropertyExists(USER_PROPERTY_KEY)).isFalse();
  }

  @Test
  void should_return_property_keys() {
    assertThat(clientInfo.getPropertyKeys()).contains(CONTEXT_PROPERTY_KEY);
  }

  @Test
  void should_be_equal_to_itself() {
    assertThat(clientInfo.equals(clientInfo)).isTrue();
  }

  @Test
  void should_be_equal_to_an_object_with_same_fields() {
    ClientInfo compareObject =
        new ClientInfo(
            APPLICATION_ID, DATE_TIME, Map.of(CONTEXT_PROPERTY_KEY, CONTEXT_PROPERTY_VALUE));

    assertThat(clientInfo.equals(compareObject)).isTrue();
  }

  @Test
  void should_not_be_equal_to_null() {
    assertThat(clientInfo.equals(null)).isFalse();
  }

  @Test
  void should_not_be_equal_to_a_different_class() {
    String compareObject = "2wertgyhuji";

    assertThat(clientInfo.equals(compareObject)).isFalse();
  }

  @Test
  void should_not_be_equal_to_an_object_with_different_application_value() {
    ClientInfo compareObject =
        new ClientInfo(
            APPLICATION_ID_ALTERNATIVE,
            DATE_TIME,
            Map.of(CONTEXT_PROPERTY_KEY, CONTEXT_PROPERTY_VALUE));

    assertThat(clientInfo.equals(compareObject)).isFalse();
  }

  @Test
  void should_not_be_equal_to_an_object_with_different_date_time_value() {
    ClientInfo compareObject =
        new ClientInfo(
            APPLICATION_ID,
            DATE_TIME.minusDays(1),
            Map.of(CONTEXT_PROPERTY_KEY, CONTEXT_PROPERTY_VALUE));

    assertThat(clientInfo.equals(compareObject)).isFalse();
  }

  @Test
  void should_not_be_equal_to_an_object_with_different_properties() {
    ClientInfo compareObject =
        new ClientInfo(APPLICATION_ID, DATE_TIME, Map.of(USER_PROPERTY_KEY, USER_PROPERTY_VALUE));

    assertThat(clientInfo.equals(compareObject)).isFalse();
  }

  @Test
  void should_have_the_same_hash_code_than_itself() {
    assertThat(clientInfo.hashCode()).isEqualTo(clientInfo.hashCode());
  }

  @Test
  void should_have_the_same_hash_code_than_a_object_with_same_field_values() {
    ClientInfo compareObject =
        new ClientInfo(
            APPLICATION_ID, DATE_TIME, Map.of(CONTEXT_PROPERTY_KEY, CONTEXT_PROPERTY_VALUE));

    assertThat(clientInfo.hashCode()).isEqualTo(compareObject.hashCode());
  }

  @Test
  void should_not_have_the_same_hash_code_than_an_object_with_different_application_value() {
    ClientInfo compareObject =
        new ClientInfo(
            APPLICATION_ID_ALTERNATIVE,
            DATE_TIME,
            Map.of(CONTEXT_PROPERTY_KEY, CONTEXT_PROPERTY_VALUE));

    assertThat(clientInfo.hashCode()).isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void should_not_have_the_same_hash_code_than_an_object_with_different_date_time_value() {
    ClientInfo compareObject =
        new ClientInfo(
            APPLICATION_ID,
            DATE_TIME.minusDays(1),
            Map.of(CONTEXT_PROPERTY_KEY, CONTEXT_PROPERTY_VALUE));

    assertThat(clientInfo.hashCode()).isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void should_not_have_the_same_hash_code_than_an_object_with_different_properties() {
    ClientInfo compareObject =
        new ClientInfo(APPLICATION_ID, DATE_TIME, Map.of(USER_PROPERTY_KEY, USER_PROPERTY_VALUE));

    assertThat(clientInfo.hashCode()).isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void should_convert_to_string() {
    assertThat(clientInfo.toString()).startsWith("ClientInfo");
  }
}
