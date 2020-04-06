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

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ContextsAggregatorTest {

  private static final String APPLICATION_ID = "e3968bda-b73c-4844-a89c-6a881631a3b5";
  private static final String APPLICATION_ID_ALT = "f0f6891a-c7c2-4ea8-b5b1-46dc151fe627";

  private Application application;

  private Map<String, Context> contexts;

  private ContextsAggregator contextsAggregator;

  @BeforeEach
  void setUp() {
    application = new Application(APPLICATION_ID, "Website", "gOamHi76csrxxW5T");

    contextsAggregator = new ContextsAggregator(application);

    contexts = generateContextList(contextsAggregator);
    contextsAggregator.setContexts(contexts);
  }

  @Test
  void constructor_should_set_application_and_empty_contexts_list() {
    contextsAggregator = new ContextsAggregator(application);

    assertThat(contextsAggregator)
        .hasFieldOrPropertyWithValue("application", application)
        .hasFieldOrPropertyWithValue("contexts", Maps.newHashMap());
  }

  @Test
  void constructor_should_set_empty_contexts_list() {
    contextsAggregator = new ContextsAggregator();

    assertThat(contextsAggregator)
        .hasFieldOrPropertyWithValue("application", null)
        .hasFieldOrPropertyWithValue("contexts", Maps.newHashMap());
  }

  @Test
  void should_set_and_return_application() {
    application =
        new Application(
            "4e42342b-9cb0-4571-9d64-2022ad80d4a6", "Mobile Application", "xe1dnv2bRMpLbXJh");

    contextsAggregator.setApplication(application);

    assertThat(contextsAggregator.getApplication()).isEqualTo(application);
  }

  @Test
  void should_set_and_return_contexts() {
    Map<String, Context> contextList = generateContextList(contextsAggregator);
    contextsAggregator.setContexts(contextList);

    assertThat(contextsAggregator.getContexts()).isEqualTo(contextList);
  }

  @Test
  void should_add_a_context() {
    contextsAggregator.addContext("staging");

    assertThat(contextsAggregator.getContexts()).containsKey("staging");
  }

  @Test
  void should_throw_an_error_adding_a_context_with_null_name() {
    assertThatThrownBy(() -> contextsAggregator.addContext(null))
        .isInstanceOf(NullPointerException.class);
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   "})
  void should_throw_an_error_adding_a_context_with_empty_name(String name) {
    assertThatThrownBy(() -> contextsAggregator.addContext(name))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void should_throw_an_error_adding_a_context_that_already_exists() {
    assertThatThrownBy(() -> contextsAggregator.addContext("testing"))
        .isInstanceOf(ObjectAlreadyExistsInAggregate.class);
  }

  @Test
  void should_return_true_if_a_context_exists() {
    assertThat(contextsAggregator.containsContext("production")).isEqualTo(true);
  }

  @Test
  void should_return_false_if_a_context_does_not_exists() {
    assertThat(contextsAggregator.containsContext("staging")).isEqualTo(false);
  }

  @Test
  void should_return_false_context_null_context_exists() {
    assertThat(contextsAggregator.containsContext(null)).isEqualTo(false);
  }

  @Test
  void should_return_a_context_by_name() {
    assertThat(contextsAggregator.getContext("production"))
        .isPresent()
        .get()
        .hasFieldOrPropertyWithValue("name", "production")
        .hasFieldOrPropertyWithValue("contextsAggregator", contextsAggregator);
  }

  @Test
  void should_throw_an_error_getting_a_context_with_null_name() {
    assertThatThrownBy(() -> contextsAggregator.getContext(null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void should_return_empty_if_a_context_does_not_exists() {
    assertThat(contextsAggregator.getContext("staging")).isEmpty();
  }

  @Test
  void should_remove_context() {
    contextsAggregator.removeContext("production");

    assertThat(contextsAggregator.getContexts()).doesNotContainKeys("production");
  }

  @Test
  void should_throw_an_error_removing_a_context_with_null_name() {
    assertThatThrownBy(() -> contextsAggregator.removeContext(null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void should_throw_exception_if_context_to_remove_does_not_exists() {
    assertThatThrownBy(() -> contextsAggregator.removeContext("staging"))
        .isInstanceOf(ObjectDoesNotExists.class);
  }

  @Test
  void should_be_equal_to_itself() {
    assertThat(contextsAggregator.equals(contextsAggregator)).isTrue();
  }

  @Test
  void should_be_equal_to_an_object_with_same_id() {
    ContextsAggregator compareObject = new ContextsAggregator(application);

    assertThat(contextsAggregator.equals(compareObject)).isTrue();
  }

  @Test
  void should_not_be_equal_to_null() {
    assertThat(contextsAggregator.equals(null)).isFalse();
  }

  @Test
  void should_not_be_equal_to_a_different_class() {
    String compareObject = "2wertgyhuji";

    assertThat(contextsAggregator.equals(compareObject)).isFalse();
  }

  @Test
  void should_not_be_equal_to_an_object_with_different_application_id() {
    Application anotherApplication = new Application();
    anotherApplication.setId(APPLICATION_ID_ALT);
    ContextsAggregator compareObject = new ContextsAggregator(anotherApplication);

    assertThat(contextsAggregator.equals(compareObject)).isFalse();
  }

  @Test
  void should_have_the_same_hash_code_than_a_object_with_same_id() {
    ContextsAggregator compareObject = new ContextsAggregator(application);

    assertThat(contextsAggregator.hashCode()).isEqualTo(compareObject.hashCode());
  }

  @Test
  void should_not_have_the_same_hash_code_than_an_object_with_different_properties() {
    Application anotherApplication = new Application();
    anotherApplication.setId(APPLICATION_ID_ALT);
    ContextsAggregator compareObject = new ContextsAggregator(anotherApplication);

    assertThat(contextsAggregator.hashCode()).isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void should_return_string_representation() {
    assertThat(contextsAggregator.toString()).startsWith("ContextsAggregator");
  }

  private Map<String, Context> generateContextList(ContextsAggregator contextsAggregator) {
    Map<String, Context> contextsList = new HashMap<>();
    contextsList.put("production", new Context(contextsAggregator, "production"));
    contextsList.put("testing", new Context(contextsAggregator, "testing"));
    return contextsList;
  }
}
