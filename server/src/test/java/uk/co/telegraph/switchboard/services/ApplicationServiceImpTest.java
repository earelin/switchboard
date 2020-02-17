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

package uk.co.telegraph.switchboard.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.domain.Context;
import uk.co.telegraph.switchboard.repositories.ApplicationRepository;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceImpTest {

  private static final long APPLICATION_ID = 10;
  private static final String APPLICATION_NAME = "Amazing application";
  private static final String APPLICATION_KEY = "amazing-application";
  private static final String APPLICATION_SECRET = "qwertyuiop";
  private static final String APPLICATION_DESCRIPTION = "Amazing application description";

  private ApplicationService applicationService;

  @Mock
  private ApplicationRepository applicationRepository;

  @BeforeEach
  void setUp() {
    applicationService = new ApplicationServiceImp(applicationRepository, secretsGeneratorService);
  }

  @Test
  void shouldCreateApplicationWithDefaultContext() {
    when(applicationRepository.save(any()))
        .thenAnswer(invocation -> {
          Application application = invocation.getArgument(0);
          application.setId(APPLICATION_ID);
          return application;
        });

    Application application = applicationService
        .createFrom(APPLICATION_NAME, APPLICATION_DESCRIPTION);

    assertThat(application)
        .hasFieldOrPropertyWithValue("id", APPLICATION_ID)
        .hasFieldOrPropertyWithValue("key", APPLICATION_KEY)
        .hasFieldOrPropertyWithValue("secret", APPLICATION_SECRET)
        .hasFieldOrPropertyWithValue("description", APPLICATION_DESCRIPTION);

    assertThat(application.getContexts())
        .extracting("key")
        .contains(Context.DEFAULT_KEY);
  }

}
