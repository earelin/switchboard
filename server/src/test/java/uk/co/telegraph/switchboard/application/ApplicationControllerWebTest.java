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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.co.telegraph.switchboard.utils.ApplicationContentGenerator.getApplicationList;

import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.telegraph.switchboard.Integration;
import uk.co.telegraph.switchboard.application.dto.ApplicationRequestDto;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.factories.ApplicationFactory;
import uk.co.telegraph.switchboard.repositories.ApplicationRepository;

/**
 * Web layer controller test.
 */
@Integration
@WebMvcTest(ApplicationController.class)
public class ApplicationControllerWebTest {

  private static final String APPLICATION_ID = "8c42f1d8-f588-451c-8465-be0ea7ed8025";
  private static final String APPLICATION_NAME = "Website";
  private static final String APPLICATION_DESCRIPTION = "Public website";
  private static final String APPLICATION_SECRET = "RHzSD62mQe4BhH3E";
  private static final String APPLICATION_UPDATED_NAME = "Old Website";
  private static final String APPLICATION_UPDATED_DESCRIPTION = "Old public website";

  private Gson gson = new Gson();

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ApplicationRepository applicationRepository;

  @MockBean
  private ApplicationFactory applicationFactory;

  @Test
  void should_create_an_application_with_name_and_description() throws Exception {
    Application application = new Application(APPLICATION_ID, APPLICATION_NAME, APPLICATION_SECRET);
    application.setDescription(APPLICATION_DESCRIPTION);
    when(applicationFactory.createApplication(APPLICATION_NAME, APPLICATION_DESCRIPTION))
        .thenReturn(application);

    ApplicationRequestDto request
        = new ApplicationRequestDto(APPLICATION_NAME, APPLICATION_DESCRIPTION);

    mockMvc.perform(post("/api/application")
          .contentType(MediaType.APPLICATION_JSON)
          .characterEncoding(StandardCharsets.UTF_8.name())
          .content(gson.toJson(request)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(APPLICATION_ID))
        .andExpect(jsonPath("$.name").value(APPLICATION_NAME))
        .andExpect(jsonPath("$.description").value(APPLICATION_DESCRIPTION))
        .andExpect(jsonPath("$.secret").value(APPLICATION_SECRET));
  }

  @Test
  void should_not_create_an_application_with_empty_name() throws Exception {
    ApplicationRequestDto request = new ApplicationRequestDto("  ", APPLICATION_DESCRIPTION);

    mockMvc.perform(post("/api/application")
          .contentType(MediaType.APPLICATION_JSON)
          .characterEncoding(StandardCharsets.UTF_8.name())
          .content(gson.toJson(request)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_find_an_application_if_exists() throws Exception {
    Application application = new Application(APPLICATION_ID, APPLICATION_NAME, APPLICATION_SECRET);
    application.setDescription(APPLICATION_DESCRIPTION);
    when(applicationRepository.getApplication(APPLICATION_ID))
        .thenReturn(Optional.of(application));

    mockMvc.perform(get("/api/application/{id}", APPLICATION_ID))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(APPLICATION_ID))
        .andExpect(jsonPath("$.name").value(APPLICATION_NAME))
        .andExpect(jsonPath("$.description").value(APPLICATION_DESCRIPTION))
        .andExpect(jsonPath("$.secret").value(APPLICATION_SECRET));
  }

  @Test
  void should_return_not_found_is_an_requested_application_does_not_exists() throws Exception {
    when(applicationRepository.getApplication(APPLICATION_ID))
        .thenReturn(Optional.empty());

    mockMvc.perform(get("/api/application/{id}", APPLICATION_ID))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void should_delete_one_resource() throws Exception {
    when(applicationRepository.doesApplicationExists(APPLICATION_ID))
        .thenReturn(true);

    mockMvc.perform(delete("/api/application/{id}", APPLICATION_ID))
        .andDo(print())
        .andExpect(status().isOk());
    verify(applicationRepository).removeApplication(APPLICATION_ID);
  }

  @Test
  void should_return_not_found_if_requested_application_for_delete_does_not_exists()
      throws Exception {
    when(applicationRepository.doesApplicationExists(APPLICATION_ID))
        .thenReturn(false);

    mockMvc.perform(delete("/api/application/{id}", APPLICATION_ID))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void should_update_one_application() throws Exception {
    Application application = new Application(APPLICATION_ID, APPLICATION_NAME, APPLICATION_SECRET);
    application.setDescription(APPLICATION_DESCRIPTION);
    when(applicationRepository.getApplication(APPLICATION_ID))
        .thenReturn(Optional.of(application));

    ApplicationRequestDto request = new ApplicationRequestDto(
        APPLICATION_UPDATED_NAME,
        APPLICATION_UPDATED_DESCRIPTION
    );

    mockMvc.perform(put("/api/application/{id}", APPLICATION_ID)
          .contentType(MediaType.APPLICATION_JSON)
          .characterEncoding(StandardCharsets.UTF_8.name())
          .content(gson.toJson(request)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(APPLICATION_ID))
        .andExpect(jsonPath("$.name").value(APPLICATION_UPDATED_NAME))
        .andExpect(jsonPath("$.description").value(APPLICATION_UPDATED_DESCRIPTION))
        .andExpect(jsonPath("$.secret").value(APPLICATION_SECRET));
  }

  @Test
  void should_not_update_an_application_that_does_not_exists() throws Exception {
    when(applicationRepository.getApplication(APPLICATION_ID))
        .thenReturn(Optional.empty());

    ApplicationRequestDto request = new ApplicationRequestDto(
        APPLICATION_UPDATED_NAME,
        APPLICATION_UPDATED_DESCRIPTION
    );

    mockMvc.perform(put("/api/application/{id}", APPLICATION_ID)
          .contentType(MediaType.APPLICATION_JSON)
          .characterEncoding(StandardCharsets.UTF_8.name())
          .content(gson.toJson(request)))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void should_not_update_an_application_with_empty_name() throws Exception {
    ApplicationRequestDto request = new ApplicationRequestDto("  ", APPLICATION_DESCRIPTION);

    mockMvc.perform(put("/api/application/{id}", APPLICATION_ID)
          .contentType(MediaType.APPLICATION_JSON)
          .characterEncoding(StandardCharsets.UTF_8.name())
          .content(gson.toJson(request)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_return_application_list() throws Exception {
    Page<Application> applications
        = new PageImpl<>(getApplicationList(), PageRequest.of(2, 10), 30);
    when(applicationRepository.getPagedApplicationList(any()))
        .thenReturn(applications);

    mockMvc.perform(get("/api/application"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.last").value(true))
        .andExpect(jsonPath("$.number").value(2))
        .andExpect(jsonPath("$.size").value(10));
  }
}
