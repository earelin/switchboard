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

package uk.co.telegraph.switchboard.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import uk.co.telegraph.switchboard.controllers.dtos.ApplicationDto;
import uk.co.telegraph.switchboard.factories.ApplicationFactory;
import uk.co.telegraph.switchboard.repositories.ApplicationRepository;
import uk.co.telegraph.switchboard.utils.ApplicationContentGenerator;

@ExtendWith(MockitoExtension.class)
class ApplicationControllerTest {

  private static final String APPLICATION_ID = "e6211ab1-8838-38ec-a10f-d36f673bb84f";
  private static final String APPLICATION_NAME = "Mobile Application";
  private static final String APPLICATION_SECRET
      = "QjVpgDl7C7j9q09GMHlE5qYVsOU1VDG5ZQSgVAvmf8lhrY7HnKAzNtXeZS1fPtcS";
  private static final String APPLICATION_DESCRIPTION
      = "Nullam ipsum orci, suscipit a nibh vel, tempus iaculis nibh.";

  @Mock
  private ApplicationRepository applicationRepository;

  @Mock
  private ApplicationFactory applicationFactory;

  @InjectMocks
  private ApplicationController applicationController;

  @Test
  void shouldReturnAnApplicationIfExists() {
    when(applicationRepository.findById("e6211ab1-8838-38ec-a10f-d36f673bb84f"))
        .thenReturn(Optional.of(ApplicationContentGenerator.generateApplication(
            APPLICATION_ID,
            APPLICATION_NAME,
            APPLICATION_SECRET,
            APPLICATION_DESCRIPTION,
            Set.of("development", "staging", "production")
        )));

    ApplicationDto applicationDto = applicationController.find(APPLICATION_ID);

    assertThat(applicationDto)
        .hasFieldOrPropertyWithValue("id", APPLICATION_ID)
        .hasFieldOrPropertyWithValue("name", APPLICATION_NAME)
        .hasFieldOrPropertyWithValue("secret", APPLICATION_SECRET)
        .hasFieldOrPropertyWithValue("description", APPLICATION_DESCRIPTION);

    assertThat(applicationDto.getContexts())
        .contains("development", "staging", "production");
  }

  @Test
  void shouldThrowAnExceptionIfApplicationDoesNotExists() {
    when(applicationRepository.findById("e6211ab1-8838-38ec-a10f-d36f673bb84f"))
        .thenReturn(Optional.empty());

    assertThatThrownBy(() -> applicationController.find(APPLICATION_ID))
        .isInstanceOf(ResponseStatusException.class)
        .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND)
        .hasFieldOrPropertyWithValue("reason",
            "Application not found: e6211ab1-8838-38ec-a10f-d36f673bb84f");
  }
}
