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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public class SortablePageableBuilder {

  private final Set<String> allowedSortingProperties;

  /**
   * Constructor allows to specify supported property names for sorting.
   *
   * @param allowedSortingProperties Allowed property names for sorting.
   */
  public SortablePageableBuilder(String... allowedSortingProperties) {
    this.allowedSortingProperties = Set.of(allowedSortingProperties);
  }

  /**
   * Builds a Pageable object.
   *
   * @param page Page number.
   * @param size Number of items per page.
   * @param sortingProperties Sorting properties names plus optional direction in the form
   *     columnName|[asc,desc].
   * @return Pageable object
   */
  public Pageable buildPageable(int page, int size, String[] sortingProperties) {
    if (page < 0) {
      throw new IllegalArgumentException(
          "Page number must not be less than 0, current value: " + page);
    }

    if (size < 1) {
      throw new IllegalArgumentException(
          "Page size must not be less than 1, current value " + size);
    }

    return PageRequest.of(page, size, buildSorting(sortingProperties));
  }

  private Sort buildSorting(String[] sortingProperties) {
    if (sortingProperties != null && sortingProperties.length > 0) {
      List<Sort.Order> orders = new ArrayList<>(sortingProperties.length);

      for (String sortProperty : sortingProperties) {
        orders.add(buildSortOrder(sortProperty));
      }

      return Sort.by(orders);
    }

    return Sort.unsorted();
  }

  private Sort.Order buildSortOrder(String sortingProperty) {
    Sort.Direction direction = Direction.ASC;
    String[] sortingComponents = sortingProperty.split(":");

    if (sortingComponents.length > 2) {
      throw new IllegalArgumentException("Malformed sorting request: " + sortingProperty);
    }

    if (sortingComponents.length == 2) {
      if (sortingComponents[1].equals("asc") || sortingComponents[1].equals("desc")) {
        direction = Direction.valueOf(sortingComponents[1].toUpperCase());
      } else {
        throw new IllegalArgumentException(
            "Sorting direction value has to be 'asc' or 'desc', current value: "
                + sortingComponents[1]);
      }
    }

    if (!allowedSortingProperties.contains(sortingComponents[0])) {
      throw new IllegalArgumentException("Illegal sorting property: " + sortingComponents[0]);
    }

    return new Order(direction, sortingComponents[0]);
  }
}
