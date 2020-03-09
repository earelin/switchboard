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

import org.springframework.data.domain.Pageable;

public class SortablePageableBuilder {

  private final String[] sortingProperties;

  /**
   * Constructor allows to specify supported property names for sorting.
   * @param sortingProperties Allowed property names for sorting.
   */
  public SortablePageableBuilder(String... sortingProperties) {
    this.sortingProperties = sortingProperties;
  }

  /**
   * Builds a Pageable object.
   * @param page Page number.
   * @param size Number of items per page.
   * @param sortColumn Sorting column name.
   * @param sortDirection Sorting direction ("asc" or "desc")
   * @return Pageable object
   */
  public Pageable buildPageable(int page, int size, String sortColumn, String sortDirection) {
    throw new UnsupportedOperationException();
  }
}
