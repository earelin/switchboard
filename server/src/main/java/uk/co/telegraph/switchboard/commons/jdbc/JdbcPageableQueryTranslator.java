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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Translates Pageable request objects to SQL syntax sentences.
 *
 *
 */
@Slf4j
public class JdbcPageableQueryTranslator {

  private final Set<String> columns;

  /**
   * Construct a translator with search support for the passed columns.
   * The class will check that Pageable objects refers to those columns.
   * @param columns Set of columns to support for sorting. Empty for not sorting support.
   */
  protected JdbcPageableQueryTranslator(Set<String> columns) {
    this.columns = columns;
  }

  /**
   * Construct a translator without sorting support.
   * Pageable sorting objects will be ignored.
   * @return Translator without sorting support.
   */
  public static JdbcPageableQueryTranslator notSortable() {
    return new JdbcPageableQueryTranslator(Set.of());
  }

  /**
   * Construct a translator with sorting support.
   * @param columns A not null and not empty set of columns with sorting support.
   * @return Translator with sorting support.
   */
  public static JdbcPageableQueryTranslator sortableOfColumns(Set<String> columns) {
    if (Objects.isNull(columns) || columns.isEmpty()) {
      throw new IllegalArgumentException(
          "Column set must not be empty or null in a sortable translator.");
    }
    return new JdbcPageableQueryTranslator(Set.copyOf(columns));
  }

  public boolean doesSupportSorting() {
    return !columns.isEmpty();
  }

  public String toSql(Pageable pageable) {
    List<String> statements = new ArrayList<>(2);

    if (doesSupportSorting()) {
      statements.addAll(sortingToSql(pageable));
    }
    statements.addAll(pagingToSql(pageable));

    return String.join(" ", statements).trim();
  }

  private List<String> sortingToSql(Pageable pageable) {
    List<String> statements = new ArrayList<>();

    Sort sorting = pageable.getSort();
    if (sorting.isSorted()) {
      statements.add("ORDER BY");

      List<String> properties = new ArrayList<>();
      for (Sort.Order order: sorting) {
        if (columns.contains(order.getProperty())) {
          properties.add(String.format("%s %s", order.getProperty(), order.getDirection()));
        }
      }

      statements.add(String.join(", ", properties));
    }

    return statements;
  }

  private List<String> pagingToSql(Pageable pageable) {
    List<String> statements = new ArrayList<>(2);

    statements.add(String.format("LIMIT %d", pageable.getPageSize()));

    long offset = pageable.getOffset();
    if (offset > 0) {
      statements.add(String.format("OFFSET %d", offset));
    }

    return statements;
  }
}
