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

package uk.co.telegraph.switchboard.infrastructure.repository.dao.mapping;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import uk.co.telegraph.switchboard.domain.model.Application;
import uk.co.telegraph.switchboard.infrastructure.repository.dao.ApplicationDao;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ApplicationDaoMapper {
  default Application daoToDomain(ApplicationDao applicationDao) {
    Application application = new Application(
        applicationDao.getId(),
        applicationDao.getName(),
        applicationDao.getSecret());
    application.setDescription(applicationDao.getDescription());
    return application;
  }

  ApplicationDao domainToDao(Application application);
}
