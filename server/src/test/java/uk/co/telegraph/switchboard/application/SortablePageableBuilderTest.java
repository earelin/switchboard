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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.Order.desc;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class SortablePageableBuilderTest {

  private SortablePageableBuilder sortablePageableBuilder;

  @BeforeEach
  void setUp() {
    sortablePageableBuilder = new SortablePageableBuilder("name", "category");
  }

  @Test
  void constructor_should_set_sorting_property_names() {
    assertThat(sortablePageableBuilder)
        .hasFieldOrPropertyWithValue("allowedSortingProperties", Set.of("name", "category"));
  }

  @Test
  void should_generate_a_not_sorted_pageable() {
    Pageable pageable = sortablePageableBuilder.buildPageable(1, 10, null);

    assertThat(pageable.isPaged()).isTrue();
    assertThat(pageable.getPageNumber()).isEqualTo(1);
    assertThat(pageable.getPageSize()).isEqualTo(10);
    assertThat(pageable.getSort().isSorted()).isFalse();
  }

  @Test
  void should_generate_a_not_sorted_pageable_with_empty_sorting_array() {
    Pageable pageable = sortablePageableBuilder.buildPageable(1, 10, new String[] {});

    assertThat(pageable.isPaged()).isTrue();
    assertThat(pageable.getPageNumber()).isEqualTo(1);
    assertThat(pageable.getPageSize()).isEqualTo(10);
    assertThat(pageable.getSort().isSorted()).isFalse();
  }

  @Test
  void should_generate_a_sorted_pageable() {
    Pageable pageable = sortablePageableBuilder.buildPageable(1, 10,
        new String[] {"name:asc", "category:desc"});

    assertThat(pageable.isPaged()).isTrue();
    assertThat(pageable.getPageNumber()).isEqualTo(1);
    assertThat(pageable.getPageSize()).isEqualTo(10);

    Sort sorting = pageable.getSort();
    assertThat(sorting.isSorted()).isTrue();
    assertThat(sorting.iterator())
        .toIterable()
        .containsExactly(asc("name"), desc("category"));
  }

  @Test
  void should_throw_error_if_page_is_less_than_0() {
    assertThatThrownBy(() -> sortablePageableBuilder.buildPageable(-1, 1, null))
        .isInstanceOf(IllegalArgumentException.class);

    assertThatThrownBy(() -> sortablePageableBuilder.buildPageable(-10, 1, null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void should_throw_error_if_size_is_less_than_1() {
    assertThatThrownBy(() -> sortablePageableBuilder.buildPageable(1, 0, null))
        .isInstanceOf(IllegalArgumentException.class);

    assertThatThrownBy(() -> sortablePageableBuilder.buildPageable(1, -10, null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void should_throw_error_if_column_name_is_not_in_list() {
    assertThatThrownBy(() -> sortablePageableBuilder.buildPageable(2, 10, new String[] {"surname"}))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void should_throw_error_if_sorting_direction_does_not_match() {
    assertThatThrownBy(() -> sortablePageableBuilder.buildPageable(2, 10, new String[] {"name:esc"}))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void should_throw_error_if_sorting_request_is_malformed() {
    assertThatThrownBy(() -> sortablePageableBuilder.buildPageable(2, 10, new String[] {"name:asc:desc"}))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
