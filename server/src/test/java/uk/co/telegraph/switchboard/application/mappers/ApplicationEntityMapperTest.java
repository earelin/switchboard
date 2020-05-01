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

package uk.co.telegraph.switchboard.application.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uk.co.telegraph.switchboard.application.dto.ApplicationCreateDto;
import uk.co.telegraph.switchboard.application.dto.ApplicationDto;
import uk.co.telegraph.switchboard.domain.Application;

class ApplicationEntityMapperTest {

  private static final String APPLICATION_ID = "8c42f1d8-f588-451c-8465-be0ea7ed8025";
  private static final String APPLICATION_NAME = "Website";
  private static final String APPLICATION_DESCRIPTION = "Public website";
  private static final String APPLICATION_SECRET = "RHzSD62mQe4BhH3E";

  private static final String APPLICATION_UPDATED_NAME = "Old Website";
  private static final String APPLICATION_UPDATED_DESCRIPTION = "Old public website";

  private ApplicationMapper applicationMapper;

  private Application application;

  @BeforeEach
  void setUp() {
    application = new Application(APPLICATION_ID, APPLICATION_NAME, APPLICATION_SECRET);
    application.setDescription(APPLICATION_DESCRIPTION);

    applicationMapper = Mappers.getMapper(ApplicationMapper.class);
  }

  @Test
  void should_map_from_domain_to_dto() {
    ApplicationDto applicationDto = applicationMapper.domainToDto(application);

    assertThat(applicationDto)
        .hasFieldOrPropertyWithValue("id", APPLICATION_ID)
        .hasFieldOrPropertyWithValue("name", APPLICATION_NAME)
        .hasFieldOrPropertyWithValue("description", APPLICATION_DESCRIPTION)
        .hasFieldOrPropertyWithValue("secret", APPLICATION_SECRET);
  }

  @Test
  void should_return_null_dto_if_domain_is_null() {
    ApplicationDto applicationDto = applicationMapper.domainToDto(null);

    assertThat(applicationDto).isNull();
  }

  @Test
  void should_update_domain_with_request_dto() {
    ApplicationCreateDto applicationCreateDto =
        new ApplicationCreateDto(APPLICATION_UPDATED_NAME, APPLICATION_UPDATED_DESCRIPTION);

    applicationMapper.updateDomainFromDto(applicationCreateDto, application);

    assertThat(application)
        .hasFieldOrPropertyWithValue("id", APPLICATION_ID)
        .hasFieldOrPropertyWithValue("name", APPLICATION_UPDATED_NAME)
        .hasFieldOrPropertyWithValue("description", APPLICATION_UPDATED_DESCRIPTION)
        .hasFieldOrPropertyWithValue("secret", APPLICATION_SECRET);
  }

  @Test
  void should_not_update_domain_with_request_dto_if_dto_is_null() {
    applicationMapper.updateDomainFromDto(null, application);

    assertThat(application)
        .hasFieldOrPropertyWithValue("id", APPLICATION_ID)
        .hasFieldOrPropertyWithValue("name", APPLICATION_NAME)
        .hasFieldOrPropertyWithValue("description", APPLICATION_DESCRIPTION)
        .hasFieldOrPropertyWithValue("secret", APPLICATION_SECRET);
  }
}
