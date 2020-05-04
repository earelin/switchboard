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

package uk.co.telegraph.switchboard.infrastructure.repository.dao.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.APPLICATION_DESCRIPTION;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.APPLICATION_ID;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.APPLICATION_NAME;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.APPLICATION_SECRET;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.generateApplication;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.generateApplicationDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uk.co.telegraph.switchboard.domain.model.Application;
import uk.co.telegraph.switchboard.infrastructure.repository.dao.ApplicationDao;

class ApplicationDaoMapperTest {

  private ApplicationDaoMapper applicationDaoMapper;

  @BeforeEach
  void setUp() {
    applicationDaoMapper = Mappers.getMapper(ApplicationDaoMapper.class);
  }

  @Test
  void should_map_from_domain_to_dao() {
    Application domain = generateApplication();

    assertThat(applicationDaoMapper.domainToDao(domain))
        .isInstanceOf(ApplicationDao.class)
        .hasFieldOrPropertyWithValue("id", APPLICATION_ID)
        .hasFieldOrPropertyWithValue("name", APPLICATION_NAME)
        .hasFieldOrPropertyWithValue("description", APPLICATION_DESCRIPTION)
        .hasFieldOrPropertyWithValue("secret", APPLICATION_SECRET);
  }

  @Test
  void should_map_from_dao_from_domain() {
    ApplicationDao dao = generateApplicationDao();

    assertThat(applicationDaoMapper.daoToDomain(dao))
        .isInstanceOf(Application.class)
        .hasFieldOrPropertyWithValue("id", APPLICATION_ID)
        .hasFieldOrPropertyWithValue("name", APPLICATION_NAME)
        .hasFieldOrPropertyWithValue("description", APPLICATION_DESCRIPTION)
        .hasFieldOrPropertyWithValue("secret", APPLICATION_SECRET);
  }
}
