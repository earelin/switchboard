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

package uk.co.telegraph.switchboard.application.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@Getter
@Setter
@ToString
public class PageDto<T> implements Serializable {

  private static final long serialVersionUID = 7316735749604752908L;

  private final List<T> content;
  private final long totalElements;
  private final int totalPages;
  private final int pageNumber;
  private final int pageSize;
  private final boolean isLast;
  private final boolean isFirst;

  public PageDto(Page<T> page) {
    this.content = page.getContent();
    this.totalElements = page.getTotalElements();
    this.totalPages = page.getTotalPages();
    this.pageNumber = page.getNumber();
    this.pageSize = page.getSize();
    this.isLast = page.isLast();
    this.isFirst = page.isFirst();
  }
}
