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

package uk.co.telegraph.switchboard.commons.jdbc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

class JdbcPageableQueryTranslatorTest {

  private static final Set<String> COLUMNS = Set.of("name", "surname", "age");

  private JdbcPageableQueryTranslator queryTranslator;

  private Sort sorting;

  @BeforeEach
  void setUp() {
    sorting = Sort.by(
        new Sort.Order(Direction.ASC, "surname"),
        new Sort.Order(Direction.DESC, "name")
    );
  }

  @Test
  void shouldCreateANotSortableQueryTranslator() {
    queryTranslator = JdbcPageableQueryTranslator.notSortable();

    assertThat(queryTranslator.doesSupportSorting())
        .isFalse();
  }

  @Test
  void shouldCreateASortableQueryTranslator() {
    queryTranslator = JdbcPageableQueryTranslator.sortableOfColumns(COLUMNS);

    assertThat(queryTranslator.doesSupportSorting())
        .isTrue();
  }

  @Test
  void shouldRaiseExceptionCreatingASortableQueryTranslatorWithEmptyColumns() {
    assertThatThrownBy(() -> JdbcPageableQueryTranslator.sortableOfColumns(Set.of()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Column set must not be empty or null in a sortable translator.");
  }

  @Test
  void shouldRaiseExceptionCreatingASortableQueryTranslatorWithNullColumns() {
    assertThatThrownBy(() -> JdbcPageableQueryTranslator.sortableOfColumns(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Column set must not be empty or null in a sortable translator.");
  }

  @Test
  void shouldReturnANotSortedPageableRequestTranslatedToSql() {
    queryTranslator = JdbcPageableQueryTranslator.notSortable();
    Pageable pageable = PageRequest.of(5, 25);

    assertThat(queryTranslator.toSql(pageable))
        .isEqualTo("LIMIT 25 OFFSET 125");
  }

  @Test
  void shouldReturnANotSortedPageableRequestForFirstPageTranslatedToSql() {
    queryTranslator = JdbcPageableQueryTranslator.notSortable();
    Pageable pageable = PageRequest.of(0, 25);

    assertThat(queryTranslator.toSql(pageable))
        .isEqualTo("LIMIT 25");
  }

  @Test
  void shouldReturnANotSortedPageableRequestIgnoringSortingTranslatedToSql() {
    queryTranslator = JdbcPageableQueryTranslator.notSortable();
    Pageable pageable = PageRequest.of(5, 25, sorting);

    assertThat(queryTranslator.toSql(pageable))
        .isEqualTo("LIMIT 25 OFFSET 125");
  }

  @Test
  void shouldReturnASortedPageableRequestTranslatedToSql() {
    queryTranslator = JdbcPageableQueryTranslator.sortableOfColumns(COLUMNS);
    Pageable pageable = PageRequest.of(5, 25, sorting);

    assertThat(queryTranslator.toSql(pageable))
        .isEqualTo("ORDER BY surname ASC, name DESC LIMIT 25 OFFSET 125");
  }

  @Test
  void shouldReturnASortedPageableRequestTranslatedToSqlIgnoringNotSortableColumns() {
    queryTranslator = JdbcPageableQueryTranslator.sortableOfColumns(COLUMNS);
    sorting = Sort.by(
        new Sort.Order(Direction.ASC, "surname"),
        new Sort.Order(Direction.DESC, "name"),
        new Sort.Order(Direction.DESC, "language")
    );
    Pageable pageable = PageRequest.of(5, 25, sorting);

    assertThat(queryTranslator.toSql(pageable))
        .isEqualTo("ORDER BY surname ASC, name DESC LIMIT 25 OFFSET 125");
  }

  @Test
  void shouldReturnASortedPageableRequestForFirstPageTranslatedToSql() {
    queryTranslator = JdbcPageableQueryTranslator.sortableOfColumns(COLUMNS);
    Pageable pageable = PageRequest.of(0, 25, sorting);

    assertThat(queryTranslator.toSql(pageable))
        .isEqualTo("ORDER BY surname ASC, name DESC LIMIT 25");
  }

  @Test
  void shouldReturnANotSortedPageableRequestIfSortingIsEmptyTranslatedToSql() {
    queryTranslator = JdbcPageableQueryTranslator.sortableOfColumns(COLUMNS);
    Pageable pageable = PageRequest.of(5, 25);

    assertThat(queryTranslator.toSql(pageable))
        .isEqualTo("LIMIT 25 OFFSET 125");
  }

}
