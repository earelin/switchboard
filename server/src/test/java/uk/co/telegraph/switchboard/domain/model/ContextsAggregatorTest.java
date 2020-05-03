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

import com.google.common.collect.Sets;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import uk.co.telegraph.switchboard.domain.validation.ValidationException;

class ContextsAggregatorTest {

  private ContextsAggregator contextsAggregator;

  @BeforeEach
  void setUp() {
    contextsAggregator = new ContextsAggregator();
  }

  @Test
  void constructor_should_set_empty_contexts_list() {
    contextsAggregator = new ContextsAggregator();

    assertThat(contextsAggregator)
        .hasFieldOrPropertyWithValue("contexts", Sets.newHashSet());
  }

  @Test
  void constructor_should_set_provided_set_of_contexts() {
    Set<String> contexts = Set.of("test", "production");
    contextsAggregator = new ContextsAggregator(contexts);

    assertThat(contextsAggregator.getContexts())
        .contains("test", "production")
        .hasSize(2);
  }

  @Test
  void should_add_a_context() {
    contextsAggregator.addContext("staging");

    assertThat(contextsAggregator.getContexts())
        .contains("staging");
  }

  @Test
  void should_throw_an_error_adding_a_context_with_null_name() {
    assertThatThrownBy(() -> contextsAggregator.addContext(null))
        .isInstanceOf(ValidationException.class);
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   "})
  void should_throw_an_error_adding_a_context_with_empty_name(String name) {
    assertThatThrownBy(() -> contextsAggregator.addContext(name))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_throw_an_error_adding_a_context_that_already_exists() {
    Set<String> contexts = Set.of("test", "production");
    contextsAggregator = new ContextsAggregator(contexts);

    assertThatThrownBy(() -> contextsAggregator.addContext("test"))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_remove_context() {
    Set<String> contexts = Set.of("test", "production");
    contextsAggregator = new ContextsAggregator(contexts);

    contextsAggregator.removeContext("production");

    assertThat(contextsAggregator.getContexts())
        .doesNotContain("production");
  }

  @Test
  void should_throw_an_error_removing_a_context_with_null_name() {
    assertThatThrownBy(() -> contextsAggregator.removeContext(null))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_throw_exception_if_context_to_remove_does_not_exists() {
    assertThatThrownBy(() -> contextsAggregator.removeContext("staging"))
        .isInstanceOf(ObjectDoesNotExists.class);
  }

  @Test
  void should_return_string_representation() {
    assertThat(contextsAggregator.toString()).startsWith("ContextsAggregator");
  }
}
