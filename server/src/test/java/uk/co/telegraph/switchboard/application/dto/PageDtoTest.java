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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.generateApplicationDtoList;

import java.io.FileNotFoundException;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class PageDtoTest {

  private static final long TOTAL_ELEMENTS = 40;
  private static final int PAGE_NUMBER = 2;
  private static final int PAGE_SIZE = 10;

  private List<ApplicationDto> applicationDtoList;
  private PageDto<ApplicationDto> pageDto;

  @BeforeEach
  void setUp() throws FileNotFoundException {
    applicationDtoList = generateApplicationDtoList();
    Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    Page<ApplicationDto> page = new PageImpl<>(applicationDtoList, pageable, TOTAL_ELEMENTS);

    pageDto = new PageDto<>(page);
  }

  @Test
  void should_return_and_set_content_on_constructor() {
    assertThat(pageDto.getContent())
        .isEqualTo(applicationDtoList);
  }

  @Test
  void should_return_and_set_total_elements_on_constructor() {
    assertThat(pageDto.getTotalElements())
        .isEqualTo(TOTAL_ELEMENTS);
  }

  @Test
  void should_return_and_set_total_pages_on_constructor() {
    assertThat(pageDto.getTotalPages())
        .isEqualTo(4);
  }

  @Test
  void should_return_and_set_page_number_on_constructor() {
    assertThat(pageDto.getPageNumber())
        .isEqualTo(PAGE_NUMBER);
  }

  @Test
  void should_return_and_set_page_size_on_constructor() {
    assertThat(pageDto.getPageSize())
        .isEqualTo(PAGE_SIZE);
  }

  @Test
  void should_return_and_set_if_is_last_page_on_constructor() {
    assertThat(pageDto.isLast())
        .isFalse();
  }

  @Test
  void should_return_and_set_if_is_first_page_on_constructor() {
    assertThat(pageDto.isFirst())
        .isFalse();
  }

  @Test
  void should_return_string_representation() {
    Assertions.assertThat(pageDto.toString())
        .startsWith("PageDto");
  }
}
