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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ContextTest {

  private static final Long CONTEXT_ID = 10L;
  private static final Long CONTEXT_ID_ALT = 125L;
  private static final String CONTEXT_NAME = "production";
  private static final String CONTEXT_NAME_ALT = "testing";

  private Context context;

  private Application application;

  private ContextsAggregator contextsAggregator;

  @BeforeEach
  void setUp() {
    application = new Application();
    application.setId("9ef05156-0a81-485a-b439-e4fec4288321");
    contextsAggregator = new ContextsAggregator(application);
    context = new Context(contextsAggregator, CONTEXT_NAME);
    context.setId(CONTEXT_ID);
  }

  @Test
  void constructor_should_set_application_context_and_name() {
    assertThat(context)
        .hasFieldOrPropertyWithValue("contextsAggregator", contextsAggregator)
        .hasFieldOrPropertyWithValue("name", CONTEXT_NAME);
  }

  @Test
  void should_set_and_return_id() {
    context.setId(CONTEXT_ID_ALT);

    assertThat(context.getId()).isEqualTo(CONTEXT_ID_ALT);
  }

  @Test
  void should_set_and_return_application_context() {
    Application application = new Application();
    application.setId("6e9f3d44-753c-48b3-a137-c250902380de");
    contextsAggregator = new ContextsAggregator(application);

    context.setContextsAggregator(contextsAggregator);

    assertThat(context.getContextsAggregator()).isEqualTo(contextsAggregator);
  }

  @Test
  void should_set_and_return_name() {
    context.setName(CONTEXT_NAME_ALT);

    assertThat(context.getName()).isEqualTo(CONTEXT_NAME_ALT);
  }

  @Test
  void should_throw_error_if_null_name_is_set() {
    assertThatThrownBy(() -> context.setName(null)).isInstanceOf(IllegalArgumentException.class);
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "",
        "   ",
        "CxQdrPbLJFZDsdE55z482zC5k1Xlcv4Ju",
        "NyLJWskvH6TLR9bzLzscI6Ol6vr2Av2bOVKA3FXFdjQX2wEZsTzqn5pYvkg96nKS"
      })
  void should_throw_error_if_invalid_name_is_set(String name) {
    assertThatThrownBy(() -> context.setName(name)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void should_be_equal_to_itself() {
    assertThat(context.equals(context)).isTrue();
  }

  @Test
  void should_be_equal_to_an_object_with_same_id() {
    Context compareObject = new Context(contextsAggregator, CONTEXT_NAME_ALT);
    compareObject.setId(CONTEXT_ID);

    assertThat(context.equals(compareObject)).isTrue();
  }

  @Test
  void should_not_be_equal_to_null() {
    assertThat(context.equals(null)).isFalse();
  }

  @Test
  void should_not_be_equal_to_a_different_class() {
    String compareObject = "2wertgyhuji";

    assertThat(context.equals(compareObject)).isFalse();
  }

  @Test
  void should_be_equal_to_an_object_with_different_id() {
    Context compareObject = new Context(contextsAggregator, CONTEXT_NAME);
    compareObject.setId(CONTEXT_ID_ALT);

    assertThat(context.equals(compareObject)).isFalse();
  }

  @Test
  void should_have_the_same_hash_code_than_itself() {
    assertThat(context.hashCode()).isEqualTo(context.hashCode());
  }

  @Test
  void should_have_the_same_hash_code_than_a_object_with_same_id() {
    Context compareObject = new Context(new ContextsAggregator(), CONTEXT_NAME_ALT);
    compareObject.setId(CONTEXT_ID);

    assertThat(context.hashCode()).isEqualTo(compareObject.hashCode());
  }

  @Test
  void should_have_a_different_hash_code_than_a_object_with_differnt_id() {
    Context compareObject = new Context(contextsAggregator, CONTEXT_NAME);
    compareObject.setId(CONTEXT_ID_ALT);

    assertThat(context.hashCode()).isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void should_return_string_representation() {
    assertThat(context.toString()).startsWith("Context");
  }
}
