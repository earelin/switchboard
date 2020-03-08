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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.telegraph.switchboard.application.dto.ApplicationDto;
import uk.co.telegraph.switchboard.application.dto.ApplicationRequest;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.factories.ApplicationFactory;
import uk.co.telegraph.switchboard.repositories.ApplicationRepository;

@ExtendWith(MockitoExtension.class)
class ApplicationControllerTest {

  private static final String APPLICATION_ID = "8c42f1d8-f588-451c-8465-be0ea7ed8025";
  private static final String APPLICATION_NAME = "Website";
  private static final String APPLICATION_DESCRIPTION = "Public website";
  private static final String APPLICATION_SECRET = "RHzSD62mQe4BhH3E";

  @Mock
  private ApplicationFactory applicationFactory;

  @Mock
  private ApplicationRepository applicationRepository;

  @InjectMocks
  private ApplicationController applicationController;

  @Test
  void should_create_an_application_with_name_and_description() {
    Application application = new Application(APPLICATION_ID, APPLICATION_NAME, APPLICATION_SECRET);
    application.setDescription(APPLICATION_DESCRIPTION);
    when(applicationFactory.createApplication(APPLICATION_NAME, APPLICATION_DESCRIPTION))
        .thenReturn(application);
    ApplicationRequest request = new ApplicationRequest(APPLICATION_NAME, APPLICATION_DESCRIPTION);

    ApplicationDto applicationDto = applicationController.createApplication(request);

    assertThat(applicationDto)
        .hasFieldOrPropertyWithValue("id", APPLICATION_ID)
        .hasFieldOrPropertyWithValue("name", APPLICATION_NAME)
        .hasFieldOrPropertyWithValue("description", APPLICATION_DESCRIPTION)
        .hasFieldOrPropertyWithValue("secret", APPLICATION_SECRET);
  }

  @Test
  @Disabled
  void should_get_an_application_if_exists() {
    Application application = new Application(APPLICATION_ID, APPLICATION_NAME, APPLICATION_SECRET);
    application.setDescription(APPLICATION_DESCRIPTION);
    when(applicationRepository.getApplication(APPLICATION_ID))
        .thenReturn(Optional.of(application));
  }
}
