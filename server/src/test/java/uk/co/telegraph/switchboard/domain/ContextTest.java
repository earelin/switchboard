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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.domain.Context;

class ContextTest {

  private static final Long CONTEXT_ID = 10L;
  private static final String CONTEXT_NAME = "production";

  private Context context;

  private Application application;

  @BeforeEach
  void setUp() {
    application = new Application();
    context = new Context(CONTEXT_ID, application);
  }

  @Test
  void constructor_should_set_id_and_application() {
    assertThat(context)
        .hasFieldOrPropertyWithValue("id", CONTEXT_ID)
        .hasFieldOrPropertyWithValue("application", application);
  }

  @Test
  void should_return_id() {
    assertThat(context.getId())
        .isEqualTo(CONTEXT_ID);
  }

  @Test
  void should_return_application() {
    assertThat(context.getApplication())
        .isEqualTo(application);
  }

  @Test
  void should_set_and_return_name() {
    context.setName(CONTEXT_NAME);
    assertThat(context.getName())
        .isEqualTo(CONTEXT_NAME);
  }

  @Test
  void should_be_equal_to_itself() {
    assertThat(context.equals(context))
        .isTrue();
  }

  @Test
  void should_be_equal_to_an_object_with_same_id() {
    Context compareObject = new Context(CONTEXT_ID, application);

    assertThat(context.equals(compareObject))
        .isTrue();
  }

  @Test
  void should_not_be_equal_to_null() {
    assertThat(context.equals(null))
        .isFalse();
  }

  @Test
  void should_not_be_equal_to_a_different_class() {
    String compareObject = "2wertgyhuji";

    assertThat(context.equals(compareObject))
        .isFalse();
  }

  @Test
  void should_be_equal_to_an_object_with_different_id() {
    Context compareObject = new Context(20L, application);

    assertThat(context.equals(compareObject))
        .isFalse();
  }

  @Test
  void should_have_the_same_hash_code_than_itself() {
    assertThat(context.hashCode())
        .isEqualTo(context.hashCode());
  }

  @Test
  void should_have_the_same_hash_code_than_a_object_with_same_id() {
    Context compareObject = new Context(CONTEXT_ID, new Application());

    assertThat(context.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void should_have_a_different_hash_code_than_a_object_with_differnt_id() {
    Context compareObject = new Context(20L, application);

    assertThat(context.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }
}
