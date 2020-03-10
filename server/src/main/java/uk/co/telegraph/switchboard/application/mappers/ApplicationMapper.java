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

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uk.co.telegraph.switchboard.application.dto.ApplicationDto;
import uk.co.telegraph.switchboard.application.dto.ApplicationRequestDto;
import uk.co.telegraph.switchboard.domain.Application;

@Mapper
public interface ApplicationMapper {
  ApplicationDto domainToDto(Application application);

  @Mapping(target = "secret", ignore = true)
  void updateDomainFromDto(ApplicationRequestDto applicationRequestDto,
      @MappingTarget Application application);
}
