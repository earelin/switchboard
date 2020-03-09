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

package uk.co.telegraph.switchboard.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class SortablePageableBuilderTest {

  private SortablePageableBuilder sortablePageableBuilder;

  @BeforeEach
  void setUp() {
    sortablePageableBuilder = new SortablePageableBuilder("name", "category");
  }

  @Test
  void constructor_should_set_sorting_property_names() {
    assertThat(sortablePageableBuilder)
        .hasFieldOrPropertyWithValue("sortingProperties", new String[] {"name", "category"});
  }

  @Test
  @Disabled
  void should_generate_a_sorted_pageable() {

  }

  @Test
  @Disabled
  void should_throw_error_if_page_is_less_than_0() {

  }

  @Test
  @Disabled
  void should_throw_error_if_size_is_less_than_1() {

  }

  @Test
  @Disabled
  void should_throw_error_if_column_name_is_not_in_list() {

  }

  @Test
  @Disabled
  void should_throw_error_if_sorting_direction_does_not_match() {

  }
}
