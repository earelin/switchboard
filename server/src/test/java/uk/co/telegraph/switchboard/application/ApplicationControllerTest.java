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

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.getApplicationList;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.telegraph.switchboard.Integration;
import uk.co.telegraph.switchboard.application.dto.ApplicationCreateDto;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.domain.validation.ValidationException;
import uk.co.telegraph.switchboard.factories.ApplicationFactory;
import uk.co.telegraph.switchboard.repositories.ApplicationRepository;

/** Web layer controller test. */
@Integration
@WebMvcTest({ApplicationController.class, GlobalControllerExceptionHandler.class})
class ApplicationControllerTest {

  private static final Gson gson = new Gson();

  private static final String APPLICATION_ID = "8c42f1d8-f588-451c-8465-be0ea7ed8025";
  private static final String APPLICATION_NAME = "Website";
  private static final String APPLICATION_DESCRIPTION = "Public website";
  private static final String APPLICATION_SECRET = "RHzSD62mQe4BhH3E";
  private static final String APPLICATION_UPDATED_NAME = "Old Website";
  private static final String APPLICATION_UPDATED_DESCRIPTION = "Old public website";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ApplicationRepository applicationRepository;

  @MockBean
  private ApplicationFactory applicationFactory;

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(mockMvc);
  }

  @Test
  void should_create_an_application_with_name_and_description() {
    Application application = new Application(APPLICATION_ID, APPLICATION_NAME, APPLICATION_SECRET);
    application.setDescription(APPLICATION_DESCRIPTION);
    when(applicationFactory.createApplication(APPLICATION_NAME, APPLICATION_DESCRIPTION))
        .thenReturn(application);
    when(applicationRepository.save(any())).then(returnsFirstArg());

    ApplicationCreateDto request =
        new ApplicationCreateDto(APPLICATION_NAME, APPLICATION_DESCRIPTION);

    given()
        .contentType(ContentType.JSON)
        .body(gson.toJson(request))
      .when()
        .post("/rest/v1/application")
      .then()
        .status(HttpStatus.CREATED)
        .body("id", equalTo(APPLICATION_ID))
        .body("name", equalTo(APPLICATION_NAME))
        .body("description", equalTo(APPLICATION_DESCRIPTION))
        .body("secret", equalTo(APPLICATION_SECRET));
  }

  @Test
  void should_not_create_an_application_with_empty_name() {
    when(applicationFactory.createApplication(any(), any()))
        .thenThrow(ValidationException.class);

    ApplicationCreateDto request = new ApplicationCreateDto("  ", APPLICATION_DESCRIPTION);

    given()
        .contentType(ContentType.JSON)
        .body(gson.toJson(request))
      .when()
        .post("/rest/v1/application")
      .then()
        .status(HttpStatus.BAD_REQUEST);
  }

  @Test
  void should_return_bad_request_if_the_create_body_is_not_valid() {
    when(applicationFactory.createApplication(any(), any()))
        .thenThrow(ValidationException.class);

    given()
        .contentType(ContentType.JSON)
        .body("{\"no-sense-field\": \"value\"}")
      .when()
        .post("/rest/v1/application")
      .then()
        .status(HttpStatus.BAD_REQUEST);
  }

  @Test
  void should_find_an_application_if_exists() {
    Application application = new Application(APPLICATION_ID, APPLICATION_NAME, APPLICATION_SECRET);
    application.setDescription(APPLICATION_DESCRIPTION);
    when(applicationRepository.getById(APPLICATION_ID)).thenReturn(Optional.of(application));

    given()
      .when()
        .get("/rest/v1/application/{id}", APPLICATION_ID)
      .then()
        .status(HttpStatus.OK)
        .body("id", equalTo(APPLICATION_ID))
        .body("name", equalTo(APPLICATION_NAME))
        .body("description", equalTo(APPLICATION_DESCRIPTION))
        .body("secret", equalTo(APPLICATION_SECRET));
  }

  @Test
  void should_return_not_found_is_an_requested_application_does_not_exists() {
    when(applicationRepository.getById(APPLICATION_ID))
        .thenReturn(Optional.empty());

    given()
      .when()
        .get("/rest/v1/application/{id}", APPLICATION_ID)
      .then()
        .status(HttpStatus.NOT_FOUND);
  }

  @Test
  void should_delete_one_resource() {
    when(applicationRepository.existsById(APPLICATION_ID))
        .thenReturn(true);

    given()
      .when()
        .delete("/rest/v1/application/{id}", APPLICATION_ID)
      .then()
        .status(HttpStatus.OK);
  }

  @Test
  void should_return_not_found_if_requested_application_for_delete_does_not_exists() {
    when(applicationRepository.existsById(APPLICATION_ID))
        .thenReturn(false);

    given()
      .when()
        .delete("/rest/v1/application/{id}", APPLICATION_ID)
      .then()
        .status(HttpStatus.NOT_FOUND);
  }

  @Test
  void should_update_one_application() throws Exception {
    Application application = new Application(APPLICATION_ID, APPLICATION_NAME, APPLICATION_SECRET);
    application.setDescription(APPLICATION_DESCRIPTION);
    when(applicationRepository.getById(APPLICATION_ID)).thenReturn(Optional.of(application));
    when(applicationRepository.save(any())).then(returnsFirstArg());

    ApplicationCreateDto request =
        new ApplicationCreateDto(APPLICATION_UPDATED_NAME, APPLICATION_UPDATED_DESCRIPTION);

    mockMvc
        .perform(
            put("/rest/v1/application/{id}", APPLICATION_ID)
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
    when(applicationRepository.getById(APPLICATION_ID)).thenReturn(Optional.empty());

    ApplicationCreateDto request =
        new ApplicationCreateDto(APPLICATION_UPDATED_NAME, APPLICATION_UPDATED_DESCRIPTION);

    mockMvc
        .perform(
            put("/rest/v1/application/{id}", APPLICATION_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(gson.toJson(request)))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void should_not_update_an_application_with_empty_name() throws Exception {
    when(applicationRepository.getById(any()))
        .thenReturn(Optional.of(new Application(APPLICATION_ID, APPLICATION_NAME, APPLICATION_SECRET)));
    ApplicationCreateDto request = new ApplicationCreateDto("  ", APPLICATION_DESCRIPTION);

    mockMvc
        .perform(
            put("/rest/v1/application/{id}", APPLICATION_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(gson.toJson(request)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_return_application_list() throws Exception {
    Page<Application> applications =
        new PageImpl<>(getApplicationList(), PageRequest.of(2, 10), 30);
    when(applicationRepository.getPagedList(any())).thenReturn(applications);

    mockMvc
        .perform(get("/rest/v1/application"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.last").value(true))
        .andExpect(jsonPath("$.first").value(false))
        .andExpect(jsonPath("$.pageNumber").value(2))
        .andExpect(jsonPath("$.pageSize").value(10))
        .andExpect(jsonPath("$.totalPages").value(3))
        .andExpect(jsonPath("$.totalElements").value(30));
  }

  @Test
  void should_return_bad_request_error_if_page_number_is_less_that_0() throws Exception {
    mockMvc
        .perform(get("/rest/v1/application").param("page", "-1"))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_return_bad_request_error_if_page_size_is_less_that_1() throws Exception {
    mockMvc
        .perform(get("/rest/v1/application").param("size", "0"))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_return_bad_request_error_if_sorting_property_does_not_exists() throws Exception {
    given()
        .param("sort", "surname")
      .when()
        .get("/rest/v1/application")
      .then()
        .status(HttpStatus.BAD_REQUEST);
  }
}
